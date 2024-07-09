package com.senac.navegacaoviagem

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.senac.navegacaoviagem.BaseDados.AppDataBase
import com.senac.navegacaoviagem.Componentes.Navegacao
import com.senac.navegacaoviagem.Model.UsuarioViewModel
import com.senac.navegacaoviagem.Model.UsuarioViewModelFactory
import com.senac.navegacaoviagem.ui.theme.NavegacaoViagemTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegacaoViagemTheme {
               // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    Navegacao()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(context: Context, navController: NavController) {
    val ctx = LocalContext.current
    val db = AppDataBase.getDataBase(ctx)
    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModelFactory(db)
    )
    var nome by remember {
        mutableStateOf("")
    }
    var senha by remember {
        mutableStateOf("")
    }
    var stringSenha by remember {
        mutableStateOf(false)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.praia),
            contentDescription = "Praia",
            modifier = Modifier
                .size(300.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Usuário",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = {
                Text(
                    text = "Usuário",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Text(
            text = "Senha",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            label = {
                Text(
                    text = "Senha",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation =
            if (stringSenha)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    stringSenha = !stringSenha
                }
                ) {
                    if (stringSenha)
                        Icon(
                            painterResource(id = R.drawable.view), ""
                        )
                    else
                        Icon(
                            painterResource(id = R.drawable.hidden), ""
                        )
                }
            }
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        val usuario = usuarioViewModel.login(nome, senha)
                        if (usuario != null) {
                            navController.navigate("menu/$nome")
                        } else {
                            context.toast("Usuário ou senha incorretos!")
                        }
                    }

                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta, contentColor = Color.White
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
            ) {
                Text(
                    text = "Entrar",
                    fontSize = 18.sp
                )
            }
        }
        Button(
            onClick = {
                navController.navigate("cadastro")
            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Magenta, contentColor = Color.White
            ), modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Text(text = "Cadastrar minha conta", fontSize = 18.sp)
        }
    }
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()