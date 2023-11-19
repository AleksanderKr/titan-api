package com.example.titanapi.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.titanapi.R
import com.example.titanapi.ui.theme.FormBg
import com.example.titanapi.ui.theme.FormBorder


@Composable
fun PasswordInputField(
    labelValue: String,
    painterRes: Painter,
    onChange: (String) -> Unit
) {

    val password = remember {
        mutableStateOf("")
    }
    val visible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = FormBorder,
            focusedLabelColor = FormBorder,
            cursorColor = FormBorder,
            backgroundColor = FormBg
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = password.value,
        onValueChange = {
            onChange(it)
            password.value = it
        },
        leadingIcon = {
            Icon(painter = painterRes, contentDescription = "")
        },
        trailingIcon = {
            val passwordIcon = if(visible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            var hidePass = if(visible.value) {
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { visible.value = !visible.value }) {
                Icon(imageVector = passwordIcon, contentDescription = "")
            }
        },
        visualTransformation = if(visible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}