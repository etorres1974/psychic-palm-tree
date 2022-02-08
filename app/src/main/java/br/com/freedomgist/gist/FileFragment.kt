package br.com.freedomgist.gist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.freedomgist.databinding.FragmentGistPageBinding

class FileFragment : Fragment() {

    private lateinit var binding : FragmentGistPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGistPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}