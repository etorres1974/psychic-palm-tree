package br.com.freedomgist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.freedomgist.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupRecyclerView()
        authenticate()

    }

    private fun setupRecyclerView() = with(binding.gistRv){
        setPagedViewModel(this@MainActivity, viewModel)
    }

    private fun authenticate(){
        viewModel.askPermissionCode()
    }

}