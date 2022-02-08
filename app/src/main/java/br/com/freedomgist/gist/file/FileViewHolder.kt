package br.com.freedomgist.gist.file

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.data.localSource.entity.File
import br.com.freedomgist.databinding.ItemFileBinding

class FileViewHolder (
    private val binding : ItemFileBinding
    ): RecyclerView.ViewHolder(binding.root){

    fun bind(file: File, onClick: (String) -> Unit) = with(binding){
        this.file = file
        //codeView.isVisible = file.content  != null
        Log.d("ABACATE", "FILE SIZE ${file.raw_url}")
        root.setOnClickListener {
            onClick(file.raw_url)
            Log.d("ABACATE", "VH CLICK ${file.raw_url}")
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