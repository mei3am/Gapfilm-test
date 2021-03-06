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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mei3am.test.AppExecutors
import com.github.mei3am.test.R
import com.github.mei3am.test.binding.FragmentDataBindingComponent
import com.github.mei3am.test.constants.Constants
import com.github.mei3am.test.constants.Status
import com.github.mei3am.test.databinding.ContentsFragmentBinding
import com.github.mei3am.test.interfaces.Injectable
import com.github.mei3am.test.models.response.Content
import com.github.mei3am.test.utils.*
import com.github.mei3am.test.view.adaprers.ContentListAdapter
import com.github.mei3am.test.viewModel.ContentsViewModel
import javax.inject.Inject


class ContentsFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors
    private var binding by autoCleared<ContentsFragmentBinding>()
    private var adapter by autoCleared<ContentListAdapter>()
    private val viewModel: ContentsViewModel by viewModels {
        viewModelFactory
    }
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
        if (viewModel.contentList.size == 0){
            loadNextPage()
        }else{
            adapter.submitList(viewModel.contentList)
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.get<Int>(Constants.CHANGED_POSITION)?.let {
            viewModel.contentList[it].favorite = (viewModel.contentList[it].favorite ?: false).not()
            adapter.notifyItemChanged(it, viewModel.contentList[it])
        }
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.contentList.isNotEmpty()){
            loadListFromDb()
        }
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
                {   // favorite call back
                    content, position, isChecked ->
                    if (isChecked){
                        insert(content, position)
                    }else{
                        delete(content, position)
                    }
                }
        )
        binding.rvContent.adapter = rvAdapter
        this.adapter = rvAdapter
        binding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1 && !viewModel.isLoadData) {
                    loadNextPage()
                }
            }
        })
    }

    private fun loadNextPage(){
        viewModel.contentQuery().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progress.root.gone()
                        val pastSize = adapter.itemCount
                        adapter.submitList(viewModel.contentList)
                        adapter.notifyItemRangeInserted(pastSize, viewModel.contentList.size)
                    }
                    Status.ERROR -> {
                        binding.progress.root.gone()
                        toast(R.string.error_in_connection)
                    }
                    Status.LOADING -> {
                        binding.progress.root.visible()

                    }

                }
            }
        })
    }

    private fun delete(content: Content, position: Int){
        viewModel.deleteFromFavoriteDbQuery(content.contentId, position).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        adapter.submitList(resource.data)
                        adapter.notifyItemChanged(position, content)
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun insert(content: Content, position: Int){
        content.favorite = true
        viewModel.insertToFavoriteDbQuery(content, position).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        adapter.submitList(resource.data)
                        adapter.notifyItemChanged(position, content)
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun loadListFromDb(){
        viewModel.loadDbQuery().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        adapter.submitList(viewModel.contentList)
                        resource.data?.forEach { changedPosition ->
                            adapter.notifyItemChanged(changedPosition)
                        }
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }
}