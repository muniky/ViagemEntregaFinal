package com.senac.navegacaoviagem.BaseDados

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.senac.navegacaoviagem.Dao.UsuarioDao
import com.senac.navegacaoviagem.Dao.ViagemDao
import com.senac.navegacaoviagem.Model.Usuario
import com.senac.navegacaoviagem.Model.Viagem

@Database(entities = [Usuario::class, Viagem::class], version = 1, exportSchema = false)
@TypeConverters(ConversoresBaseDados::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val usuarioDao : UsuarioDao
    abstract val viagemDao : ViagemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase = INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context, AppDataBase::class.java,
                "user-db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}