package br.com.freedomgist.gist.file

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.data.localSource.entity.File

class FileAdapter(private val onClick: (File) -> Unit) : ListAdapter<File, FileViewHolder>(FILE_COMPARATOR) {

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileItem = getItem(position)
        holder.bind(fileItem, onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder =
        FileViewHolder.inflate(parent)


    companion object {
        private val FILE_COMPARATOR = object : DiffUtil.ItemCallback<File>() {
            override fun areItemsTheSame(oldItem: File, newItem: File): Boolean  =
                oldItem.filename == newItem.filename

            override fun areContentsTheSame(oldItem: File, newItem: File): Boolean =
                oldItem.content == newItem.content
        }
    }
}