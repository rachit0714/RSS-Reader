package com.example.rssreader

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rssreader.ui.theme.RSSReaderTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.rssreader.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

         // Launch coroutine to call RSS feed
         lifecycleScope.launch(Dispatchers.IO) {
             try {
                 val response = RetrofitClient.rssService.getFeed()
                 val items = response.channel?.items
                 items?.forEach {
                     Log.d("RSS", "Title: ${it.title}, Type: ${it.type}")
                 }
             } catch (e: Exception) {
                 Log.e("RSS Error", "Error fetching feed: ${e.message}")
             }
         }
        setContent {
            RSSReaderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding: PaddingValues ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        BlogScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RSSReaderTheme {
        Greeting("Android")
    }
}

@Composable
fun SearchBox() {
    var searchQuery by remember { mutableStateOf("Search" )}
    TextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
        },
        singleLine = true,
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
    )
}

@Composable
fun BlogScreen() {
    var items by remember { mutableStateOf<List<RSSItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        val feed = RetrofitClient.rssService.getFeed()
        items = feed.channel?.items ?: emptyList()
    }
    Column {
        val context = LocalContext.current
        FloatingActionButton(onClick = {
            context.startActivity(Intent(context, PlaygroundActivity::class.java))
        }) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Playground")
        }
        SearchBox()
        LazyColumn {
            items(items) { item ->
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(item.title)
                    Text(item.text ?: "", maxLines = 5)
                }
            }
        }
    }
}