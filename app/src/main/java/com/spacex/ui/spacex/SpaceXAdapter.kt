package com.spacex.ui.spacex

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spacex.R
import com.spacex.data.entities.Launch
import com.spacex.databinding.LaunchesItemBinding

class SpaceXAdapter() : ListAdapter<Launch, SpaceXAdapter.LaunchViewHolder>(DiffCallback) {

    class LaunchViewHolder(
        private var binding: LaunchesItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(launch: Launch) {
            binding.launch = launch
            //binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Launch>() {
        override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Launch, newItem: Launch): Boolean {
            return oldItem.flight_number == newItem.flight_number
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): LaunchViewHolder {

        return LaunchViewHolder(LaunchesItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: LaunchViewHolder,
        position: Int) {

        val launch = getItem(position)

        holder.itemView.setOnClickListener {showPopup(holder.itemView.context, launch, holder.itemView)}
        holder.bind(launch)
    }

    private fun showPopup(
        context: Context,
        launch: Launch,
        view: View) {

        val popup = PopupMenu(context, view)

        popup.inflate(R.menu.menu_links)

        if(launch.links?.article_link == null)
            popup.menu.findItem(R.id.action_article).isVisible = false
        if(launch.links?.wikipedia == null)
            popup.menu.findItem(R.id.action_wiki).isVisible = false
        if(launch.links?.video_link == null)
            popup.menu.findItem(R.id.action_video).isVisible = false

        popup.setOnMenuItemClickListener{ item: MenuItem? ->

            when (item?.itemId) {
                R.id.action_article -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(launch.links?.article_link)))
                R.id.action_wiki -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(launch.links?.wikipedia)))
                R.id.action_video -> context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(launch.links?.video_link)))
            }

            true
        }

        popup.show()
    }
}
