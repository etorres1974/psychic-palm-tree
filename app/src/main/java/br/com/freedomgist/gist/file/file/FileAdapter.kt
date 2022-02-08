package br.com.freedomgist.gist.file.file

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.data.localSource.entity.File
import br.com.data.localSource.entity.Gist

class FileAdapter() : ListAdapter<File, FileViewHolder>(FILE_COMPARATOR) {

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileItem = getItem(position)
        holder.bind(fileItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder  =
       FileViewHolder.inflate(parent)


    companion object {
        private val FILE_COMPARATOR = object : DiffUtil.ItemCallback<File>() {
            override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
                TODO("Not yet implemented")
            }

        }
    }
}