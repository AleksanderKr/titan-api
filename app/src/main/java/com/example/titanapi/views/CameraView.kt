package com.example.titanapi.views

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.titanapi.components.ProbabilityLabel
import com.example.titanapi.components.SubHeaderComponent
import com.example.titanapi.controllers.RequestImage
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
            .padding(10.dp)) {
    }

    val localContext = LocalContext.current
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val st = stringResource(id = R.string.add_photo)
    val benignPrefix = stringResource(id = R.string.benign)
    val malignPrefix = stringResource(id = R.string.malignant)
    var strv: String? by remember {
        mutableStateOf(st)
    }
    var imageBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    var hasProcessedArray by remember {
        mutableStateOf(false)
    }
    var benignProbLabel: String? by remember {
        mutableStateOf(null)
    }
    var malignantProbLabel: String? by remember {
        mutableStateOf(null)
    }

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
                    benignProbLabel = responseData.data?.predictions?.firstOrNull()?.result?.benign_prob
                    malignantProbLabel = responseData.data?.predictions?.firstOrNull()?.result?.malignant_prob

                    benignProbLabel = benignProbLabel?.let { formatPercentage(it, benignPrefix) }
                    malignantProbLabel = malignantProbLabel?.let { formatPercentage(it, malignPrefix) }
                    strv = "\n"
                }
            }
        }

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
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
                                .setAllowFlipping(false)
                                .setAllowRotation(false)
                            imgCropLauncher.launch(cropOptions)
                        }
                )
            }
        }
        item {
            CamDebugSwitchButton()
        }
        item {
            ProbabilityLabel(value = benignProbLabel)
            ProbabilityLabel(value = malignantProbLabel)
        }
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

fun formatPercentage(input: String, prefix: String = ""): String {
    val value = input.toDoubleOrNull() ?: return "" // Konwersja ciągu na liczbę, jeśli to możliwe
    val formattedValue = String.format("%.2f", value * 100) // Przekształcenie liczby na procent z dwoma miejscami po przecinku
    return "$prefix$formattedValue%" // Dodanie przedrostka do sformatowanej wartości z znakiem procentowym
}
