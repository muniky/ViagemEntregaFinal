package com.senac.navegacaoviagem.Componentes

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.senac.navegacaoviagem.Model.TipoViagem

@Composable
fun DestinationInput(destination: String, onDestinationChanged: (String) -> Unit) {
    OutlinedTextField(
        value = destination,
        onValueChange = onDestinationChanged,
        label = { Text("Destino") }
    )
}

@Composable
fun TripTypeInput(tripType: TipoViagem, onTripTypeChanged: (TipoViagem) -> Unit) {
    Row {
        TipoViagem.values().forEach {type ->
            Row {
                RadioButton(
                    selected = tripType == type,
                    onClick = { onTripTypeChanged(type) }
                )
                Text(type.name)
            }
        }
    }
}

@Composable
fun ValueInput(value: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = {Text("Orçamento") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}