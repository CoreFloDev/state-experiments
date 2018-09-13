package com.example.florent.stateexperiment.main.messages

sealed class MainUiModel {
    object Refreshing : MainUiModel()
    data class Display(val name: String) : MainUiModel()
}
