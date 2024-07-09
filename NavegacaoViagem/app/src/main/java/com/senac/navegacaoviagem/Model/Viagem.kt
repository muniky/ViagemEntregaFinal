package com.senac.navegacaoviagem.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
@Entity
data class Viagem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var destino: String = "",
    var tipo: TipoViagem = TipoViagem.LAZER,
    var DataInicial: Date = Date(),
    var DataFinal: Date = Date(),
    var Valor: Double = 0.0
)

enum class TipoViagem {
    LAZER, NEGOCIOS
}