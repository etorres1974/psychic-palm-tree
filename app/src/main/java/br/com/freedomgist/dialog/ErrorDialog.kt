package br.com.freedomgist.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import br.com.data.apiSource.network.utils.ErrorEntity
import br.com.freedomgist.R

class ErrorDialog() : DialogFragment() {

    private lateinit var errorEntity : ErrorEntity

    fun addError(fragmentManager: FragmentManager, errorEntity: ErrorEntity) {
        this.errorEntity = errorEntity
        show(fragmentManager ,TAG)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if(!this.isAdded) {
            val transaction = manager.beginTransaction()
            transaction.add(this, TAG)
            transaction.commit()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        requireActivity().buildDialog(
            DialogData(
                title = getString(R.string.error_title),
                message = getString(errorEntity.resolveMessage())
            )
        )

    private fun ErrorEntity.resolveMessage() = when(this){
        is ErrorEntity.Forbidden -> R.string.error_forbidden_message
        is ErrorEntity.NetworkError -> R.string.error_network_message
        is ErrorEntity.ValidationError -> R.string.error_validation_error
        is ErrorEntity.Unknown -> R.string.error_unknown_error
    }

    private fun Context.buildDialog(dialogData: DialogData): Dialog = with(AlertDialog.Builder(this)) {
        setTitle(dialogData.title)
        setMessage(dialogData.message)
        setPositiveButton(dialogData.onConfirm)
        setNegativeButton(dialogData.onDismiss)
        setCancelable(false)
        return create()
    }

    private fun AlertDialog.Builder.setPositiveButton(onClick: () -> Unit) = setPositiveButton(
        context.getString(R.string.error_positive_response),
        onClick.let { { _: DialogInterface, _: Int -> it() } }
    )

    private fun AlertDialog.Builder.setNegativeButton(onClick: () -> Unit) = setNegativeButton(
        context.getString(R.string.error_negative_response),
        onClick.let { { _: DialogInterface, _: Int -> it() } }
    )
    companion object{
        const val TAG = "ErrorDialog"
    }
}

