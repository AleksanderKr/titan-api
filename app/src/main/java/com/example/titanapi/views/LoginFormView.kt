package com.example.titanapi.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.titanapi.R
import com.example.titanapi.components.IconLight
import com.example.titanapi.components.LoginButton
import com.example.titanapi.components.LoginInputField
import com.example.titanapi.components.MainHeaderComponent
import com.example.titanapi.components.PasswordInputField
import com.example.titanapi.components.ToS
import com.example.titanapi.di.TitanMobAppRouter
import com.example.titanapi.di.View

@Composable
fun LoginFormView() {
    val idInput = remember { mutableStateOf("") }
    val passInput = remember { mutableStateOf("") }
    val termsAccepted = remember { mutableStateOf(false) }

    IconLight()

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(28.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                MainHeaderComponent(value = stringResource(id = R.string.main_login_label))
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                LoginInputField(
                    labelValue = stringResource(id = R.string.input_identifier),
                    painterResource(id = R.drawable.baseline_account_circle_20)
                ) { id -> idInput.value = id }
            }

            item {
                PasswordInputField(
                    labelValue = stringResource(id = R.string.password),
                    painterResource(id = R.drawable.baseline_lock_open_20)
                ) { pw -> passInput.value = pw }
            }
            item {
                Row {
                    ToS(termsAccepted = termsAccepted)
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }

            item {
                LoginButton(
                    label = stringResource(id = R.string.sign_in),
                    identifier = idInput,
                    password = passInput,
                    enabled = termsAccepted.value
                )
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


@Preview
@Composable
fun DefaultPreviewOfLoginForm() {
    LoginFormView()
}

@Composable
fun DebugSwitchButton() {
    Button(onClick = {
        TitanMobAppRouter.routeTo(View.CameraViewObj)
    },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        colors = ButtonDefaults.buttonColors(Color.Red),
        contentPadding = PaddingValues(),) {

    }
}