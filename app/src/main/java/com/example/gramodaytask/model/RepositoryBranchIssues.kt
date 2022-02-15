package com.example.gramodaytask.model

import java.io.Serializable

class RepositoryBranchIssues : ArrayList<RepositoryBranchIssues.RepositoryBranchNode>() {

    class RepositoryBranchNode : Serializable {
        var html_url: String? = null
        var title: String? = null
        var user: UserNode? = null

        class UserNode {
            var login: String? = null
            var avatar_url: String? = null
            var html_url: String? = null
        }

        var pull_request: PullRequestNode? = null

        class PullRequestNode {
            var html_url: String? = null
        }
    }
}