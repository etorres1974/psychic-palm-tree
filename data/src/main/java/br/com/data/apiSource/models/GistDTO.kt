package br.com.data.apiSource.models

import br.com.data.localSource.entity.File
import br.com.data.localSource.entity.FileModel
import br.com.data.localSource.entity.Gist
import br.com.data.localSource.entity.GistModel

data class GistDTO(
    val id: String?,
    val owner: OwnerDTO,
    val files: Files,
    val comments: Int,
    val comments_url: String,
    val commits_url: String,
    val created_at: String,
    val description: String?,
    val forks_url: String,
    val git_pull_url: String,
    val git_push_url: String,
    val html_url: String,
    val node_id: String,
    val `public`: Boolean,
    val truncated: Boolean,
    val updated_at: String,
    val url: String,
) : GistModel , FileModel{

    data class Files(val list : List<FileDTO>)

    data class FileDTO(
        val filename: String,
        val language: String?,
        val raw_url: String,
        val size: Int,
        val type: String
    )


    data class OwnerDTO(
        val avatar_url: String?,
        val events_url: String,
        val followers_url: String,
        val following_url: String,
        val gists_url: String,
        val gravatar_id: String,
        val html_url: String,
        val id: Int?,
        val login: String?,
        val node_id: String,
        val organizations_url: String,
        val received_events_url: String,
        val repos_url: String,
        val site_admin: Boolean,
        val starred_url: String,
        val subscriptions_url: String,
        val type: String,
        val url: String
    )

    override fun toDbModel(page : Int) = Gist(
        id = id ?: "",
        owner_id = owner.id ?: 0,
        avatar_url = owner.avatar_url ?: "",
        login = owner.login ?: "",
        description = description ?: "",
        page = page
   )

    override fun getFilesDb(): List<File> = files.list.map {
        File(
            owner_id = owner.id ?:  0,
            filename = it.filename,
            language = it.language ?: "",
            raw_url = it.raw_url,
            size = it.size,
            type = it.type
        )
    }
}