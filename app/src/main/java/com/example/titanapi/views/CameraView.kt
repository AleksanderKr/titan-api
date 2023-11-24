package com.example.titanapi.views

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.canhub.cropper.CropImage.CancelledResult.uriContent
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.titanapi.R
import com.example.titanapi.components.MainHeaderComponent
import com.example.titanapi.di.TitanMobAppRouter
import com.example.titanapi.di.View
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
    var hasProcessedArray by remember { mutableStateOf(false) }

    val imgCropLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = { cropResult -> selectedImageUri = cropResult.uriContent })

    if (selectedImageUri != null) {
        val imageSource = ImageDecoder.createSource(localContext.contentResolver, selectedImageUri!!)
        imageBitmap = ImageDecoder.decodeBitmap(
            imageSource
        ) { imageDecoder: ImageDecoder, _: ImageDecoder.ImageInfo?, _: ImageDecoder.Source? ->
            imageDecoder.isMutableRequired = true
        }

        val bitmapWidth: Int = imageBitmap!!.width
        val bitmapHeight: Int = imageBitmap!!.height
        val pixels = IntArray(bitmapHeight * bitmapWidth)

        imageBitmap!!.getPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight)

        val minValue = pixels.minOrNull() ?: 0 // Minimum value in the 'pixels' array
        val maxValue = pixels.maxOrNull() ?: 255 // Maximum value in the 'pixels' array

        val targetMinValue = -139 // Define the target minimum value after normalization
        val targetMaxValue = 260 // Define the target maximum value after normalization

        val combinedRGBArray = IntArray(pixels.size)
        if (!hasProcessedArray) {
            for (i in pixels.indices) {
                val pixelValue = pixels[i]

                // Normalize the pixel value to the desired range (-139 to 260)
                val normalizedValue = ((pixelValue - minValue).toFloat() / (maxValue - minValue) *
                        (targetMaxValue - targetMinValue) + targetMinValue).toInt()

                // Ensure the normalized value stays within the specified range
                combinedRGBArray[i] = normalizedValue.coerceIn(targetMinValue, targetMaxValue)
            }
            hasProcessedArray = true
        }

        LaunchedEffect(hasProcessedArray) {
            if (hasProcessedArray) {
                val chunkSize = 100 // Adjust the chunk size as needed
                val arraySize = combinedRGBArray.size

                var startPos = 0
                while (startPos < arraySize) {
                    val endPos = (startPos + chunkSize).coerceAtMost(arraySize)
                    val chunk = combinedRGBArray.copyOfRange(startPos, endPos)
                    println(chunk.contentToString())
                    startPos += chunkSize
                }

            }
        }
        // koniec pobierania pikseli
        // skalowanie pikseli do -139-260





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
                            .setMaxCropResultSize(130, 130)
                            .setMinCropResultSize(130, 130)
                        imgCropLauncher.launch(cropOptions)
                    }
            )
        }

        CamDebugSwitchButton()


    }
}

@Preview
@Composable
fun CameraViewPreview() {
    CameraView()
}

@Composable
fun CamDebugSwitchButton() {
    Button(
        onClick = {
            TitanMobAppRouter.routeTo(View.LoginViewObj)
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        colors = ButtonDefaults.buttonColors(Color.Red),
        contentPadding = PaddingValues(),
    ) {

    }
}