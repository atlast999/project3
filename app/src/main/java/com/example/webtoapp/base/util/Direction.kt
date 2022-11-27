package com.example.webtoapp.base.util

import androidx.navigation.NavDirections

sealed class Direction {
    object BackWard : Direction()
    data class NavDirection(val direction: NavDirections): Direction()
}