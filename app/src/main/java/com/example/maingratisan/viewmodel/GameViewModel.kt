package com.example.maingratisan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maingratisan.api.GameDataApi
import com.example.maingratisan.model.GameData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private var _gameDataList = mutableStateListOf<GameData>()
    var errMsg: String by mutableStateOf("")
    val gameDataList: List<GameData>
        get() = _gameDataList

    // Get game list
    fun getGameData() {
        viewModelScope.launch {
            val gameDataApi = GameDataApi.getInstance()
            try {
                _gameDataList.clear()
                _gameDataList.addAll(gameDataApi.getGames())
            } catch (e: Exception) {
                errMsg = e.message.toString()
            }
        }
    }

    // Search
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _gameStateList = MutableStateFlow(_gameDataList)
    val gameStateList = searchText
        .combine(_gameStateList) { text, games ->
            if (text.isBlank()) {
                games
            } else {
                games.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _gameStateList.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    // Detail page
    var singleGame by mutableStateOf<GameData?>(null)
        private set

    fun addGame(game: GameData) {
        singleGame = game
    }
}
