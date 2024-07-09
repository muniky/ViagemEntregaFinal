package com.senac.navegacaoviagem.Model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.senac.navegacaoviagem.BaseDados.AppDataBase
import com.senac.navegacaoviagem.Dao.UsuarioDao
import com.senac.navegacaoviagem.toast
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class UsuarioViewModelFactory(val db: AppDataBase) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T{
        return UsuarioViewModel(db.usuarioDao) as T
    }
}
class UsuarioViewModel(val usuarioDao: UsuarioDao): ViewModel() {
    private val _uiState = MutableStateFlow(Usuario())
    val uiState: StateFlow<Usuario> = _uiState.asStateFlow()


    fun updateNome(nome: String) {
        _uiState.update {
            it.copy(nome = nome)
        }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun updateSenha(senha: String) {
        _uiState.update {
            it.copy(senha = senha)
        }
    }

    private fun updateId(id: Long) {
        _uiState.update {
            it.copy(id = id)
        }
    }

    private fun new() {
        _uiState.update {
            it.copy(id = 0, nome = "", email = "", senha = "")
        }
    }

    fun save(context: Context) {
        viewModelScope.launch {
            val nomeExistente = usuarioDao.ChecarNomeExistente(uiState.value.nome)
            if (nomeExistente > 0) {
                context.toast("Este nome de usuário já existe.")
            } else {
                val id = usuarioDao.upsert(uiState.value)
                if (id > 0) {
                    updateId(id)
                    context.toast("Usuário cadastrado com sucesso!")
                }
            }
        }
    }

    fun saveNew(context: Context) {
        save(context)
        new()
    }

    suspend fun login(nome: String, senha: String): Usuario? {
        val deferred: Deferred<Usuario?> = viewModelScope.async {
            usuarioDao.encontrarByUsuarioSenha(nome, senha)
        }
        return deferred.await()
    }

    suspend fun findById(id: Long): Usuario? {
        val deferred: Deferred<Usuario?> = viewModelScope.async {
            usuarioDao.findById(id)
        }
        return deferred.await()
    }

}