package com.example.titanapi.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.titanapi.R
import com.example.titanapi.ui.theme.ButtonGrad1

@Composable
fun ToS(termsAccepted: MutableState<Boolean>) {
    Checkbox(
        checked = termsAccepted.value,
        onCheckedChange = { termsAccepted.value = it },
        modifier = Modifier.padding(end = 8.dp)
    )
    Text(
        text = stringResource(id = R.string.tos1),
        fontSize = 14.sp,
        style = TextStyle(color = Color.DarkGray),
        modifier = Modifier
            .padding(top = 12.dp)
    )
    Text(
        text = stringResource(id = R.string.tos2),
        fontSize = 14.sp,
        style = TextStyle(color = ButtonGrad1, textDecoration = TextDecoration.Underline),
        modifier = Modifier
            .padding(top = 12.dp)
            .clickable {
                // Obsługa akcji kliknięcia dla warunków usługi (przekierowanie do warunków usługi)
                // TODO: Umieść logikę nawigacji do warunków usługi tutaj
            }
    )
}