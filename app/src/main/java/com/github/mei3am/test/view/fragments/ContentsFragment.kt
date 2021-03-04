package com.github.mei3am.test.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.github.mei3am.test.AppExecutors
import com.github.mei3am.test.R
import com.github.mei3am.test.databinding.ContentsFragmentBinding
import com.github.mei3am.test.interfaces.Injectable
import com.github.mei3am.test.utils.autoCleared
import com.github.mei3am.test.viewModel.ContentsViewModel
import javax.inject.Inject


class ContentsFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors
    var binding by autoCleared<ContentsFragmentBinding>()
    private val viewModel: ContentsViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.contents_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

    }

}