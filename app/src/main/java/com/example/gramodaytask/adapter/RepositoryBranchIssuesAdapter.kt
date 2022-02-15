package com.example.gramodaytask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gramodaytask.R
import com.example.gramodaytask.model.RepositoryBranchIssues
import java.util.*
import kotlin.math.abs

class RepositoryBranchIssuesAdapter(
    val context: Context,
    private val repositoryClickInterface: RepositoryClickInterface,
    private val allIssues: ArrayList<RepositoryBranchIssues.RepositoryBranchNode>
) :
    RecyclerView.Adapter<RepositoryBranchIssuesAdapter.ViewHolder>() {

    interface RepositoryClickInterface {
        fun onRepositoryClick(branchIssue: RepositoryBranchIssues.RepositoryBranchNode)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val issueTitle: TextView = itemView.findViewById(R.id.issue_title)
        val issueAuthor: TextView = itemView.findViewById(R.id.issue_author)
        val issueAuthorAvatar: ImageView = itemView.findViewById(R.id.issue_author_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.repository_branch_issue_item,
            parent, false
        )

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (allIssues[position].pull_request == null) {
            holder.issueTitle.text =
                allIssues[position].title


            holder.issueAuthor.text = allIssues[position].user?.login

            Glide.with(context)
                .load(allIssues[position].user?.avatar_url)
                .circleCrop()
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .into(holder.issueAuthorAvatar)

            holder.itemView.setOnClickListener {
                repositoryClickInterface.onRepositoryClick(allIssues[position])
            }
        } else {
            /**
             * GitHub's REST API v3 considers every pull request an issue, but we can identify pull requests by the pull_request key.
             */
            allIssues.removeAt(abs(position - allIssues.size - 1))
//            holder.itemView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return allIssues.size
    }
}
