package com.example.gramodaytask.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gramodaytask.data.entity.RepositoryEntity

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(repositoryEntity: RepositoryEntity)

    @Delete
    suspend fun delete(repositoryEntity: RepositoryEntity)


    @Query("Select * from repositoryTable order by id ASC")
    fun getAllRepository(): LiveData<List<RepositoryEntity>>

    // below method is use to update the note.
    @Update
    suspend fun update(repositoryEntity: RepositoryEntity)
}
