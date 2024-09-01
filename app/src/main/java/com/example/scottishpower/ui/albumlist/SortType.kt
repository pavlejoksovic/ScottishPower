package com.example.scottishpower.ui.albumlist

sealed class SortType(val ascending: Boolean) {
    class Username(ascending: Boolean): SortType(ascending)
    class Title(ascending: Boolean): SortType(ascending)
}