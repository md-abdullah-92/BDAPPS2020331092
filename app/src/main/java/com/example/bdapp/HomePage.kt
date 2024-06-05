package com.example.bdapp

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomePage {

    companion object {
        @Composable
        fun Homepage(navController: NavController) {
            // Composable function to display the main UI of the home page
            // Function to handle the home page UI layout
            val context = LocalContext.current
            //  Spacer(modifier = Modifier.height(35.dp))
            // Box with a linear gradient background
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.screen),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize(),
                    // alpha = .1f
                )

            }
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Image(

                        painter = painterResource(id = R.drawable.exam),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
                item {
                    Spacer(modifier = Modifier.padding(35.dp))
                }




                item {
                    Spacer(modifier = Modifier.padding(10.dp))
                    CButton(text = "SCHOOL OR COLLEGE", onClick = {
                        navController.navigate("submitschool")


                    },
                    )


                }
                item {
                    Spacer(modifier = Modifier.padding(10.dp))
                    CButton(text = "UNIVERSITY", onClick = {
                        navController.navigate("submit")


                    },
                    )

                }

                item {
                    Spacer(modifier = Modifier.padding(40.dp))
                    CButton(text = "Create Results", onClick = {
                        navController.navigate("EnterEIIN")

                    },
                    )
                }



                // Result Table
            }
        }
        @Composable
        fun CButton(
            onClick: () -> Unit = {},
            text: String,
            // containerColor: Color = LocalContentColor.current // Default to the current content color
        ) {
            // reusable button
            Button(
                onClick = onClick,
                shape = MaterialTheme.shapes.large,
                //  colors = ButtonDefaults.buttonColors(""),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color.White
                    )
                )
            }
        }



    }
    }
