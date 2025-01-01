package com.example.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

abstract class BasePermissionComposeState {
    var visible by mutableStateOf(false)

    fun trigger() {
        visible = true
    }

    fun dismiss() {
        visible = false
    }
}

class PermissionComposeState : BasePermissionComposeState()

@Composable
fun rememberPermissionComposeState() = remember { PermissionComposeState() }