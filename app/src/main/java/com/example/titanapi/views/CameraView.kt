package com.example.titanapi.views

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.canhub.cropper.CropImage.CancelledResult.uriContent
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
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

    val localContext = LocalContext.current
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var imageBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri })
    val imgCropLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = { cropResult -> selectedImageUri = cropResult.uriContent })

    if (selectedImageUri != null) {
        val imageSource = ImageDecoder.createSource(localContext.contentResolver, selectedImageUri!!)
        imageBitmap = ImageDecoder.decodeBitmap(imageSource)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MainHeaderComponent(value = stringResource(id = R.string.add_photo))
        Spacer(modifier = Modifier.height(20.dp))

        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap?.asImageBitmap()!!,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .background(Color.Blue)
                    .size(300.dp)
                    .border(
                        width = 1.dp,
                        color = Color.White
                    )
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.baseline_image_search_24),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(300.dp)
                    .border(
                        width = 2.dp,
                        color = Color.DarkGray
                    )
                    .clickable {
                        val cropOptions = CropImageContractOptions(uriContent, CropImageOptions())
                        cropOptions
                            .setFixAspectRatio(true)
                            .setMaxCropResultSize(512, 512)
                            .setMinCropResultSize(512, 512)
                        imgCropLauncher.launch(cropOptions)
                    }
            )
        }
    }


    /*
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp),
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
    }*/
}

@Preview
@Composable
fun CameraViewPreview() {
    CameraView()
}