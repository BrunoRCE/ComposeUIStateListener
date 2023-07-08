package com.brunoxapps.baseuistate

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.brunoxapps.baseuistate.ui.theme.BaseUIStateTheme
import com.brunoxapps.baseuistate.uistate.CollectUIState
import com.brunoxapps.baseuistate.uistate.OnUIStateListener
import com.brunoxapps.baseuistate.uistate.SetUIStateListener

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseUIStateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeWithCollect(viewModel)
                }
            }
        }
    }
}

@Composable
private fun ShowError(error: String?) {
    Toast.makeText(
        LocalContext.current,
        "error: $error",
        Toast.LENGTH_SHORT
    ).show()
}

@Composable
private fun Loader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Content(data: List<String>) {
    LazyColumn {
        items(data.size) {
            Text(text = data[it])
        }
    }
}

@Composable
private fun HomeWithCollect(
    viewModel: MainViewModel
) {
    viewModel.uiState.CollectUIState(
        onLoading = {
            Loader()
        },
        onSuccess = {
            Content(it)
        },
        onError = {
            ShowError(it.message)
        }
    )
}

@Composable
private fun HomeWithListener(
    viewModel: MainViewModel
) {
    viewModel.uiState.SetUIStateListener(object : OnUIStateListener<List<String>> {
        @Composable
        override fun Loading() {
            Loader()
        }

        @Composable
        override fun Success(data: List<String>) {
            Content(data)
        }

        @Composable
        override fun Error(error: Throwable?) {
            ShowError(error?.message)
        }
    })
}