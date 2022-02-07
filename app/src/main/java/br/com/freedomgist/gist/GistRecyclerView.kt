package br.com.freedomgist.gist

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.data.apiSource.network.utils.ErrorEntity
import br.com.freedomgist.databinding.GistRecyclerviewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GistRecyclerView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding = GistRecyclerviewBinding.inflate(LayoutInflater.from(context), this, true)

    init{
        setLayout()
    }

    fun loadStateListener(lifecycleScope: LifecycleCoroutineScope,  onLoadStateChange : (ErrorEntity) -> Unit) {
        val pagingAdapter = binding.recyclerView.adapter as GistAdapter
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest { loadState ->

                val hasError = listOf(
                    loadState.append, loadState.prepend, loadState.refresh,
                    loadState.mediator?.append, loadState?.mediator?.prepend, loadState.mediator?.refresh,
                    loadState.source.append, loadState.source.prepend, loadState.source.refresh,

                ).filterIsInstance<LoadState.Error>()
                if(hasError.isNotEmpty())
                    Log.d("ABACATE", "HAS ERROR : ${hasError}")
                onLoadStateChange(ErrorEntity.Forbidden)
            }
        }
    }

    fun setPagedViewModel(lifecycleOwner: LifecycleOwner, viewModel: GistViewModel, onLoadStateChange : (ErrorEntity) -> Unit) =
        with(binding.recyclerView) {
            adapter = GistAdapter(viewModel::onClickGist, viewModel::onFavoriteGist)
            viewModel.gisPagestLivedata().observe(lifecycleOwner) { pagingData ->
                (this.adapter as GistAdapter).submitData(
                    lifecycleOwner.lifecycle,
                    pagingData
                )
                observeEmpty()
                loadStateListener(lifecycleOwner.lifecycleScope, onLoadStateChange)
            }
        }

    private fun observeEmpty() = with(binding) {
        val isEmpty = recyclerView.adapter?.let{ it.itemCount < 1 } ?: true
        recyclerView.isVisible = isEmpty().not()
        emptyList.isVisible = isEmpty
    }

    private fun setLayout() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        addItemDecoration(decoration)
    }
}