package com.senac.navegacaoviagem.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.senac.navegacaoviagem.BaseDados.AppDataBase
import com.senac.navegacaoviagem.Dao.ViagemDao
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date


class ViagemViewModelFactory(val db: AppDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return ViagemViewModel(db.viagemDao) as T
    }
}

class ViagemViewModel (val viagemDao: ViagemDao): ViewModel() {
    private val _uiState = MutableStateFlow(Viagem())
    val uiState: StateFlow<Viagem> = _uiState.asStateFlow()

    fun updateDestino(destino: String) {
        _uiState.update { it.copy(destino = destino) }
    }

    fun updateTipoViagem(TipoViagem: TipoViagem) {
        _uiState.update { it.copy(tipo = TipoViagem) }
    }

    fun updateDataInicial(DataInicial: Date) {
        _uiState.update { it.copy(DataInicial = DataInicial) }
    }

    fun updateDataFinal(DataFinal: Date) {
        _uiState.update { it.copy(DataFinal = DataFinal) }
    }

    fun updateValor(valor: Double) {
        _uiState.update { it.copy(Valor = valor) }
    }

    private fun updateId(id: Long){
        _uiState.update { it.copy(id = id) }
    }

    fun save() {
        viewModelScope.launch {
            val id = viagemDao.upsert(uiState.value)
            if (id > 0) {
                updateId(id)
            }
        }
    }
    fun deleteViagem(viagem: Viagem){
        viewModelScope.launch {
            viagemDao.delete(viagem)
        }
    }

    fun editarViagem(id: Long){
        viewModelScope.launch {
            val viagem = viagemDao.findById(id)
            _uiState.value = viagem ?: Viagem()
        }
    }


    private fun new(){
        _uiState.update{
            val copy = it.copy(id = 0,
                tipo = TipoViagem.LAZER,
                destino = "",
                DataInicial = Date(),
                DataFinal = Date(),
                Valor = 0.0
            )
            copy
        }
    }

    fun saveNew(){
        save()
        new()
    }
    fun getAll() = viagemDao.getAll()

    suspend fun findById(id: Long): Viagem?{
        val deferred: Deferred<Viagem?> = viewModelScope.async {
            viagemDao.findById(id)
        }
        return deferred.await()
    }

}