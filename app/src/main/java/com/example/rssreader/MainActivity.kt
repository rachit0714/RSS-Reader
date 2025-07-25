package com.example.rssreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
            RSSItem(title = "Blogger", text = "Text of Blog 3", type=RSSType.VIDEO),
            RSSItem(title = "Returning Blog", text = "Back from break", type=RSSType.PHOTO)
         )
        setContent {
            RSSReaderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding: PaddingValues ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    LazyColumn {
                        items(people) {
                            ListItem(it)
                        }
                        items(rssItems) {
                            if (it.type == RSSType.TEXT) {
                                ListRSSText(it)
                            }
                        }

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
                modifier = Modifier.padding(20.dp)
            )
        }
        Text(text = item.text)
    }
}

@Composable
fun ListRSSVideo(item: RSSItem) {
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
    ) {
        Row {
            Image(
                painter = painterResource(R.drawable.baseline_text_snippet_24),
                contentDescription = "Text snippet photo",
                modifier = Modifier.width(300.dp).height(300.dp)
            )
            Text(
                text = item.title,
                modifier = Modifier.padding(20.dp)
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