package br.com.data.localSource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.DataTestRunner
import br.com.MockGistProvider
import br.com.data.localSource.dao.GistDao
import br.com.data.localSource.entity.Gist
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GistDaoTest : KoinTest {

    private lateinit var  gistDao: GistDao
    private lateinit var  db : GistDatabase

    private fun gistDatabase(ctx : Context) = Room.inMemoryDatabaseBuilder(
        ctx,
        GistDatabase::class.java
    ).build()

    @Before
    fun createDb(){
        val context : DataTestRunner = ApplicationProvider.getApplicationContext()
        db = gistDatabase(context)
        gistDao = db.gistDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insert_gist_on_dao() = runBlocking {
        assert(gistDao.getAll().isEmpty())
        val gist = Gist("1", "", 1, "", "")
        gistDao.insert(gist)
        val result = gistDao.getAll()
        assertEquals(gist , result.first())
        assertEquals(1 ,result.size)
    }

    @Test
    fun convert_api_gist_into_db_gist_model() = runBlocking {
        val gist = MockGistProvider.getSingle()
        val dbGist = gist.toDbModel()

        assert(gistDao.getAll().isEmpty())
            {"GistDao should start empty"}

        gistDao.insert(dbGist)

        val result = gistDao.getAll()
        assert(dbGist == result.first())
            {"Was expecting $gist but got: $result"}

    }

}