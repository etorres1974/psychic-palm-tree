package br.com.data

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnitRunner
import br.com.DataTestRunner
import br.com.data.localSource.GistDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule

class MockTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(cl, DataTestRunner::class.java.name, context)

}

open class BaseTest(){

    lateinit var context: Context
    lateinit var app : DataTestRunner
    lateinit var db : GistDatabase

    @Before
    open fun setup(){
        context = ApplicationProvider.getApplicationContext()
        app = context.applicationContext as DataTestRunner
        db = app.db
    }

    @After
    open fun teardown(){
        db.clearAllTables()
    }
}