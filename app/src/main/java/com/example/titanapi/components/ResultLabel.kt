package com.example.titanapi.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.titanapi.R
import com.example.titanapi.ui.theme.ResultTextColor

@Composable
fun ResultLabel(benP: String?, malP: String?) {
    if (!benP.isNullOrBlank() && !malP.isNullOrBlank()) {
        val benProb = benP.toDoubleOrNull() ?: 0.0
        val malProb = malP.toDoubleOrNull() ?: 0.0

        val msg = when {
            benProb.compareTo(malProb) > 0 -> {
               stringResource(id = R.string.result_benign) 
            }
            malProb.compareTo(benProb) > 0 -> {
                stringResource(id = R.string.result_malignant)
            }
            else -> {
                stringResource(id = R.string.result_malignant)
            }

        }
        Text(
            text = stringResource(id = R.string.result) + msg,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 20.dp)
                .padding(12.dp),
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            color = ResultTextColor,
            textAlign = TextAlign.Center
        )
    }
}
