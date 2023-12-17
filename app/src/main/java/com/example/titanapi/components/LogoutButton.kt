package com.example.titanapi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
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
import com.example.titanapi.controllers.RequestImage
import com.example.titanapi.di.TitanMobAppRouter
import com.example.titanapi.di.View
import com.example.titanapi.ui.theme.ButtonGrad1
import com.example.titanapi.ui.theme.ButtonGrad2
import com.example.titanapi.ui.theme.Pink40
import com.example.titanapi.ui.theme.Purple40
import com.example.titanapi.ui.theme.Purple80
import com.example.titanapi.ui.theme.PurpleGrey40
import com.example.titanapi.ui.theme.PurpleGrey80

@Composable
fun LogoutButton(
    label: String
    ) {
    Button(
        onClick = {
            TitanMobAppRouter.routeTo(View.LoginViewObj)
            // send logout request
        },
        modifier = Modifier
            .widthIn(84.dp)
            .heightIn(24.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(50.dp)
    ) {
        Box(
            modifier = Modifier
                .widthIn(84.dp)
                .heightIn(32.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(ButtonGrad1, ButtonGrad2)),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}