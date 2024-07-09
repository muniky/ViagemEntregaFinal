package com.senac.navegacaoviagem.Componentes

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.senac.navegacaoviagem.BaseDados.AppDataBase
import com.senac.navegacaoviagem.Model.TipoViagem
import com.senac.navegacaoviagem.Model.Viagem
import com.senac.navegacaoviagem.Model.ViagemViewModel
import com.senac.navegacaoviagem.Model.ViagemViewModelFactory
import com.senac.navegacaoviagem.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
   fun TelaViagem(navController: NavController) {
    val ctx = LocalContext.current
    val db = AppDataBase.getDataBase(ctx)
    val viagemViewModel : ViagemViewModel = viewModel (
        factory = ViagemViewModelFactory(db)
    )
    val viagemItems = viagemViewModel.getAll().collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "cadastroviagem") },
                containerColor = Color.Magenta,
                contentColor = Color(0xFFd4262c)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Viagem")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Minhas Viagens",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(items = viagemItems.value) { viagem ->
                    TripCard(viagem, viagemViewModel, navController)
                }
            }
        }
    }
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TripCard(viagem: Viagem, viagemViewModel: ViagemViewModel, navController: NavController) {
    val ctx = LocalContext.current
    val tripImage = if (viagem.tipo == TipoViagem.LAZER) {
        painterResource(id = R.drawable.viagem)
    } else {
        painterResource(id = R.drawable.maleta)
    }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false },
            title = { Text("Excluir Viagem") },
            text = { Text("Você tem certeza que deseja excluir a viagem?") },
            confirmButton = {
                Button(onClick = {
                    viagemViewModel.deleteViagem(viagem)
                    showDialog = false
                    Toast.makeText(ctx, "Viagem excluída!", Toast.LENGTH_SHORT).show()
                }
                ) {
                    Text("Excluir viagem")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    navController.navigate("cadastroviagem/${viagem.id}")
                },
                onLongClick = {
                    showDialog = true
                }
            )
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = tripImage,
                        contentDescription = "Tipo Viagem",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = viagem.destino,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = "Data de Início: ${dateFormat.format(viagem.DataInicial)}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Data Final: ${dateFormat.format(viagem.DataFinal)}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Valor: R$ ${viagem.Valor}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}