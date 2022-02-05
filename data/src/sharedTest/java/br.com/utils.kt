package br.com

import java.io.InputStreamReader

fun Any.fileReader(filePath: String): String {
    val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(filePath))
    return reader.readText().also { reader.close() }
}
