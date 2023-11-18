package com.example.titanapi.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.titanapi.R
import com.example.titanapi.components.LoginInputField
import com.example.titanapi.components.MainHeaderComponent
import com.example.titanapi.components.PasswordInputField
import com.example.titanapi.components.LoginButton
import com.example.titanapi.ui.theme.AppBg

@Composable
fun LoginFormView() {
    Surface(
        color = AppBg,
        modifier = Modifier
            .fillMaxSize()
            .background(AppBg)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            MainHeaderComponent(value = stringResource(id = R.string.main_login_label))

            Spacer(modifier = Modifier.height(20.dp))

            LoginInputField(
                labelValue = stringResource(id = R.string.input_identifier),
                painterResource(id = R.drawable.baseline_account_circle_20)
            )

            PasswordInputField(
                labelValue = stringResource(id = R.string.password),
                painterResource(id = R.drawable.baseline_lock_open_20)
            )

            Spacer(modifier = Modifier.height(80.dp))
            
            LoginButton(value = stringResource(id = R.string.sign_in))
        }
    }

}

@Preview
@Composable
fun DefaultPreviewOfLoginForm() {
    LoginFormView()
}