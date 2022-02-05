package br.com

import okhttp3.mockwebserver.MockWebServer
import org.junit.After

open class MockWebServerTest {

    var mockWebServer : MockWebServer? = null

    @After
    open fun teardown(){
        mockWebServer?.apply {
            dispatcher.shutdown()
            shutdown()
        }
    }
}