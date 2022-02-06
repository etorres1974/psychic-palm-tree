package br.com.data.apiSource.network.utils

import br.com.data.apiSource.models.GistDTO
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.Gson

class JsonHelper {

    private val gistFileDeserializer = JsonDeserializer { jsonElement, _, context ->
        val jsonObject = jsonElement.asJsonObject
        val gistFiles : List<GistDTO.FileDTO> =
            jsonObject.entrySet().map{ context.deserialize(it.value, GistDTO.FileDTO::class.java)  }
        GistDTO.Files(gistFiles)
    }

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(GistDTO.Files::class.java, gistFileDeserializer)
        .create()
}