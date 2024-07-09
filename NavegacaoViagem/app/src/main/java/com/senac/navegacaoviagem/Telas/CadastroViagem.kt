import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.senac.navegacaoviagem.BaseDados.AppDataBase
import com.senac.navegacaoviagem.Componentes.DatePickerComponent
import com.senac.navegacaoviagem.Componentes.DestinationInput
import com.senac.navegacaoviagem.Componentes.TripTypeInput
import com.senac.navegacaoviagem.Componentes.ValueInput
import com.senac.navegacaoviagem.Model.TipoViagem
import com.senac.navegacaoviagem.Model.Viagem
import com.senac.navegacaoviagem.Model.ViagemViewModel
import com.senac.navegacaoviagem.Model.ViagemViewModelFactory
import java.util.Date

@Composable

fun CadastroViagem(navController: NavController, viagemId: Long? = null){
    val snackbarHostState = remember { SnackbarHostState()}
    val viagem = remember {
        mutableStateOf(
            Viagem(
                destino = "",
                tipo = TipoViagem.LAZER,
                DataInicial = Date(),
                DataFinal = Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000),
                Valor = 0.0
            )
        )
    }

    val ctx = LocalContext.current
    val db = AppDataBase.getDataBase(ctx)
    val viagemViewModel: ViagemViewModel = viewModel (
        factory = ViagemViewModelFactory(db)
    )
    val state = viagemViewModel.uiState.collectAsState()
    LaunchedEffect(viagemId) {
        viagemId?.let {
            viagemViewModel.editarViagem(it)
        }
    }
    Scaffold (
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Magenta.copy(alpha = 0.1f))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (viagemId == null) "Cadastrar Nova Viagem" else "Editar Viagem",
                            modifier = Modifier.weight(0.1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    DestinationInput(
                        destination = state.value.destino,
                        onDestinationChanged = { viagemViewModel.updateDestino(it) }
                    )
                    TripTypeInput(
                        tripType = state.value.tipo,
                        onTripTypeChanged = { viagemViewModel.updateTipoViagem(it) }
                    )
                    ValueInput(
                        value = formatValue(state.value.Valor),
                        onValueChanged = {
                            val doubleValue = it.toDoubleOrNull() ?: 0.0
                            viagemViewModel.updateValor(doubleValue)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DatePickerComponent(
                        label = "Data Início",
                        date = state.value.DataInicial,
                        onDateSelected = { viagemViewModel.updateDataInicial(it) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DatePickerComponent(
                        label = "Data Final",
                        date = state.value.DataFinal,
                        onDateSelected = { viagemViewModel.updateDataFinal(it) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (state.value.destino == ""){
                                Toast.makeText(ctx, "Informe um destino.", Toast.LENGTH_SHORT).show()
                            } else {
                                viagemViewModel.save()
                                Toast.makeText(ctx, "Viagem salva!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Salvar Viagem")
                    }
                }
            }
        }
    )
}

@Composable
fun formatValue(value: Double): String {
    val intValue = value.toInt()
    return if (value == intValue.toDouble()) {
        intValue.toString()
    } else {
        value.toString()
    }
}