package br.com.freedomgist.gist

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.data.apiSource.network.utils.AUTH
import br.com.data.apiSource.network.utils.handleResult
import br.com.data.localSource.entity.GistFilter
import br.com.freedomgist.AuthViewModel
import br.com.freedomgist.R
import br.com.freedomgist.databinding.ActivityGistBinding
import br.com.freedomgist.dialog.ErrorDialog
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel


class GistActivity : FragmentActivity() {

    private lateinit var binding : ActivityGistBinding

    private val authViewModel : AuthViewModel by viewModel()

    private var errorDialog : ErrorDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gist)
        setupViewPager()
    }

    override fun onResume() {
        super.onResume()
        clearErrorDialog()
        if(AUTH.token == null) {
            when {
                AUTH.deviceCode == null -> authViewModel.fetchDeviceCode()
                AUTH.deviceCode != null -> authViewModel.fetchToken(AUTH.deviceCode!!)
            }
            observer()
        }
    }

    private fun observer(){
        errorDialog = ErrorDialog()
        authViewModel.deviceCode.observe( this){
            it.handleResult(
                success = { deviceCode ->
                    copyToClipBoard(deviceCode.user_code)
                    errorDialog?.deviceCode(supportFragmentManager,
                        deviceCode, onConfirm = ::openAuthIntent, onDismiss = ::clearErrorDialog)
                },
                error = { error ->
                    errorDialog?.addError(supportFragmentManager,
                    error, onConfirm = ::openAuthIntent, onDismiss = ::clearErrorDialog)}
            )
        }
    }

    private fun clearErrorDialog(){
        if(errorDialog?.isAdded === true)
            errorDialog?.dismiss()
        errorDialog = null
        authViewModel.deviceCode.removeObservers(this)
    }

    private fun openAuthIntent(){
        Log.d("ABACATE", "Open intent ")
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/device"))
        ContextCompat.startActivity(this, browserIntent, Bundle())
        clearErrorDialog()
    }

    private fun copyToClipBoard(text : String){
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }



    private fun setupViewPager()= with(binding){
        val pagerAdapter = GistFilterFragmentStateAdapter(this@GistActivity)
        pager.adapter = pagerAdapter
        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = GistFilter.values()[position].name
        }.attach()
    }

    private inner class GistFilterFragmentStateAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private val tabs = GistFilter.values()

        override fun getItemCount(): Int = tabs.size

        override fun createFragment(position: Int): Fragment =
            GistFragment.getInstance(tabs[position])

    }
}

