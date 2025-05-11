package com.example.hfta

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.hfta.ui.theme.HFTATheme


fun tokenize(context: Context) {
    val bytes = context.assets.open(BuildConfig.SELECTED_TOKENIZER).use { it.readBytes() }
    val tokenizer = HFTokenizer(bytes)

    val sentences = listOf("Hello world", "hello world", "hello jặy")
    for (sentence in sentences) {
        val ids = tokenizer.encode(sentence)
        Log.i("TOKENIZER_OUTPUT", ids.contentToString())
    }
    tokenizer.tearDown()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tokenize(this)

        setContent {
            HFTATheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text(
                        text = "Tokenization exists as Instrumentation tests",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}