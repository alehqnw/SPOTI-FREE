package com.example.proyectospotify.ui.dataclass

import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialogo(
    openDialog: (Boolean)->Unit,
    userConfirm: (String) -> Unit,
    passwordConfirm:(String)->Unit
){
    val Red = Color(20,3,2)
    val Black = Color(5, 5, 5)
    val DarkColors = darkColorScheme(
        primary=Red,
        secondary = Black
    )
    MaterialTheme(
        colorScheme = DarkColors
    ){}

    var user by remember{ mutableStateOf("")}
    var password by remember{ mutableStateOf("")}
    var usuarioError by rememberSaveable{ mutableStateOf(user.isEmpty())}
    var passwordError by rememberSaveable{ mutableStateOf(password.isEmpty())}
    LaunchedEffect(key1 =(user)){
        usuarioError=user.isEmpty()
    }
    LaunchedEffect(key1 =(password)){
        passwordError=password.isEmpty()
    }
    Dialog(onDismissRequest = { openDialog(false) }) {
        Card(
            modifier = Modifier
                .size(300.dp),
            colors=CardDefaults.cardColors(Color.DarkGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)) {
                Text(text = "Crear nuevo usuario",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
                    fontSize = 20.sp,
                    style = TextStyle(
                        color=Color.White
                    )
                )
                OutlinedTextField(
                    value = user,
                    onValueChange ={user=it} ,
                    label = {"Nuevo usuario"},
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                        .padding(horizontal = 20.dp),
                    isError = (usuarioError == user.isEmpty()),
                    supportingText = {
                        if (passwordError){
                            Text(text = "Error debes ingresar el correo",
                                modifier = Modifier.fillMaxWidth(),
                                color=MaterialTheme.colorScheme.error)
                        }
                    },
                    colors = TextFieldDefaults.colors(Color.Gray),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    singleLine = true)
                OutlinedTextField(
                    value = password,
                    onValueChange = {password=it},
                    label = {"Nueva Contraseña"},
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                        .padding(horizontal = 20.dp),
                    colors = TextFieldDefaults.colors(Color.Gray),
                    isError = passwordError==password.isEmpty(),
                    supportingText = {
                        if (passwordError){
                            Text(text = "Error no lo puedes dejar en blanco",
                                modifier = Modifier.fillMaxWidth(),
                                color=MaterialTheme.colorScheme.error)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done),
                    visualTransformation = PasswordVisualTransformation()
                )
                Row(
                    modifier = Modifier
                    .fillMaxWidth(),
                    Arrangement.SpaceAround) {
                    Button(onClick = { openDialog(false) }) {
                        Text(text = "Cancelar")
                    }
                    Button(onClick = {
                        if(!passwordError&&!usuarioError){(user);userConfirm(user); passwordConfirm(password);openDialog(false) }
                        }) {
                        Text(text="Aceptar")
                    }

                }

            }
        }
    }
}
