package com.example.maingratisan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.maingratisan.R
import com.example.maingratisan.formatDate
import com.example.maingratisan.model.GameData
import com.example.maingratisan.navigation.Screen
import com.example.maingratisan.ui.theme.DarkGradient
import com.example.maingratisan.ui.theme.cardSmall
import com.example.maingratisan.ui.theme.cardTitle
import com.example.maingratisan.viewmodel.GameViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    val gameList by gameViewModel.gameStateList.collectAsState()
    val searchText by gameViewModel.searchText.collectAsState()
    var active by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
    ) {

        // Search bar
        DockedSearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchText,
            onQueryChange = gameViewModel::onSearchTextChange,
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text(text = "Search...") },
            trailingIcon = {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
            }
        ) { }
        
        Spacer(modifier = Modifier.height(24.dp))

        // Game list
        if (gameViewModel.errMsg.isBlank()) {
            LazyColumn {
                itemsIndexed(items = gameList) { _, game ->
                    GameItem(
                        game = game,
                        navController = navController,
                        gameViewModel = gameViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun GameItem(game: GameData, navController: NavHostController, gameViewModel: GameViewModel) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clickable {
                gameViewModel.addGame(game = game)
                navController.navigate(Screen.Detail.route)
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Background image
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(game.image)
                        .build(),
                    placeholder = painterResource(id = R.drawable.placeholder)
                ),
                modifier = Modifier.fillMaxSize(),
                contentDescription = game.title,
                contentScale = ContentScale.Crop
            )

            // Gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                DarkGradient
                            ),
                            endX = 800f
                        )
                    )
            )

            // Bookmark button
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { }
                ) {
                    FaIcon(
                        faIcon = FaIcons.BookmarkRegular,
                        size = 28.dp,
                        tint = LocalContentColor.current
                    )
                }
            }

            // Text
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight()
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 4.dp,
                        alignment = Alignment.Bottom
                    ),
                    horizontalAlignment = Alignment.End
                ) {

                    // Price
                    Row {
                        if (game.worth != "N/A") {
                            Text(
                                color = Color.Gray,
                                text = game.worth,
                                style = MaterialTheme.typography.cardSmall
                                    .copy(textDecoration = TextDecoration.LineThrough)
                            )
                        }
                        Text(
                            text = "  $0.00",
                            style = MaterialTheme.typography.cardSmall
                        )
                    }

                    // Title
                    Text(
                        text = game.title,
                        style = MaterialTheme.typography.cardTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Bottom text
                    Row {

                        // Type
                        val typeColor = when (game.type) {
                            "Game" -> Color.Cyan
                            "Early Access" -> Color.Green
                            "DLC" -> Color.Yellow
                            else -> Color.White
                        }
                        Text(
                            text = game.type,
                            style = MaterialTheme.typography.cardSmall,
                            color = typeColor
                        )

                        // End date
                        val endDate = when (game.end_date) {
                            "N/A" -> " • No end date"
                            else -> " • Until " + formatDate(game.end_date)
                        }
                        Text(
                            text = endDate,
                            style = MaterialTheme.typography.cardSmall
                        )
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
}