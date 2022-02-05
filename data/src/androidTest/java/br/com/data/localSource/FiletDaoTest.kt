package br.com.data.localSource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.DataTestRunner
import br.com.MockGistProvider
import br.com.data.localSource.dao.FileDao
import br.com.data.localSource.entity.File
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FiletDaoTest : KoinTest {

    private lateinit var  fileDao: FileDao
    private lateinit var  db : GistDatabase

    private fun gistDatabase(ctx : Context) = Room.inMemoryDatabaseBuilder(
        ctx,
        GistDatabase::class.java
    ).build()

    @Before
    fun createDb(){
        val context : DataTestRunner = ApplicationProvider.getApplicationContext()
        db = gistDatabase(context)
        fileDao = db.fileDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insert_gist_on_dao() = runBlocking {
        assert(fileDao.getAll().isEmpty())
        val file = File("1", "", "", "", 1, "")
        fileDao.insert(file)
        val result = fileDao.getAll()
        assertEquals(file , result.first())
        assertEquals(1 ,result.size)
    }

    @Test
    fun convert_api_gist_into_db_gist_model() = runBlocking {
        val gist = MockGistProvider.getSingle()
        val dbGist = gist.toDbModel()
        val dbFiles = gist.getFilesDb()

        assert(fileDao.getAll().isEmpty())
            {"GistDao should start empty"}

        fileDao.insert(dbFiles)

        val result = fileDao.getAll()
        assert(dbFiles == result)
            {"Was expecting $dbFiles but got: $result"}

        assert(result.all{ it.owner_id == dbGist.id})
            {"All files should be mapped to the same owner"}

    }

}