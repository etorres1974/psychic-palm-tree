package br.com.freedomgist.gist.file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.freedomgist.databinding.FragmentFileBinding
import br.com.freedomgist.databinding.FragmentGistPageBinding

class FileFragment(private val code : String) : Fragment() {

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
        binding.codeView.setCode(code)
    }
}