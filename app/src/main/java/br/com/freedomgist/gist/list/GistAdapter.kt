package br.com.freedomgist.gist.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.data.localSource.entity.Gist
import br.com.freedomgist.gist.GistActions

class GistAdapter(
    private val gistActions: GistActions
) : PagingDataAdapter<Gist, GistViewHolder>(GIST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GistViewHolder {
        return GistViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: GistViewHolder, position: Int) {
        val gistItem = getItem(position)
        holder.bind(gistItem, gistActions)
    }

    companion object {
        private val GIST_COMPARATOR = object : DiffUtil.ItemCallback<Gist>() {
            override fun areItemsTheSame(oldItem: Gist, newItem: Gist): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Gist, newItem: Gist): Boolean =
                oldItem.updated_at == newItem.updated_at &&
                oldItem.favorite == newItem.favorite

        }
    }
}