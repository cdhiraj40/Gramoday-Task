package com.example.gramodaytask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gramodaytask.R
import com.example.gramodaytask.model.RepositoryBranches

class RepositoryBranchAdapter(
    val context: Context,
    private val repositoryClickInterface: RepositoryClickInterface,
    private val allBranches: ArrayList<RepositoryBranches.RepositoryBranchNode>
) :
    RecyclerView.Adapter<RepositoryBranchAdapter.ViewHolder>() {

    interface RepositoryClickInterface {
        fun onRepositoryClick(
            repositoryBranch: RepositoryBranches.RepositoryBranchNode,
            repositoryBranchSHA: String
        )
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val branchTitle: TextView = itemView.findViewById(R.id.branch_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.repository_branch_item,
            parent, false
        )

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.branchTitle.text = allBranches[position].name
        holder.itemView.setOnClickListener {
            repositoryClickInterface.onRepositoryClick(
                allBranches[position],
                allBranches[position].commit?.sha.toString()
            )
        }
    }

    override fun getItemCount(): Int {
        return allBranches.size
    }
}
