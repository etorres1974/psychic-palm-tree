package br.com.freedomgist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import br.com.data.apiSource.network.utils.ErrorEntity
import br.com.freedomgist.databinding.ActivityMainBinding
import br.com.freedomgist.dialog.ErrorDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    val erroDialog = ErrorDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupListeners()
        setupRecyclerView()

    }

    private fun setupRecyclerView() = with(binding.gistRv){
        setPagedViewModel(this@MainActivity, viewModel){ err ->
            //showError(err)
        }
    }

    private fun setupListeners() = with(viewModel){
        errorEntityLiveData.observe(this@MainActivity){
            //showError(it)
        }
    }

    private fun showError(error : ErrorEntity)  {
        erroDialog.addError(supportFragmentManager, error)
    }

    private fun authenticate(){
        binding.emptyList.isVisible
        viewModel.askPermissionCode()
    }

}



