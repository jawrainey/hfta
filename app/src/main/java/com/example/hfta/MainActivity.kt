package com.example.hfta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.hfta.ui.theme.HFTATheme


class MainActivity : ComponentActivity() {
    private lateinit var tokenizer: HFTokenizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val bytes = this.assets.open(BuildConfig.SELECTED_TOKENIZER).use { it.readBytes() }
        tokenizer = HFTokenizer(bytes)

        setContent {
            HFTATheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TextToTokens(tokenizer = tokenizer, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tokenizer.tearDown()
    }
}

@Composable
fun TextToTokens(tokenizer: HFTokenizer, modifier: Modifier) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var output by remember { mutableStateOf(listOf<Int>()) }

    Column(modifier = modifier) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                output = tokenizer.encode(it.text).toList()
            },
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "$output", modifier = Modifier.padding(10.dp))
        Box(modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(1f)) {
            Text(
                text = BuildConfig.SELECTED_TOKENIZER,
                fontWeight = FontWeight.Bold,
                modifier = modifier.align(Alignment.BottomCenter)
            )
        }
    }
}