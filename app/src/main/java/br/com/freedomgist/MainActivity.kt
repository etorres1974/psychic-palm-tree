package br.com.freedomgist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.freedomgist.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel.queryGists()
    }

}