package com.example.rssreader

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rssreader.network.RetrofitClient

class PlaygroundActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            MaterialTheme {
                Text("Hello from PlaygroundActivity!")
                Switch()

            }
            LazyColumn {

                items(1) {
                    Switch()
                }

                items(people) {
                    //Switch()
                    ListItem(it)
                }
                items(rotation) {
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
            }
            SearchBox2()
        }
    }
}

@Composable
fun SearchBox2() {
    var searchQuery by remember { mutableStateOf("Search" ) }
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
fun Switch() {
    val context = LocalContext.current
    FloatingActionButton(onClick = {
        context.startActivity(Intent(context, MainActivity::class.java))
    }) {
        Icon(Icons.Default.PlayArrow, contentDescription = "Main")
    }
}