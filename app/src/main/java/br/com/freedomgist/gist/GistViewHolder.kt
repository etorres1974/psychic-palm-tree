package br.com.freedomgist.gist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.data.localSource.entity.Gist
import br.com.freedomgist.databinding.ItemGistBinding

class GistViewHolder (
    private val binding : ItemGistBinding
    ): RecyclerView.ViewHolder(binding.root){

    fun bind(gist: Gist, onClickGist: (String) -> Unit) = with(binding){
        binding.gist = gist
        root.setOnClickListener { onClickGist(gist.id) }
    }

    companion object {
        fun inflate(parent: ViewGroup) = GistViewHolder(
            ItemGistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}