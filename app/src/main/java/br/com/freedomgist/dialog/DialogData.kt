package br.com.freedomgist.dialog

data class DialogData(
    val title: String,
    val message: String,
    val onConfirm: (() -> Unit) = {},
    val onDismiss: (() -> Unit) = {}
)