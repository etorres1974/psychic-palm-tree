package br.com.freedomgist.gist.file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.com.freedomgist.databinding.FragmentFileBinding
import java.lang.IllegalArgumentException

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
        setupCodeView(getCodeData())

    }

    private fun setupCodeView(code: CodeData) = with(binding){
        tvCodeUrl.text = code.url
        code.content?.let{
            codeView.setCode(code = code.content, language = code.language)
        }
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