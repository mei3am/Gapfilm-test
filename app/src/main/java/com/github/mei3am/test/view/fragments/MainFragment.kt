package com.github.mei3am.test.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mei3am.test.AppExecutors
import com.github.mei3am.test.R
import com.github.mei3am.test.databinding.MainFragmentBinding
import com.github.mei3am.test.interfaces.Injectable
import com.github.mei3am.test.utils.autoCleared
import com.github.mei3am.test.view.adaprers.FragmentViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject


class MainFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors
    var binding by autoCleared<MainFragmentBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        initUi()
    }

    private fun initUi(){
        val pagerAdapter = FragmentViewPagerAdapter(childFragmentManager, lifecycle)
        pagerAdapter.populateFragment(FavoriteFragment(), getString(R.string.favorites))
        pagerAdapter.populateFragment(ContentsFragment(), getString(R.string.contents))

        binding.viewPager.apply {
            adapter = pagerAdapter
            TabLayoutMediator(binding.tabs, this)
            { tab, position -> tab.text = pagerAdapter.getPageTitle(position) }.attach()
            offscreenPageLimit = 2
            post { setCurrentItem(1, false) }
        }
    }
}