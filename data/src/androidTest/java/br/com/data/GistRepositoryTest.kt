package br.com.data

import br.com.data.repository.GistRepository
import org.junit.Test


class GistRepositoryTest  : BaseTest(){

    val gistRepository : GistRepository by lazy { app.gistRepository }

    @Test
    fun assertA(){
        assert(gistRepository != null)
    }
}