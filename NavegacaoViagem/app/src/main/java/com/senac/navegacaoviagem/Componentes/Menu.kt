package com.senac.navegacaoviagem.Componentes

import Cadastro
import CadastroViagem
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.ui.Modifier
import com.senac.navegacaoviagem.Model.Viagem

@Composable
fun NavigationBarS(navController: NavController, usuario: String?, viagem: Viagem?){
    val navController = rememberNavController()
    Scaffold (
        bottomBar = { NavigationBottom(navController, usuario) }
    ) { innerPadding ->
        NavHost(navController,
            startDestination = "home/$usuario",
            modifier = Modifier.padding(innerPadding)
        ){
            composable (route = "home/{usuario}", arguments = listOf(navArgument("usuario") { type = NavType.StringType })
            ) { backStackEntry ->
                val usuarioArg = backStackEntry.arguments?.getString("usuario")
                Home(navController = navController, usuario = usuario?:usuarioArg)
            }
            composable("listaviagens") { TelaViagem(navController = navController) }
            composable("about") { About(navController = navController) }
            composable("cadastroviagem") { CadastroViagem(navController = navController)}
            composable(route = "cadastroviagem/{viagemId}",
                arguments = listOf(navArgument("viagemId") { type = NavType.LongType })
            ) { backStackEntry ->
                val viagemId = backStackEntry.arguments?.getLong("viagemId")
                CadastroViagem(navController = navController, viagemId = viagemId)

            }
        }
    }
}

@Composable
fun NavigationBottom(navController: NavController, usuario: String?){
    BottomAppBar (
        modifier = Modifier,
        containerColor = Color.Magenta,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        tonalElevation = 8.dp,
        windowInsets = NavigationBarDefaults.windowInsets
    ){
        var selectedItem by remember { mutableStateOf(0) }

        BottomNavigationItem(
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
                navController.navigate("home/$usuario")
            },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text(text = "Home")},
        )

        BottomNavigationItem(
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
                navController.navigate("listaviagens")
            },
            icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Viagens") },
            label = { Text(text = "Viagens")}
        )

        BottomNavigationItem(
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
                navController.navigate("about")
            },
            icon = { Icon(Icons.Filled.Info, contentDescription = "Sobre") },
            label = { Text(text = "Sobre") }
        )

    }
}
