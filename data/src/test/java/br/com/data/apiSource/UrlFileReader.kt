package br.com.data.apiSource

import br.com.data.apiSource.network.utils.getTextFromWeb
import org.junit.Test


class TestReader{

    @Test
    fun test_reader(){
        val url = "https://gist.githubusercontent.com/GrahamcOfBorg/bbdd69380ae5a71983aa50fee642c9c0/raw/51be4f6deb9dbe97c742b4e266213149fd42c1c1/Changed%2520Paths"
        val reader = getTextFromWeb(url)
        assert(reader?.isEmpty()?.not())
    }
}