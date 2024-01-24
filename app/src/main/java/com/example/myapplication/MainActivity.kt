package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.repository.UiState
import com.example.myapplication.repository.model.CountryResponse
import com.example.myapplication.repository.model.Flag
import com.example.myapplication.repository.model.Name

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            viewModel.getData()
            MainView(viewModel = viewModel, onClick = {name -> navigateToCountryDetailsActivity(name)})

        }
    }

    private fun navigateToCountryDetailsActivity(name: String) {
        val detailsIntent = Intent(this, CountryDetailsActivity::class.java)
        detailsIntent.putExtra("CUSTOM_NAME", name)
        startActivity(detailsIntent)
    }
}

@Composable
fun MainView(viewModel: MainViewModel, onClick: (String) -> Unit) {
    val uiState by viewModel.immutableCountriesData.observeAsState(UiState())

    when {
        uiState.isLoading -> { MyLoadingView() }

        uiState.error != null -> { MyErrorView() }

        uiState.data != null -> { uiState.data?.let { MyListView(countries = it, onClick) } }
    }

}

@Composable
fun CountryView(name: Name, independent: String, flag: Flag, capital: List<String>, onClick: (String) -> Unit) {

    Column (modifier=Modifier
        .padding(10.dp)
        .background(Color(87, 163, 235), RoundedCornerShape(15.dp))
        .border(BorderStroke(1.dp, Color.Transparent), RoundedCornerShape(15.dp))
        .clickable { onClick.invoke(name.common) }
    ){
        Text(text = name.common, fontSize = 20.sp, fontWeight = FontWeight(1000),
            modifier = Modifier.offset(10.dp), color = Color(255, 255, 255))

        Row(modifier = Modifier.padding(15.dp).fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = "Capital: ${capital[0]}", fontWeight = FontWeight(500), color = Color(255, 255, 255))
                Text(text = "Independence: $independent", fontWeight = FontWeight(500), color = Color(255, 255, 255))
            }
            AsyncImage(
                model = flag.png,
                contentDescription = "Flaga ${name.common}"
            )
        }
    }

}

@Composable
fun MyErrorView() {
    Log.d("Country", "ERROR")
}

@Composable
fun MyLoadingView() {
    Text(text = "Loading")
}

@Composable
fun MyListView(countries: List<CountryResponse>, onClick: (String) -> Unit) {
    Column (modifier = Modifier.background(Color(232,244,253))){
        LazyColumn{
            items(countries) { country ->
                CountryView(name = country.name, independent = country.independent, flag = country.flags, capital = country.capital, onClick = {name -> onClick.invoke(name)})
            }
        }
    }
}
