package com.example.gramodaytask.model

import java.io.Serializable

class RepositoryBranchCommits : ArrayList<RepositoryBranchCommits.RepositoryBranchNode>() {

    class RepositoryBranchNode : Serializable {
        var sha: String? = null
        var commit: CommitNode? = null

        class CommitNode {
            var author: AuthorNode? = null

            class AuthorNode {
                var name: String? = null
                var email: String? = null
                var date: String? = null
            }

            var committer: CommitterNode? = null

            class CommitterNode {
                var name: String? = null
                var email: String? = null
                var date: String? = null
            }

            var message: String? = null

            var tree: TreeNode? = null

            class TreeNode {
                var sha: String? = null
            }
        }

        var author: AuthorNode? = null

        class AuthorNode {
            var login: String? = null
            var avatar_url: String? = null
            var html_url: String? = null
        }

    }
}