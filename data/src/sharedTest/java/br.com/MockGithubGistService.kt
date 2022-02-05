package br.com

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.koin.test.KoinTest
import java.lang.IllegalArgumentException

class MockGithubGistService {

    fun successApi() = getApi(successDispatcher())

    fun errorApi() = getApi(errorDispatcher())

    private fun getApi(dispatcher: Dispatcher) =  MockWebServer(dispatcher).apply{
        start(8080)
    }


    private fun MockWebServer(dispatcher: Dispatcher) : MockWebServer = MockWebServer().apply {
        this.dispatcher = dispatcher
    }

    private fun successDispatcher() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when{
                request.path?.startsWith("/gists") == true -> MockResponse().setResponseCode(200).setBody(fileReader("MultipleGists.json"))
                else -> throw IllegalArgumentException("Route ${request.path} is not implemented in mockwebserver")
            }
        }
    }

    private fun errorDispatcher() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when{
                request.path?.startsWith("/gists") == true -> MockResponse().setResponseCode(422).setBody(UNPROCESSABLE_ENTITY)
                else -> throw IllegalArgumentException("Route ${request.path} is not implemented in mockwebserver")
            }
        }
    }

    companion object{
        private const val UNPROCESSABLE_ENTITY = "Status: 422 Unprocessable Entity"
    }
}
