package com.example.maingratisan.view

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.maingratisan.R
import com.example.maingratisan.formatDate
import com.example.maingratisan.navigation.Screen
import com.example.maingratisan.ui.theme.DarkGradient
import com.example.maingratisan.viewmodel.GameViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun DetailScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    val context = LocalContext.current

    val singleGame = gameViewModel.singleGame
    if (singleGame != null) {
        Column {

            // Top box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {

                // Top image
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(singleGame.image)
                            .build(),
                        placeholder = painterResource(id = R.drawable.placeholder)
                    ),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = singleGame.title,
                    contentScale = ContentScale.Crop
                )

                // Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    DarkGradient,
                                    Color.Transparent
                                ),
                            )
                        )
                )

                // Back icon
                IconButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(30.dp),
                    onClick = {
                        navController.navigate(Screen.Home.route)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Rounded.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Back"
                    )
                }
            }

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Top text
                Row {

                    // Price
                    if (singleGame.worth != "N/A") {
                        Text(
                            color = Color.Gray,
                            text = singleGame.worth,
                            style = MaterialTheme.typography.bodySmall
                                .copy(textDecoration = TextDecoration.LineThrough)
                        )
                    }
                    Text(
                        text = " $0.00 • ",
                        style = MaterialTheme.typography.bodySmall
                    )

                    // Type
                    val typeColor = when (singleGame.type) {
                        "Game" -> Color.Cyan
                        "Early Access" -> Color.Green
                        "DLC" -> Color.Yellow
                        else -> MaterialTheme.typography.bodySmall.color
                    }
                    Text(
                        text = singleGame.type,
                        style = MaterialTheme.typography.bodySmall,
                        color = typeColor
                    )

                    // End date
                    val endDate = when (singleGame.end_date) {
                        "N/A" -> " • No end date"
                        else -> " • Until " + formatDate(singleGame.end_date)
                    }
                    Text(
                        text = endDate,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Title
                Text(
                    text = singleGame.title,
                    style = MaterialTheme.typography.titleLarge
                )

                // Description
                Text(
                    text = singleGame.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                // Instruction
                Text(
                    text = "Instructions\n" + singleGame.instructions,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Get button
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(singleGame.open_giveaway_url)
                        )
                        context.startActivity(intent)
                    }
                ) {
                    FaIcon(
                        faIcon = FaIcons.Link,
                        size = 16.dp,
                        tint = LocalContentColor.current
                    )
                    Text(text = "  Get Loot")
                }

                // Bookmark button
                FilledTonalButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { }
                ) {
                    FaIcon(
                        faIcon = FaIcons.BookmarkRegular,
                        size = 16.dp,
                        tint = LocalContentColor.current
                    )
                    Text(text = "  Bookmark")
                }
            }
        }
    }
}