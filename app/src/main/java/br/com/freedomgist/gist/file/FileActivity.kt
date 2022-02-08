package br.com.freedomgist.gist.file

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import br.com.freedomgist.R
import br.com.freedomgist.databinding.ActivityFileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


@OptIn(ExperimentalPagingApi::class)
class FileActivity() : AppCompatActivity() {

    private val viewModel : FileViewModel by viewModel()

    private lateinit var binding: ActivityFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file)
        viewModel.getFiles(getGistId())
        setupViews()
        setupListeners()
    }

    private fun setupViews() = with(binding){
        rvFiles.adapter = FileAdapter(viewModel::getCode)
    }

    private fun setupListeners() = with(viewModel) {
        files.observe(this@FileActivity){ files ->
            (binding.rvFiles.adapter as FileAdapter).submitList(files)
        }
        code.observe(this@FileActivity){ code ->
            binding.rvFiles.isVisible = false
            Log.d("ABACATE", "CODE ->$code <-")
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            val fragment = FileFragment(code)
            transaction.add(R.id.fcv_file, fragment)
            transaction.addToBackStack("File")
            transaction.commit()
        }
    }

    override fun onBackPressed() {
        binding.rvFiles.isVisible = true
        super.onBackPressed()
    }

    private fun getGistId() = intent.extras?.getInt(OWNER_ID) ?: throw IllegalArgumentException("Gist Id is mandatory for this activity")

    companion object{
        private const val OWNER_ID = "OWNER_ID"
        fun getIntent(ctx : Context, ownerId : Int ) = Intent(ctx, FileActivity::class.java).apply {
            this.putExtra(OWNER_ID, ownerId)
        }
    }
}



