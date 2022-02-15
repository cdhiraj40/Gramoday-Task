package com.example.gramodaytask.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositoryTable")
class RepositoryEntity(
    @ColumnInfo(name = "owner")
    val repositoryOwner: String,
    @ColumnInfo(name = "title")
    val repositoryTitle: String,
    @ColumnInfo(name = "description")
    val repositoryDescription: String,
    @ColumnInfo(name = "url")
    val repositoryUrl: String,
    @ColumnInfo(name = "open_issues_count")
    val openIssuesCount: Int

) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}

