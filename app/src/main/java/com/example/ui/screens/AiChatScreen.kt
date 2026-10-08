package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.GlassCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@Composable
fun AiChatScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val messages by viewModel.chatMessages.collectAsState()
    val isThinking by viewModel.isAiThinking.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GraphiteBackground)
    ) {
        // Chat Header
        Surface(
            color = GraphiteSurface,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextWhite)
                    }
                    Column {
                        Text("TBSM AFGO AI", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                        Text(
                            text = "Provider: ${uiState.activeAiProvider} • ${if (uiState.isAiEnabled) "Aktif" else "Offline"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaroonLight
                        )
                    }
                }

                IconButton(onClick = { viewModel.clearChat() }) {
                    Icon(imageVector = Icons.Default.DeleteSweep, contentDescription = "Clear Chat", tint = MetallicSilverMuted)
                }
            }
        }

        // Messages List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { msg ->
                val isUser = msg.first == "user"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                ) {
                    GlassCard(
                        modifier = Modifier.widthIn(max = 300.dp),
                        backgroundColor = if (isUser) MaroonPrimary else GraphiteSurfaceVariant,
                        borderColor = if (isUser) MaroonLight else SurfaceBorder,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isUser) 16.dp else 4.dp,
                            bottomEnd = if (isUser) 4.dp else 16.dp
                        )
                    ) {
                        Text(
                            text = msg.second,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextWhite
                        )
                    }
                }
            }

            if (isThinking) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        GlassCard(
                            modifier = Modifier.widthIn(max = 240.dp),
                            backgroundColor = GraphiteSurfaceVariant
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = MaroonLight
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "TBSM AI sedang menganalisis...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MetallicSilver
                                )
                            }
                        }
                    }
                }
            }
        }

        // Input Field
        Surface(
            color = GraphiteSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Tanyakan masalah motor, K3, busi...", style = MaterialTheme.typography.bodyMedium, color = TextMuted) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("ai_chat_input"),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = GraphiteBackground,
                        unfocusedContainerColor = GraphiteBackground,
                        focusedBorderColor = MaroonLight,
                        unfocusedBorderColor = SurfaceBorder
                    ),
                    maxLines = 3
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            val txt = inputText
                            inputText = ""
                            viewModel.sendChatMessage(txt)
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaroonPrimary, shape = RoundedCornerShape(24.dp))
                        .testTag("ai_send_button")
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = TextWhite)
                }
            }
        }
    }
}
