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
                    modifier = Modifier.padding(0.dp)
                )
                Text(
                    text = player.lastName,
                    modifier = Modifier.padding(0.dp)
                )
            }
            Text(
                text = player.age.toString(),
                modifier = Modifier.padding(horizontal = 100.dp)
            )
            Text(
                text = player.position,
                modifier = Modifier.padding(horizontal = 0.dp)
            )
        }
    }
}