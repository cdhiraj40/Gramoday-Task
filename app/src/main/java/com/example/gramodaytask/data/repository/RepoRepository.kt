package com.example.gramodaytask.data.repository

import androidx.lifecycle.LiveData
import com.example.gramodaytask.data.dao.RepositoryDao
import com.example.gramodaytask.data.entity.RepositoryEntity

class RepoRepository(private val repositoryDao: RepositoryDao) {

    val allRepositoryEntity: LiveData<List<RepositoryEntity>> = repositoryDao.getAllRepository()

    suspend fun insert(note: RepositoryEntity) {
        repositoryDao.insert(note)
    }

    suspend fun delete(note: RepositoryEntity) {
        repositoryDao.delete(note)
    }

    suspend fun update(note: RepositoryEntity) {
        repositoryDao.update(note)
    }
}
