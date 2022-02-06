package br.com.freedomgist

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import br.com.data.apiSource.network.utils.ErrorEntity

class ErrorDialog(private val errorEntity: ErrorEntity) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.error_title)
            builder.setMessage(errorEntity.resolveMessage())
                .setPositiveButton(R.string.error_positive_response,
                    DialogInterface.OnClickListener { dialog, id ->
                        //TODO - get token if forbidden
                    })
                .setNegativeButton(R.string.error_negative_response,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun ErrorEntity.resolveMessage() = when(this){
        is ErrorEntity.Forbidden -> R.string.error_forbidden_message
        is ErrorEntity.NetworkError -> R.string.error_network_message
        is ErrorEntity.ValidationError -> R.string.error_validation_error
        is ErrorEntity.Unknown -> R.string.error_unknown_error
    }
}