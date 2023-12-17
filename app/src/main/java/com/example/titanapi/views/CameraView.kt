package com.example.titanapi.views

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.titanapi.components.HistoryButton
import com.example.titanapi.components.LogoutButton
import com.example.titanapi.components.ProbabilityLabel
import com.example.titanapi.components.ResultLabel
import com.example.titanapi.components.SendAnotherButton
import com.example.titanapi.components.SubHeaderComponent
import com.example.titanapi.controllers.RequestImage
import com.example.titanapi.ui.theme.AppBg
import com.example.titanapi.ui.theme.ImgPlaceBg

@Composable
fun CameraView() {
    Surface(
        color = AppBg,
        modifier = Modifier
            .fillMaxSize()
            .background(AppBg)
            .padding(10.dp)) {
    }

    val localContext = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null)
    }
    val st = stringResource(id = R.string.add_photo)
    val benignPrefix = stringResource(id = R.string.benign)
    val malignPrefix = stringResource(id = R.string.malignant)

    var strv: String? by remember { mutableStateOf(st) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var hasProcessedArray by remember { mutableStateOf(false) }
    var benignProbLabel: String? by remember { mutableStateOf(null) }
    var malignantProbLabel: String? by remember { mutableStateOf(null) }
    var benignProbVal: String? by remember { mutableStateOf(null) }
    var malignantProbVal: String? by remember { mutableStateOf(null) }

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

        val minValue = pixels.minOrNull() ?: 0
        val maxValue = pixels.maxOrNull() ?: 255
        val targetMinValue = -139
        val targetMaxValue = 260

        val combinedRGBArray = IntArray(pixels.size)
        if (!hasProcessedArray) {
            for (i in pixels.indices) {
                val pixelValue = pixels[i]

                val normalizedValue = ((pixelValue - minValue).toFloat() / (maxValue - minValue) *
                        (targetMaxValue - targetMinValue) + targetMinValue).toInt()

                combinedRGBArray[i] = normalizedValue.coerceIn(targetMinValue, targetMaxValue)
            }
            hasProcessedArray = true
        }

        LaunchedEffect(hasProcessedArray) {
            if (hasProcessedArray) {
                val futurePredictionResponse = RequestImage.sendImageRequest(combinedRGBArray)

                futurePredictionResponse.thenAcceptAsync { responseData ->
                    benignProbVal = responseData.data?.predictions?.firstOrNull()?.result?.benign_prob
                    malignantProbVal = responseData.data?.predictions?.firstOrNull()?.result?.malignant_prob

                    benignProbLabel = benignProbVal?.let { formatPercentage(it, benignPrefix) }
                    malignantProbLabel = malignantProbVal?.let { formatPercentage(it, malignPrefix) }
                    strv = "TITAN"
                }
            }
        }

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
                contentAlignment = Alignment.CenterStart
            ) {
                HistoryButton(label = stringResource(id = R.string.usage_log))
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .offset(y = (-50).dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                LogoutButton(label = stringResource(id = R.string.logout))
            }
            SubHeaderComponent(value = strv!!)
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
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
                    painter = painterResource(id = R.drawable.thelion_dark),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    alpha = 0.5f,
                    modifier = Modifier
                        .background(ImgPlaceBg)
                        .size(300.dp)
                        .border(
                            width = 2.dp,
                            color = Color.DarkGray
                        )
                        .clickable {
                            val cropOptions =
                                CropImageContractOptions(uriContent, CropImageOptions())
                            cropOptions
                                .setMaxCropResultSize(130, 130)
                                .setMinCropResultSize(130, 130)
                                .setAllowFlipping(false)
                                .setAllowRotation(false)
                            imgCropLauncher.launch(cropOptions)
                        }
                )
            }
        }
        item {
            ProbabilityLabel(value = benignProbLabel)
            ProbabilityLabel(value = malignantProbLabel)
            ResultLabel(benP = benignProbVal, malP = malignantProbVal)
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            SendAnotherButton(label = stringResource(id = R.string.send_image), strv)
        }
    }
}


fun formatPercentage(input: String, prefix: String = ""): String {
    val value = input.toDoubleOrNull() ?: return ""
    val formattedValue = String.format("%.2f", value * 100)
    return "$prefix$formattedValue%"
}
@Preview
@Composable
fun CameraViewPreview() {
    CameraView()
}

