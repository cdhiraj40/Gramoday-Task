package com.example.gramodaytask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gramodaytask.R
import com.example.gramodaytask.data.entity.RepositoryEntity
import com.example.gramodaytask.utils.extensions.shareLink

class RepositoryListAdapter(
    val context: Context,
//    private val repositoryClickDeleteInterface: RepositoryClickDeleteInterface,
    private val repositoryClickInterface: RepositoryClickInterface
) :
    RecyclerView.Adapter<RepositoryListAdapter.ViewHolder>() {

//    interface RepositoryClickDeleteInterface {
//        fun onDeleteIconClick(repository: RepositoryEntity)
//    }

    interface RepositoryClickInterface {
        fun onRepositoryClick(repository: RepositoryEntity)
    }

    private val allRepositorys = ArrayList<RepositoryEntity>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val repositoryTitle: TextView = itemView.findViewById(R.id.repository_title)
        val repositoryDescription: TextView =
            itemView.findViewById(R.id.repository_description)
        val repositoryshareIcon: ImageView = itemView.findViewById(R.id.share_repository)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.repository_list_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.repositoryTitle.text = allRepositorys[position].repositoryTitle
        holder.repositoryDescription.text = allRepositorys[position].repositoryDescription

        holder.repositoryshareIcon.setOnClickListener {
            context.shareLink(allRepositorys[position].repositoryUrl, "Repository Link")
        }
        holder.itemView.setOnClickListener {
            repositoryClickInterface.onRepositoryClick(allRepositorys[position])
        }
    }

    override fun getItemCount(): Int {
        return allRepositorys.size
    }

    fun getDataItemCount(): Int {
        return allRepositorys.size
    }

    fun updateList(newList: List<RepositoryEntity>) {
        allRepositorys.clear()
        allRepositorys.addAll(newList)
        notifyDataSetChanged()
    }
}
