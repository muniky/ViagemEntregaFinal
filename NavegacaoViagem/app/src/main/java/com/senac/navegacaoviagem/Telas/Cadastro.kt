import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.senac.navegacaoviagem.BaseDados.AppDataBase
import com.senac.navegacaoviagem.Model.UsuarioViewModel
import com.senac.navegacaoviagem.Model.UsuarioViewModelFactory


import com.senac.navegacaoviagem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cadastro(context: Context, navController: NavController) {
    val ctx = LocalContext.current
    val db = AppDataBase.getDataBase(ctx)
    val usuarioViewModel : UsuarioViewModel = viewModel(
        factory = UsuarioViewModelFactory(db)
    )
    val state = usuarioViewModel.uiState.collectAsState()
    val viewSenha = remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp), content = {
            Text(
                text = "Usuário",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = state.value.nome,
                onValueChange = { usuarioViewModel.updateNome(it) },
                label = {
                    Text(
                        text = "Usuário",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            )

            Text(
                text = "E-mail",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = state.value.email,
                onValueChange = { usuarioViewModel.updateEmail(it) },
                label = {
                    Text(
                        text = "E-mail",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            )

            Text(
                text = "Senha",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = state.value.senha,
                onValueChange = { usuarioViewModel.updateSenha(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                label = {
                    Text(
                        text = "Senha",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation =
                if (viewSenha.value)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewSenha.value = !viewSenha.value
                        }
                    ) {
                        if (viewSenha.value)
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
                        usuarioViewModel.save(ctx)
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Magenta,
                        contentColor = Color.White
                    ), modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = "Cadastrar",
                        fontSize = 18.sp
                    )
                }
            }
            Button(
                onClick = {
                    navController.navigate("login")
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "Voltar ao login",
                    fontSize = 18.sp
                )
            }
        }
    )
}
fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()