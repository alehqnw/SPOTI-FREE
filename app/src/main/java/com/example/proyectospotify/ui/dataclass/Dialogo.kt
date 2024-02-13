package com.example.proyectospotify.ui.dataclass

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialogo(
    openDialog: (Boolean)->Unit,
    userConfirm: (String) -> Unit,
    passwordConfirm:(String)->Unit
){
    var user by remember{ mutableStateOf("")}
    var password by remember{ mutableStateOf("")}
    Dialog(onDismissRequest = { openDialog(false) }) {
        Card(
            modifier = Modifier.size(500.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Crear nuevo usuario")
                TextField(
                    value = user,
                    onValueChange = {user=it},
                    label = {"Introducir nuevo usuario"})
                TextField(
                    value = password,
                    onValueChange = {password=it},
                    label = {"Introducir nueva contraseña"})
                Row {

                    Button(onClick = { userConfirm(user);passwordConfirm(password);openDialog(false) }) {
                        Text(text="Aceptar")
                    }
                    Button(onClick = { openDialog(false) }) {
                        Text(text = "Cancelar")
                    }
                }

            }
        }
    }
}