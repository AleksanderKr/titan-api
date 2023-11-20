package com.example.titanapi.views

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.titanapi.R
import com.example.titanapi.components.MainHeaderComponent
import com.example.titanapi.controllers.RequestLogin
import com.example.titanapi.ui.theme.AppBg

@Composable
fun CameraView() {
    Surface(
        color = AppBg,
        modifier = Modifier
            .fillMaxSize()
            .background(AppBg)
            .padding(28.dp)) {
    }
    //Log.d("TAG","CAMERAVIEW "+ RequestLogin.logged_user.toString())

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri })

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) {
                    Text(text = stringResource(id = R.string.pick_photo))
                }
                Button(onClick = {

                }) {
                    Text(text = stringResource(id = R.string.take_photo))
                }
            }
        }

        item {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop)
        }
        item {
            Button(onClick = {

                },
                modifier= Modifier.padding(40.dp
                )) {

                    Text(text = stringResource(id = R.string.take_photo))
            }
        }
    }
}

@Preview
@Composable
fun CameraViewPreview() {
    CameraView()
}