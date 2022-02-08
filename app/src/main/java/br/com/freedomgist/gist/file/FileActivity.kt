package br.com.freedomgist.gist.file

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import br.com.freedomgist.GistViewModel
import br.com.freedomgist.R
import br.com.freedomgist.databinding.ActivityFileBinding
import br.com.freedomgist.gist.list.bindActions
import br.com.freedomgist.gist.list.setupViews
import org.koin.androidx.viewmodel.ext.android.viewModel


@OptIn(ExperimentalPagingApi::class)
class FileActivity() : AppCompatActivity() {

    private val viewModel : FileViewModel by viewModel()
    private val gistViewModell : GistViewModel by viewModel()

    private lateinit var binding: ActivityFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file)
        viewModel.getGistById(getGistId())
        setupViews()
        setupListeners()
    }

    private fun setupViews() = with(binding){
        rvFiles.adapter = FileAdapter(viewModel::getCode)
    }

    private fun setupListeners() = with(viewModel) {
        gist.observe(this@FileActivity){ gistAndFiles ->
            (binding.rvFiles.adapter as FileAdapter).submitList(gistAndFiles.files)
            binding.itemGist.setupViews(gistAndFiles.gist)
            binding.itemGist.bindActions(gistAndFiles.gist, gistViewModell)
        }

        code.observe(this@FileActivity){ code ->
            openFileFragment(code)
        }
    }

    private fun openFileFragment(code : CodeData){
        binding.rvFiles.isVisible = false
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val fragment = FileFragment.getInstance(code)
        transaction.replace(R.id.fcv_file, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        binding.rvFiles.isVisible = true
        super.onBackPressed()
    }

    private fun getGistId() = intent.extras?.getString(GIST_ID) ?: throw IllegalArgumentException("Gist Id is mandatory for this activity")

    companion object{
        private const val GIST_ID = "GIST_ID"
        fun getIntent(ctx : Context, gistId : String ) = Intent(ctx, FileActivity::class.java).apply {
            this.putExtra(GIST_ID, gistId)
        }
    }
}



