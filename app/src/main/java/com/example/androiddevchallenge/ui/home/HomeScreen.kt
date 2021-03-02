package com.example.androiddevchallenge.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.Screen
import com.example.androiddevchallenge.model.pets

@Composable
fun HomeScreen(navigateTo: (Screen) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Home") })
        },
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(pets) { pet ->
                Card(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .clickable {
                            navigateTo(Screen.Detail(pet.name, pet.drawable))
                        }
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = pet.drawable),
                            contentDescription = "Launcher background",
                        )
                        Text(
                            text = pet.name,
                            modifier = Modifier.padding(16.dp),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}