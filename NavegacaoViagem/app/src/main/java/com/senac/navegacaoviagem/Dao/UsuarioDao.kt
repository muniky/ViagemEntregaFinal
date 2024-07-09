package com.senac.navegacaoviagem.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.senac.navegacaoviagem.Model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
        fun insert(usuario: Usuario): Long

    @Update
    fun update(usuario: Usuario)

    @Upsert
    suspend fun upsert(usuario: Usuario): Long

    @Query("select * from usuario order by usuario.nome")
    fun getAll(): Flow<List<Usuario>>

    @Query("select * from usuario where usuario.id = :id")
    suspend fun findById(id: Long) : Usuario?

    @Query("select * from usuario where nome = :nome and senha = :senha")
    suspend fun encontrarByUsuarioSenha(nome: String, senha: String): Usuario?

    @Query("select count(*) from usuario where nome = :nome")
    suspend fun ChecarNomeExistente(nome: String): Int

    @Delete
    suspend fun delete(usuario: Usuario)
}