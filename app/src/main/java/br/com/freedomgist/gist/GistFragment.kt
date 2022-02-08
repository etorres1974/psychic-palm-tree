package br.com.freedomgist.gist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.ExperimentalPagingApi
import br.com.data.localSource.entity.GistFilter
import br.com.freedomgist.GistViewModel
import br.com.freedomgist.databinding.FragmentGistPageBinding
import br.com.freedomgist.gist.file.FileActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


@OptIn(ExperimentalPagingApi::class)
class GistFragment(private val gistFilter : GistFilter) : Fragment() {

    private lateinit var binding : FragmentGistPageBinding

    private val viewModel : GistViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGistPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupNavigationObserver()
    }

    private fun setupNavigationObserver(){
        viewModel.openGist.observe(this@GistFragment.viewLifecycleOwner){ gistId ->
            Log.d("ABACATE", " id ->>>>>>${gistId}")
            val intent = FileActivity.getIntent(requireActivity().baseContext, gistId)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() = with(binding.gistRv){
        setPagedViewModel(lifecycleOwner = this@GistFragment.viewLifecycleOwner, viewModel = viewModel, gistFilter = gistFilter){ err ->
            Log.d("ABA", "Gist Frag ${err}")
        }
    }

}


