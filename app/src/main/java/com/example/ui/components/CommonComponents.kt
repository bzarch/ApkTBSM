package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    borderColor: Color = SurfaceBorder,
    backgroundColor: Color = GraphiteSurface.copy(alpha = 0.90f),
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val clickableModifier = if (onClick != null) {
        modifier
            .clip(shape)
            .clickable(onClick = onClick)
    } else {
        modifier.clip(shape)
    }

    Card(
        modifier = clickableModifier,
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun StatusBadge(
    text: String,
    statusType: String = "INFO", // SUCCESS, WARNING, ERROR, INFO
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (statusType.uppercase()) {
        "SUCCESS", "COMPLETED", "NORMAL", "IN STOCK" -> Pair(StatusSuccess.copy(alpha = 0.2f), Color(0xFF4CAF50))
        "WARNING", "INSPECTION", "WORKING", "LOW STOCK", "PERLU PEMERIKSAAN" -> Pair(StatusWarning.copy(alpha = 0.2f), Color(0xFFFFB74D))
        "ERROR", "WAITING", "OUT OF STOCK", "PERINGATAN" -> Pair(StatusError.copy(alpha = 0.2f), Color(0xFFEF5350))
        else -> Pair(StatusInfo.copy(alpha = 0.2f), Color(0xFF42A5F5))
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = bgColor,
        border = BorderStroke(1.dp, textColor.copy(alpha = 0.5f))
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun SafetyWarningBanner(
    warningText: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaroonDark.copy(alpha = 0.6f)),
        border = BorderStroke(1.dp, MaroonAccent.copy(alpha = 0.8f))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "⚠️ K3 & SAFETY:",
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFFFF8A80)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = warningText,
                style = MaterialTheme.typography.bodySmall,
                color = TextWhite
            )
        }
    }
}
