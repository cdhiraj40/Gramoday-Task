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
import com.example.gramodaytask.model.RepositoryBranchCommits
import com.example.gramodaytask.utils.DateUtils.getDate
import com.example.gramodaytask.utils.DateUtils.parseISO8601Date
import java.util.*

class RepositoryBranchCommitsAdapter(
    val context: Context,
    private val repositoryClickInterface: RepositoryClickInterface,
    private val allCommits: ArrayList<RepositoryBranchCommits.RepositoryBranchNode>
) :
    RecyclerView.Adapter<RepositoryBranchCommitsAdapter.ViewHolder>() {

    interface RepositoryClickInterface {
        fun onRepositoryClick(branchCommit: RepositoryBranchCommits.RepositoryBranchNode)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val commitDate: TextView = itemView.findViewById(R.id.commit_date)
        val commitSHA: TextView = itemView.findViewById(R.id.commit_sha)
        val commitMessage: TextView = itemView.findViewById(R.id.commit_message)
        val commitAuthor: TextView = itemView.findViewById(R.id.commit_author)
        val commitAuthorAvatar: ImageView = itemView.findViewById(R.id.commit_author_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.repository_branch_commit_item,
            parent, false
        )

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.commitDate.text =
            getDate(parseISO8601Date(allCommits[position].commit?.author?.date!!))


        holder.commitSHA.text = allCommits[position].commit?.tree?.sha
        holder.commitAuthor.text = allCommits[position].author?.login

        var commitMessage = allCommits[position].commit?.message

        commitMessage = commitMessage?.replace("\\", "\\")
        holder.commitMessage.text = commitMessage

        Glide.with(context)
            .load(allCommits[position].author?.avatar_url)
            .circleCrop()
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .into(holder.commitAuthorAvatar)

        holder.itemView.setOnClickListener {
            repositoryClickInterface.onRepositoryClick(allCommits[position])
        }
    }

    override fun getItemCount(): Int {
        return allCommits.size
    }
}
