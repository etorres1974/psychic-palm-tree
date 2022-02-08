package br.com.freedomgist.gist.file

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import br.com.freedomgist.R
import br.com.freedomgist.databinding.ActivityFileBinding
import br.com.freedomgist.gist.file.file.FileAdapter
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
        title.text = getGistId().toString()
        rvFiles.adapter = FileAdapter()
    }

    private fun setupListeners() = with(viewModel) {
        files.observe(this@FileActivity){ files ->
            (binding.rvFiles.adapter as FileAdapter).submitList(files)
        }
    }

    private fun getGistId() = intent.extras?.getInt(OWNER_ID) ?: throw IllegalArgumentException("Gist Id is mandatory for this activity")

    companion object{
        private const val OWNER_ID = "OWNER_ID"
        fun getIntent(ctx : Context, ownerId : Int ) = Intent(ctx, FileActivity::class.java).apply {
            this.putExtra(OWNER_ID, ownerId)
        }
    }
}



