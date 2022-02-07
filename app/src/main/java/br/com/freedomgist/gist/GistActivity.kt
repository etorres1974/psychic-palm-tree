package br.com.freedomgist.gist

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.data.localSource.entity.GistFilter
import br.com.freedomgist.R
import br.com.freedomgist.databinding.ActivityGistBinding
import com.google.android.material.tabs.TabLayoutMediator


class GistActivity : FragmentActivity() {

    private lateinit var binding : ActivityGistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gist)
        setupViewPager()
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
            GistFragment(tabs[position])

    }
}

