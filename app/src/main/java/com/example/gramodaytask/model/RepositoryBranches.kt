package com.example.gramodaytask.model

import java.io.Serializable

class RepositoryBranches : ArrayList<RepositoryBranches.RepositoryBranchNode>() {

    class RepositoryBranchNode : Serializable {
        var name: String? = null
        var commit: CommitNode? = null

        class CommitNode {
            var sha: String? = null
            var url: String? = null
        }

        var protected: Boolean? = null
    }
}