package com.example.gramodaytask.model

import java.io.Serializable

class Repository : Serializable {
    val id: Int? = null
    val owner: OwnerNode? = null
    val name: String? = null
    val description: String? = null
    val full_name: String? = null
    val url: String? = null
    val html_url: String? = null

    class OwnerNode : Serializable {
        val avatar_url: String? = null
        val id: Int? = null
        val repos_url: String? = null
    }
}