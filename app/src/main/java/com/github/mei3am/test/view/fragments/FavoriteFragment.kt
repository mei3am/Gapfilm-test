package com.github.mei3am.test.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mei3am.test.AppExecutors
import com.github.mei3am.test.R
import com.github.mei3am.test.binding.FragmentDataBindingComponent
import com.github.mei3am.test.constants.Status
import com.github.mei3am.test.databinding.ContentsFragmentBinding
import com.github.mei3am.test.interfaces.Injectable
import com.github.mei3am.test.models.response.Content
import com.github.mei3am.test.utils.Klog
import com.github.mei3am.test.utils.autoCleared
import com.github.mei3am.test.utils.toast
import com.github.mei3am.test.view.adaprers.ContentListAdapter
import com.github.mei3am.test.viewModel.FavoriteViewModel
import javax.inject.Inject


class FavoriteFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors
    var binding by autoCleared<ContentsFragmentBinding>()
    private val viewModel: FavoriteViewModel by viewModels {
        viewModelFactory
    }
    private var adapter by autoCleared<ContentListAdapter>()
    private val dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

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
        initRv()
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }

    private fun initRv(){
        val rvAdapter = ContentListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors,
         { // on item click
            content, favorite, position ->
            findNavController().navigate(
                    MainFragmentDirections.
                    showContentDetailsFragment(
                            contentId = content.contentId,
                            favorite = favorite,
                            position = position))
        },
        { // favorite call back
            content, position, _ ->
            delete(content.contentId, position)
        }
        )
        binding.rvContent.adapter = rvAdapter
        this.adapter = rvAdapter
    }

    private fun loadList(){
        viewModel.loadFromDbQuery().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progress.root.visibility = View.GONE
                        adapter.submitList(viewModel.contentList)
                        adapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        binding.progress.root.visibility = View.GONE
                        toast(R.string.error_in_connection)
                    }
                    Status.LOADING -> {
                        binding.progress.root.visibility = View.VISIBLE

                    }

                }
            }
        })
    }

    private fun delete(contentId: Int, position: Int){
        viewModel.deleteFromDbQuery(contentId, position).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        adapter.submitList(resource.data)
                        adapter.notifyItemRemoved(position)
                    }
                    Status.ERROR -> {
                        toast(R.string.error)
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

}