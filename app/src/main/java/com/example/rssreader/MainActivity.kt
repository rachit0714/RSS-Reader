package com.example.rssreader

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
import androidx.compose.material3.Card
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rssreader.network.RetrofitClient

class MainActivity : ComponentActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

         val people = listOf(
             Person(firstName = "Bryce", lastName = "Harper", age = 32, position = "1B"),
             Person(firstName = "Aaron", lastName = "Nola", age = 34, position = "SP"),
             Person(firstName = "Christopher", lastName = "Sanchez", age = 27, position = "SP")
         )
         val rotation = people.filter {it.position.equals("SP")}
         val rssItems: List<RSSItem> = listOf(
             RSSItem(title = "Welcome to my blog", text = "This is the first blog", type=RSSType.TEXT),
             RSSItem(title = "Blog item", text = "Blog 2 text", type=RSSType.TEXT),
             RSSItem(title = "Video-log", text = "Play video for details", type=RSSType.VIDEO),
             RSSItem(title = "Bryce Harper", text = "Back from break", type=RSSType.IMAGE),
             RSSItem(title = "Video-log", text = "Play video for details", type=RSSType.VIDEO) ,
             RSSItem(title = "Bryce Harper", text = "Back from break", type=RSSType.IMAGE)
         )
        setContent {
            RSSReaderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding: PaddingValues ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        BlogScreen()
    /*                    LazyColumn {
                            items(people) {
                                ListItem(it)
                            }
                            items(rssItems) {
                                when (it.type) {
                                    RSSType.TEXT -> {
                                        ListRSSText(it)
                                    }
                                    RSSType.VIDEO -> {
                                        ListRSSVideo(it)
                                    }
                                    RSSType.IMAGE -> {
                                        ListRSSImage(it)
                                    }
                                }
                            }
                        }*/
                        SearchBox()
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
fun ListRSSText(item: RSSItem) {
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
    ) {
        Row {
            Image(
                painter = painterResource(R.drawable.baseline_text_snippet_24),
                contentDescription = "Text snippet photo",
                modifier = Modifier.width(100.dp).height(100.dp)
            )
            Text(
                text = item.title,
                fontSize = 32.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(20.dp)
            )
        }
        Text(
            text = item.text
        )
    }
}

@Composable
fun ListRSSVideo(item: RSSItem) {
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
    ) {
        Column {
            Text (
                text = "Click below to play the video",
                modifier = Modifier.padding(12.dp),
            )
            item.mediaUrl?.let { video ->
                Image(
                    painter = painterResource(0),
                    contentDescription = "Text snippet image",
                    modifier = Modifier.fillMaxWidth()
                        .width(300.dp)
                        .height(300.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
            Text(
                text = item.title,
                fontSize = 32.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(20.dp)
            )
        }
        Text(text = item.text)
    }
}

@Composable
fun ListRSSImage(item: RSSItem) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = "Photo below:",
                modifier = Modifier.padding(12.dp)
            )
            item.mediaUrl?.let { photo ->
                Image (
                    painter = painterResource(0),
                    contentDescription = "Text snippet photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(300.dp)
                        .height(300.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
            Text(
                text = item.title,
                fontSize = 32.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .padding(20.dp)
                    .clickable {
                        Log.d(item.title, "Photo pressed")
                    }
            )
        }
        Text(text = item.text)
    }
}


@Composable
fun ListItem(player: Person) {
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
    ) {
        Row(modifier = Modifier.padding(top = 12.dp)) {
            Image(
                painter = painterResource(R.drawable.baseline_person_24),
                contentDescription = "Photo of a person",
                modifier = Modifier.width(100.dp).height(100.dp)
            )
            Column {
                Text(
                    text = player.firstName,
                )
                Text(
                    text = player.lastName,
                )
                Text(
                    text = player.age.toString(),
                )
                Text(
                    text = player.position,
                )
            }

        }
    }
}

@Composable
fun BlogScreen() {
    var items by remember { mutableStateOf<List<RSSItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        val feed = RetrofitClient.rssService.getFeed()
        items = feed.channel?.items ?: emptyList()
    }

    LazyColumn {
        items(items) { item ->
            Column(modifier = Modifier.padding(12.dp)) {
                Text(item.title)
                Text(item.text ?: "", maxLines = 5)
            }
        }
    }
}