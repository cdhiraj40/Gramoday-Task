package com.example.gramodaytask.data.viewModal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gramodaytask.data.database.RepositoryDatabase
import com.example.gramodaytask.data.entity.RepositoryEntity
import com.example.gramodaytask.data.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryViewModal(application: Application) : AndroidViewModel(application) {

    val allRepositoryEntity: LiveData<List<RepositoryEntity>>
    val repository: RepoRepository

    init {
        val dao = RepositoryDatabase.getDatabase(application).getRepositoryDao()
        repository = RepoRepository(dao)
        allRepositoryEntity = repository.allRepositoryEntity
    }

    fun deleteRepository(note: RepositoryEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun updateRepository(note: RepositoryEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun addRepository(note: RepositoryEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    suspend fun getRepository(id: Int): RepositoryEntity {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRepository(id)
        }
        return repository.getRepository(id)
    }
}
