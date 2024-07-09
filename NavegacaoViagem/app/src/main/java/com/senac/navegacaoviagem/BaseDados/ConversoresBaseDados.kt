package com.senac.navegacaoviagem.BaseDados

import androidx.room.TypeConverter
import com.senac.navegacaoviagem.Model.TipoViagem
import java.util.Date

class ConversoresBaseDados {
    @TypeConverter
    fun intToTripType(value: Int?): TipoViagem? {
        return TipoViagem.values().firstOrNull() { it.ordinal == value}
    }

    @TypeConverter
    fun tripTypeToInt(tipo: TipoViagem): Int {
        return tipo.ordinal
    }


    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}