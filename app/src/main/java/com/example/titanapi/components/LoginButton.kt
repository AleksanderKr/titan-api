package com.example.titanapi.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.titanapi.controllers.RequestLogin
import com.example.titanapi.ui.theme.Pink40
import com.example.titanapi.ui.theme.Purple40

@Composable
fun LoginButton(
    value: String,
    identifier: MutableState<String>,
    password: MutableState<String>,
) {
    Button(
        onClick = {
            RequestLogin.sendLoginRequest(identifier.value, password.value)
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(64.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Pink40, Purple40)),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}