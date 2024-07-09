package com.senac.navegacaoviagem.Componentes

import Cadastro
import CadastroViagem
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.senac.navegacaoviagem.Login
import com.senac.navegacaoviagem.Model.Viagem

@Composable
fun Navegacao(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { Login(context = LocalContext.current, navController = navController) }
        composable("cadastro") { Cadastro(context = LocalContext.current, navController = navController) }
        composable("about") { About(navController = navController) }
        composable("cadastroviagem") { CadastroViagem(navController = navController) }
        composable(route= "menu/{usuario}", arguments = listOf(navArgument("usuario"){
            type = NavType.StringType })
        ) { backStackEntry ->
            val usuario = backStackEntry.arguments?.getString("usuario")
            NavigationBarS(navController = navController, usuario = usuario, viagem = Viagem())
        }
        composable("listaviagens"){ TelaViagem(navController = navController) }
        composable(route = "cadastroviagem/{viagemId}",
            arguments = listOf(navArgument("viagemId") { type = NavType.LongType })
        ) { backStackEntry ->
            val viagemId = backStackEntry.arguments?.getLong("viagemId")
            CadastroViagem(navController = navController, viagemId = viagemId)
        }
    }
}
