package com.github.mei3am.test.view.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mei3am.test.AppExecutors
import com.github.mei3am.test.R
import com.github.mei3am.test.binding.FragmentDataBindingComponent
import com.github.mei3am.test.constants.Constants
import com.github.mei3am.test.constants.Status
import com.github.mei3am.test.databinding.ContentDetailsFragmentBinding
import com.github.mei3am.test.interfaces.Injectable
import com.github.mei3am.test.models.response.Content
import com.github.mei3am.test.models.response.ContentDetailsResponse
import com.github.mei3am.test.utils.autoCleared
import com.github.mei3am.test.utils.toast
import com.github.mei3am.test.viewModel.ContentsDetailsViewModel
import javax.inject.Inject
import kotlin.math.roundToInt


class ContentDetailsFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors
    var binding by autoCleared<ContentDetailsFragmentBinding>()
    private val params by navArgs<ContentDetailsFragmentArgs>()
    private val viewModel: ContentsDetailsViewModel by viewModels {
        viewModelFactory
    }
    private val dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private var isFavoriteChanged = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.content_details_fragment, container,
                false, dataBindingComponent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        initUi()
        initClickListener()
        details()
        handleBackButton()
    }

    private fun initUi(){
        binding.cbFavorite.apply {
            isChecked = params.favorite
            setOnCheckedChangeListener { _, isChecked ->
                isFavoriteChanged = !isFavoriteChanged
                if (isChecked){
                    insert()
                }else{
                    delete()
                }
            }
        }
    }

    private fun initClickListener(){
        binding.ibBack.setOnClickListener {
            back()
        }
    }

    private fun handleBackButton(){
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun back(){
        if (isFavoriteChanged){
            findNavController().previousBackStackEntry?.savedStateHandle?.set(Constants.CHANGED_POSITION,
                    params.position)
        }
        findNavController().popBackStack()
    }

    private fun details(){
        viewModel.detailsQuery(params.contentId).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progress.root.visibility = View.GONE
                        binding.cbFavorite.visibility = View.VISIBLE
                        setUi(resource.data)
                    }
                    Status.ERROR -> {
                        binding.progress.root.visibility = View.GONE
                        toast(R.string.error_in_connection)
                    }
                    Status.LOADING -> {
                        binding.progress.root.visibility = View.VISIBLE
                        binding.cbFavorite.visibility = View.GONE

                    }
                }
            }
        })
    }

    private fun setUi(data: ContentDetailsResponse?){
        data?.result?.let {
            binding.landScapeImage = it.landscapeImage
            binding.ivLandScape.apply {
                //force a 16:9 aspect ratio
                val width = measuredWidth
                val height = (width * .5625f).roundToInt()
                val lp = layoutParams
                lp.height = height
                lp.width = width
                layoutParams = lp
            }
            binding.tvTitle.text = it.title
            binding.tvSummary.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               Html.fromHtml(it.summary, Html.FROM_HTML_MODE_COMPACT);
            } else {
                Html.fromHtml(it.summary);
            }
        }
    }

    private fun delete(){
        viewModel.deleteFromDbQuery(params.contentId).observe(viewLifecycleOwner, {})
    }

    private fun insert(){
        viewModel.contentResult?.let {
            val content = Content(
                    contentId = it.contentID!!,
                    title = it.title!!,
                    zoneId = it.zoneID,
                    thumbImage = it.thumbImage!!,
                    favorite = true
            )
            viewModel.insertToFavoriteDbQuery(content).observe(viewLifecycleOwner, {})
        }

    }
}