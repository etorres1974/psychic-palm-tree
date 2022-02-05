package br.com.data.localSource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.data.localSource.dao.GistDao
import br.com.data.localSource.entity.Gist
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class GistDaoTest {

    private lateinit var gistDao: GistDao
    private lateinit var db : GistDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GistDatabase::class.java).build()
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
        val gist = Gist("1")
        gistDao.insert(gist)
        val result = gistDao.getAll()
        assertEquals(gist , result.first())
        assertEquals(1 ,result.size)
    }
}