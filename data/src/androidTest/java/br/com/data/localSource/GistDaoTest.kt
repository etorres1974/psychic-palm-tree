package br.com.data.localSource

import br.com.MockGistProvider
import br.com.data.InstrumentedTest
import br.com.data.localSource.dao.GistDao
import br.com.data.localSource.entity.Gist
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GistDaoTest : InstrumentedTest() {

    private val gistDao: GistDao by lazy { app.gistDao }

    @Test
    fun insert_gist_on_dao() = runBlocking {
        assert(gistDao.getAll().isEmpty())
        val gistDTO = MockGistProvider.getSingle()
        val gist = gistDTO.toDbModel(1)
        gistDao.insert(gist)
        val result = gistDao.getAll()
        assertEquals(gist.id , result.first().id)
        assertEquals(1 ,result.size)
    }

    @Test
    fun convert_api_gist_into_db_gist_model() = runBlocking {
        val gist = MockGistProvider.getSingle()
        val dbGist = gist.toDbModel(1)

        assert(gistDao.getAll().isEmpty())
            {"GistDao should start empty"}

        gistDao.insert(dbGist)

        val result = gistDao.getAll()
        assert(dbGist.id == result.first().id)
            {"Was expecting $gist but got: $result"}

    }

}