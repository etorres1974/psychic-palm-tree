package br.com.data

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import br.com.DataTestRunner

class MockTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(cl, DataTestRunner::class.java.name, context)

}