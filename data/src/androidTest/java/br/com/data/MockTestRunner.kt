package br.com.data

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import br.com.DataApplication

class MockTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?,
                                context: Context?): Application {
        return super.newApplication(cl, DataTestRunner::class.java.name, context)
    }
}

class DataTestRunner : DataApplication(){
    override fun getBaseUrl() = "http://127.0.0.1:8080"
}