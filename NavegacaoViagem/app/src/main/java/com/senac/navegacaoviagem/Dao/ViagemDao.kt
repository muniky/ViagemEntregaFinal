package com.senac.navegacaoviagem.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.senac.navegacaoviagem.Model.Viagem
import kotlinx.coroutines.flow.Flow

@Dao
interface ViagemDao {
    @Insert
    fun insert(viagem: Viagem): Long

    @Update
    fun update(viagem: Viagem)

    @Upsert
    suspend fun upsert(viagem: Viagem): Long

    @Query("select * from viagem order by viagem.DataInicial")
    fun getAll(): Flow<List<Viagem>>

    @Query("select * from viagem where viagem.id = :id")
    suspend fun findById(id: Long) : Viagem?

    @Delete
    suspend fun delete(viagem: Viagem)
}