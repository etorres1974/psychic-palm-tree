package br.com.freedomgist.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import br.com.data.apiSource.models.DeviceCode
import br.com.data.apiSource.network.utils.ErrorEntity
import br.com.freedomgist.R

class ErrorDialog() : DialogFragment() {

    private var errorEntity : ErrorEntity? = null
    private var onConfirm: (() -> Unit) = {}
    private var onDismiss: (() -> Unit) = {}

    private var deviceCode : DeviceCode? = null


    fun deviceCode(fragmentManager: FragmentManager, deviceCode: DeviceCode, onConfirm: (() -> Unit) = {} , onDismiss : (() -> Unit) = {} ) {
        this.deviceCode = deviceCode
        this.onConfirm = onConfirm
        this.onDismiss = onDismiss
        show(fragmentManager ,TAG)
    }

    fun addError(fragmentManager: FragmentManager, errorEntity: ErrorEntity, onConfirm: (() -> Unit) = {} , onDismiss : (() -> Unit) = {} ) {
        this.errorEntity = errorEntity
        this.onConfirm = onConfirm
        this.onDismiss = onDismiss
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
                message = getString(errorEntity.resolveMessage()) + codeMessage(),
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
        )

    private fun codeMessage() = "\n" + deviceCode?.user_code ?: ""


    private fun ErrorEntity?.resolveMessage() = when{
        this == null && deviceCode != null -> R.string.use_code
        this is ErrorEntity.Forbidden -> R.string.error_forbidden_message
        this is ErrorEntity.NetworkError -> R.string.error_network_message
        this is ErrorEntity.ValidationError -> R.string.error_validation_error
        else  -> R.string.error_unknown_error

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

