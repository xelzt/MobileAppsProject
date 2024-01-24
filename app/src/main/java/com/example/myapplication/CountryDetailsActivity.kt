package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.repository.UiState
import com.example.myapplication.repository.model.Country

class CountryDetailsActivity : ComponentActivity() {

    private val viewModel: CountryDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("CUSTOM_NAME")
        setContent {
            viewModel.getData("$name")
            CountryDetailsView(viewModel = viewModel)
        }
    }
}

@Composable
fun CountryDetailsView(viewModel: CountryDetailsViewModel) {
    val uiState by viewModel.immutableCountryDetailsData.observeAsState(UiState())
    when {
        uiState.isLoading -> { MyLoadingView() }

        uiState.error != null -> { MyErrorView() }

        uiState.data != null -> { uiState.data?.let { CountryView(country = it) } }
    }
}

@Composable
fun CountryView(country: Country) {
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .background(Color(232,244,253))
    ){
        AsyncImage(
            model = country.flags.png,
            contentDescription = "Flaga ${country.name.common}",
            modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        )
        CountryDetailsRow("Country (Official Name)", country.name.official)
        CountryDetailsRow("Country (Common Name)", country.name.common)
        CountryDetailsRow("Region", country.region)
        CountryDetailsRow("Area", "${country.area}")

    }
}

@Composable
fun CountryDetailsRow(textDescription: String, data: String){
    Row(modifier = Modifier
        .padding(15.dp)
        .border(BorderStroke(1.dp, Color.Transparent), RoundedCornerShape(12.dp))
        .fillMaxWidth()
        .background(Color(87, 163, 235), RoundedCornerShape(15.dp))
        .padding(15.dp)
    ) {
        Text(text = "$textDescription: $data", color = Color(255, 255, 255))
    }
}
