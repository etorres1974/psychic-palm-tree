package br.com.freedomgist.gist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import br.com.data.localSource.entity.Gist
import br.com.freedomgist.R
import br.com.freedomgist.databinding.ItemGistBinding
import br.com.freedomgist.gist.GistActions
import br.com.freedomgist.loadImageUrl

class GistViewHolder (
    private val binding : ItemGistBinding
    ): RecyclerView.ViewHolder(binding.root){

    fun bind(gist: Gist?, gistActions: GistActions) = with(binding){
        setupViews(gist)
        bindActions(gist, gistActions)
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

fun ItemGistBinding.setupViews(gist: Gist?) {
    gist?.let {
        this.gist = gist

        ivUser.loadImageUrl(gist.avatar_url)

        val starDrawable = when {
            gist.favorite -> R.drawable.ic_baseline_star_24
            else -> R.drawable.ic_baseline_star_outline_24
        }
        ivStar.setImageDrawable(AppCompatResources.getDrawable(root.context, starDrawable))
    }
}

fun ItemGistBinding.bindActions(gist : Gist?, gistActions: GistActions) {
    gist?.let {
        root.setOnClickListener {
            gistActions.onClickGist(gist.id)
        }
        ivStar.setOnClickListener {
            gistActions.onFavoriteGist(gist.favorite, gist.id)
        }
    }
}