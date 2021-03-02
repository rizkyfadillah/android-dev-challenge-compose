package com.example.androiddevchallenge.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Start building your app here!
@Composable
fun DetailScreen(name: String, image: Int, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Navigate Up"
                        )
                    }
                })
        },
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                }
        ) {
            Column {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "Launcher background",
                )
                Text(
                    text = name,
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