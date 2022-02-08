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
import java.lang.IllegalArgumentException


@OptIn(ExperimentalPagingApi::class)
class GistFragment() : Fragment() {

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
            val intent = FileActivity.getIntent(requireActivity().baseContext, gistId)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() = with(binding.gistRv){
        setPagedViewModel(lifecycleOwner = this@GistFragment.viewLifecycleOwner, viewModel = viewModel, gistFilter = getGistFilter()){ err ->
            Log.d("ABA", "Gist Frag ${err}")
        }
    }

    private fun getGistFilter() : GistFilter = arguments?.getSerializable(GIST_FILTER) as? GistFilter ?: throw IllegalArgumentException()

    companion object{
        private const val GIST_FILTER = "GIST_FILTER"
        fun getInstance(gistFilter: GistFilter): GistFragment {
            val args = Bundle().apply { putSerializable(GIST_FILTER, gistFilter) }
            return GistFragment().apply { arguments = args }
        }
    }

}


