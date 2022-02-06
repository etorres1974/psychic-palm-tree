package br.com.freedomgist.gist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.freedomgist.databinding.GistRecyclerviewBinding

class GistRecyclerView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding = GistRecyclerviewBinding.inflate(LayoutInflater.from(context), this, true)

    init{
        setLayout()
    }

    fun setPagedViewModel(lifecycleOwner: LifecycleOwner, viewModel: GistViewModel) =
        with(binding.recyclerView) {
            adapter = GistAdapter(viewModel::onClickGist)
            viewModel.gisPagestLivedata().observe(lifecycleOwner) { pagingData ->
                (this.adapter as GistAdapter).submitData(
                    lifecycleOwner.lifecycle,
                    pagingData
                )
                observeEmpty()
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