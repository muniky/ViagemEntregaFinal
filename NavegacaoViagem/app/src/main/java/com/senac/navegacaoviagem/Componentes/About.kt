package com.senac.navegacaoviagem.Componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier

@Composable
fun About(navController: NavController){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Desenvolvedor: Muniky\n" +
                    "E-mail: Muniky@gmail.com\n" +
                    "Versão do aplicativo: 10", fontSize = 24.sp)
    }
}