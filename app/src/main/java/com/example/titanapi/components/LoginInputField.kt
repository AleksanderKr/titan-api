package com.example.titanapi.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.titanapi.ui.theme.FormBg
import com.example.titanapi.ui.theme.FormBorder


@Composable
fun LoginInputField(
    labelValue: String,
    painterRes: Painter,
    onChange: (String) -> Unit
) {

    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = FormBorder,
            focusedLabelColor = FormBorder,
            cursorColor = FormBorder,
            backgroundColor = FormBg
        ),
        keyboardOptions = KeyboardOptions.Default,
        value = textValue.value,
        onValueChange = {
            onChange(it)
            textValue.value = it
        },
        leadingIcon = {
            Icon(painter = painterRes, contentDescription = "")
        }
    )
}