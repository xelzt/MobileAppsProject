package com.example.myapplication

import MainViewModel
import MtgMainViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items
import coil.compose.AsyncImage
import com.example.myapplication.repository.UiState
import com.example.myapplication.repository.model.CountryResponse
import com.example.myapplication.repository.model.Flag
import com.example.myapplication.repository.model.Name

@Composable
fun MainView(viewModel: MainViewModel) {
    val uiState by viewModel.immutableCountiesData.observeAsState(UiState())

    when {
        uiState.isLoading -> { MyLoadingView() }

        uiState.error != null -> { MyErrorView() }

        uiState.data != null -> { uiState.data?.let { MyListView(countries = it) } }
    }

}

@Composable
fun CountryView(name: Name, independent: String, flag: Flag, capital: List<String>) {
    Row {
        Box(modifier = Modifier
            .border(BorderStroke(1.dp, Color.Black))
            .padding(all = 5.dp)
            .fillMaxWidth()
        ){
            Column {
                Text(text = name.common, fontSize = 20.sp, fontWeight = FontWeight(1000))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(text = "Stolica: ${capital[0]}")
                        Text(text = "Niepodległość: ${independent}")
                    }
                    AsyncImage(
                        model = flag.png,
                        contentDescription = "Flaga ${name.common}",
                        modifier = Modifier.wrapContentSize(Alignment.TopEnd)
                    )
                }
            }
        }
    }
}

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            viewModel.getData()
            MainView(viewModel = viewModel)

        }
    }
}



@Composable
fun MyErrorView() {
//    wyświetla tekst z błędem lub snackbar
}

@Composable
fun MyLoadingView() {
//    wyświetla loader
}

@Composable
fun MyListView(countries: List<CountryResponse>) {
//    wyświetla list w oparciu o LazyColumn lub LazyRow
    Column {
        Text(text = "Countries App", modifier = Modifier.padding(start = 50.dp, end = 50.dp))
        LazyColumn {
            // import funkcji items z androidx.compose.foundation.lazy.items
            items(countries) { country ->
                CountryView(name = country.name, independent = country.independent, flag = country.flags, capital = country.capital)
            }
        }
    }
}
