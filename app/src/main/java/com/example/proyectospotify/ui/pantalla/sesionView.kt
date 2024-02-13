package com.example.proyectospotify.ui.pantalla

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectospotify.R
import com.example.proyectospotify.ui.dataclass.Dialogo
import com.example.proyectospotify.ui.views.BBDD
import com.example.proyectospotify.ui.views.sesionViewModel

@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun sesionView(navController: NavController) {
    var usuario by remember{ mutableStateOf("")}
    var password by remember{ mutableStateOf("")}
    val corutinaScope = rememberCoroutineScope()
    var abrirDialogo by remember{ mutableStateOf(false)}
    var sesionviewModel:sesionViewModel = viewModel()
    LaunchedEffect(key1 = Unit){
        sesionviewModel.cargarBD()
        BBDD.CargaCanciones()
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround){
                Text(text = "Inicio de sesión en SPICETIFY", fontSize = 30.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
                    color=Color.White)
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(3f)
            .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)) {
            Column(modifier = Modifier
                .padding(horizontal = 50.dp)
                .fillMaxWidth()){

                Image(painter = painterResource(id = R.drawable.iconoapp),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp)
                        .padding(bottom = 25.dp))

                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it;sesionviewModel.actualizaUsuario(usuario)  },
                    label= { Text(text = "Usuario") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 25.dp),
                    colors = TextFieldDefaults.colors(Color.DarkGray),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    singleLine = true)

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it;sesionviewModel.actualizaPassword(password)  },
                    label= { Text(text = "Contraseña")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp),
                    colors = TextFieldDefaults.colors(Color.DarkGray),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done),
                    visualTransformation = PasswordVisualTransformation()
                    )

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {

                    Button(
                        onClick = {abrirDialogo=true},
                        colors = ButtonDefaults.buttonColors(Color.White)) {
                        Text(text = "Crear usuario",color=Color.Black)
                            if(abrirDialogo){
                                Dialogo(
                                    openDialog = {abrirDialogo=it},
                                    userConfirm ={usuario=it},
                                    passwordConfirm = {password=it})
                                if(!usuario.isEmpty() && !password.isEmpty()){
                                    sesionviewModel.actualizaUsuario(usuario)
                                    sesionviewModel.actualizaPassword(password)
                                    sesionviewModel.AnyadirCreeden(usuario,password)
                                }
                            }


                    }

                    Button(
                        onClick = { sesionviewModel.compruebaCreeden(navController) },
                        colors = ButtonDefaults.buttonColors(Color.White)) {
                        Text(text = "Iniciar sesión",color=Color.Black)
                    }
                }
            }


        }

    }
}