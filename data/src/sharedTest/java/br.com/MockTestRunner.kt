package br.com

class DataTestRunner : DataApplication() {

    override fun getBaseUrl() = MOCK_BASE_URL

    companion object{
        const val MOCK_BASE_URL = "http://127.0.0.1:8080"
    }
}