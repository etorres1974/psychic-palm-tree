package br.com.data.localSource

import br.com.MockGistProvider
import br.com.data.InstrumentedTest
import br.com.data.localSource.dao.FileDao
import br.com.data.localSource.entity.File
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FileDaoTest : InstrumentedTest() {

    private val fileDao: FileDao by lazy { app.fileDao }

    @Test
    fun insert_gist_on_dao() = runBlocking {
        assert(fileDao.getAll().isEmpty())
        val gistDTO = MockGistProvider.getSingle()
        val file = File(1, "", "", "", "1", 1,"")
        fileDao.insert(file)
        val result = fileDao.getAll()
        assertEquals(file , result.first())
        assertEquals(1 ,result.size)
    }

    @Test
    fun convert_api_gist_into_db_gist_model() = runBlocking {
        val gist = MockGistProvider.getSingle()
        val dbGist = gist.toDbModel(1)
        val dbFiles = gist.getFilesDb()

        assert(fileDao.getAll().isEmpty())
            {"GistDao should start empty"}

        dbFiles.forEach {fileDao.insert(it) }

        val result = fileDao.getAll()
        assert(dbFiles == result)
            {"Was expecting $dbFiles but got: $result"}

        assert(result.all{ it.owner_id == dbGist.owner_id})
            {"All files should be mapped to the same owner"}

    }

}