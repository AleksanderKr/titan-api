package com.example.titanapi.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.titanapi.R

@Composable
fun IconLight() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Transparent)
        .padding(40.dp, 480.dp, 40.dp, 30.dp)) {
        Image(
            painter = painterResource(id = R.drawable.thelion_light),
            contentDescription = "thelion",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}