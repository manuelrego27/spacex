package com.spacex.ui.spacex

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.spacex.R
import com.spacex.data.entities.CompanyInfo
import com.spacex.data.entities.Launch
import com.spacex.utils.Resource
import timber.log.Timber

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    resource: Resource<List<Launch>>?) {
    val adapter = recyclerView.adapter as SpaceXAdapter

    resource?.let {
        if (!it.data.isNullOrEmpty())adapter.submitList(it.data)
    }
}

@BindingAdapter("companyInfo")
fun bindCompanyInfo(view: TextView,
                    resource: Resource<CompanyInfo>?){
    resource?.let {
        view.text = it.data?.summaryString ?: "Loading"
    }
}

@BindingAdapter("spinner")
fun bindProgressBar(
    view: View,
    resource: Resource<List<Launch>>?){
    resource?.let {
        when (it.status) {
            Resource.Status.SUCCESS -> view.visibility = View.GONE
            Resource.Status.ERROR -> Timber.e(resource.message)
            Resource.Status.LOADING -> view.visibility = View.VISIBLE
        }
    }
}


@BindingAdapter("imageUrl")
fun bindImage(
    imgView: ImageView,
    imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .centerCrop()
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}