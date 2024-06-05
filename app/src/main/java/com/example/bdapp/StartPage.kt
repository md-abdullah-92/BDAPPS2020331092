package com.example.bdapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bdapp.ui.theme.BdappTheme
import kotlinx.coroutines.delay

class StartPage {
    companion object {
        @Composable
        fun Startpage(navController: NavController) {

            Spacer(modifier = Modifier.height(35.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        // shape = RoundedCornerShape(20.dp),
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF00FFFF),
                                Color(0xFF4169e1)
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite,
                            tileMode = TileMode.Decal

                        )

                    )
            )
            {
                Column {
                    Spacer(modifier = Modifier.height(150.dp))
                    Image(
                        painter = painterResource(id = R.drawable.exam),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                    Textrow("Welcome")

                }
            }
            LaunchedEffect(Unit) {
                delay(3000) // Simulating a 2-second delay
                navController.navigate("homepage")
            }


        }

        @Composable
        fun Textrow(text: String) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 35.sp,
                    style = TextStyle(
                        color = Color.White // Set the font color to white
                    ),
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        @Preview(showBackground = true, showSystemUi = true)
        @Composable
        fun GreetingPreview() {
            BdappTheme {
              //  Startpage()
            }

        }
    }
}
