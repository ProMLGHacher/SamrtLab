package com.example.samrtlab.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun CustomDropDown(
    value: String,
    items: List<String>,
    onChange: (String) -> Unit
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color(0xFFF5F5F9), shape = RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFFEBEBEB), shape = RoundedCornerShape(10.dp))
            .clickable {
                isExpanded.value = true
            }
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            value,
            color = Color(0xFF939396),
            modifier = Modifier
                .fillMaxWidth()
        )
        DropdownMenu(modifier = Modifier.fillMaxWidth(), properties = PopupProperties(
            dismissOnBackPress = true
        ), expanded = isExpanded.value, onDismissRequest = { isExpanded.value = false }) {
            items.forEach {
                DropdownMenuItem(onClick = {
                     onChange(it)
                    isExpanded.value = false
                }) {
                    Text(it)
                }
            }
        }
    }
}