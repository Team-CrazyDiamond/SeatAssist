package com.example.seatassist.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun MainPlaceholder(text: String, textAlign: TextAlign = TextAlign.End, fontSize: TextUnit = 16.sp) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = textAlign,
        style = MaterialTheme.typography.h4,
        fontSize = fontSize,
        color = MaterialTheme.colors.onPrimary.copy(alpha = ContentAlpha.medium)
    )
}