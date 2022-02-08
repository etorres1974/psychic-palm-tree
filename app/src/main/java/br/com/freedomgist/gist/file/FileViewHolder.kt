package br.com.freedomgist.gist.file

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.data.localSource.entity.File
import br.com.freedomgist.databinding.ItemFileBinding

class FileViewHolder (
    private val binding : ItemFileBinding
    ): RecyclerView.ViewHolder(binding.root){

    fun bind(file: File, onClick: (File) -> Unit) = with(binding){
        this.file = file
        root.setOnClickListener {
            onClick(file)
        }
    }

    companion object {
        fun inflate(parent: ViewGroup) = FileViewHolder(
            ItemFileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}