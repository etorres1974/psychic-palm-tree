package br.com.freedomgist.gist.file

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.com.freedomgist.databinding.FragmentFileBinding

class FileFragment() : Fragment() {

    private lateinit var binding : FragmentFileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupCodeView(getCodeData())
    }

    private fun getCodeData() = arguments?.getSerializable(CODE_DATA) as? CodeData ?: throw IllegalArgumentException()

    companion object{
        private const val CODE_DATA = "CODE_DATA"
        fun getInstance(code : CodeData): FileFragment {
            val args = Bundle().apply { putSerializable(CODE_DATA, code) }
            return FileFragment().apply { arguments = args }
        }
    }
}

fun FragmentFileBinding.setupCodeView(code: CodeData){
    tvCodeUrl.isVisible = code.content == null
    tvEmpty.isVisible =  code.content == null
    codeView.isVisible = code.content != null

    tvCodeUrl.text = code.url
    tvCodeUrl.setOnClickListener {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(code.url))
        startActivity(it.context, browserIntent, Bundle())
    }
    code.content?.let{
        codeView.text = it
    }
}

