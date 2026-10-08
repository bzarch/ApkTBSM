package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.education.TbsmStaticDatabase
import com.example.education.QuizQuestion
import com.example.ui.components.GlassCard
import com.example.ui.components.SafetyWarningBanner
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@Composable
fun BelajarScreen(
    viewModel: MainViewModel,
    onNavigate: (String, String?, String?) -> Unit
) {
    var activeSubTab by remember { mutableStateOf("TEXTBOOK") } // TEXTBOOK, COMPONENTS, PRACTICUM, QUIZ, FLASHCARD, TOOLS, K3

    val subTabs = listOf(
        Pair("TEXTBOOK", "Buku Digital"),
        Pair("COMPONENTS", "Komponen"),
        Pair("PRACTICUM", "Praktikum"),
        Pair("K3", "K3 & Safety"),
        Pair("QUIZ", "Kuis & Evaluasi"),
        Pair("FLASHCARD", "Flashcard"),
        Pair("TOOLS", "Tools Bengkel")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Pusat Pembelajaran TBSM AFGO",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Text(
                text = "Materi kurikulum kejuruan, bank soal, panduan praktikum & K3 bengkel",
                style = MaterialTheme.typography.bodySmall,
                color = MetallicSilverMuted
            )
        }

        // Sub Tabs
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(subTabs) { tab ->
                    FilterChip(
                        selected = activeSubTab == tab.first,
                        onClick = { activeSubTab = tab.first },
                        label = { Text(tab.second) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaroonPrimary,
                            selectedLabelColor = TextWhite,
                            containerColor = GraphiteSurface,
                            labelColor = MetallicSilver
                        )
                    )
                }
            }
        }

        when (activeSubTab) {
            "TEXTBOOK" -> {
                items(TbsmStaticDatabase.materials) { mat ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onNavigate("MATERIAL_DETAIL", mat.id, mat.category) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(mat.category, style = MaterialTheme.typography.labelSmall, color = MaroonLight)
                            Icon(imageVector = Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, tint = MetallicSilverMuted)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(mat.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(mat.summary, style = MaterialTheme.typography.bodySmall, color = TextMuted)
                    }
                }
            }

            "COMPONENTS" -> {
                items(TbsmStaticDatabase.components) { comp ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onNavigate("COMPONENT_DETAIL", comp.id, comp.category) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(comp.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                            StatusBadge(text = comp.category, statusType = "INFO")
                        }
                        Text(comp.englishName, style = MaterialTheme.typography.bodySmall, color = MaroonLight)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(comp.functionDesc, style = MaterialTheme.typography.bodySmall, color = MetallicSilver, maxLines = 2)
                    }
                }
            }

            "PRACTICUM" -> {
                items(TbsmStaticDatabase.practicumModules) { mod ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onNavigate("PRACTICUM_DETAIL", mod.id, null) }
                    ) {
                        Text("Modul Praktikum Bengkel", style = MaterialTheme.typography.labelSmall, color = MaroonLight)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(mod.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Tujuan: " + mod.objectives.firstOrNull(), style = MaterialTheme.typography.bodySmall, color = TextMuted)
                    }
                }
            }

            "K3" -> {
                items(TbsmStaticDatabase.k3Topics) { k3 ->
                    GlassCard(modifier = Modifier.fillMaxWidth(), borderColor = MaroonAccent.copy(alpha = 0.5f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(k3.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                            StatusBadge(text = k3.category, statusType = "WARNING")
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(k3.summary, style = MaterialTheme.typography.bodySmall, color = MetallicSilver)

                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Pedoman Keselamatan Kerja:", fontWeight = FontWeight.SemiBold, color = MaroonLight)
                        k3.guidelines.forEach { g ->
                            Text("• $g", style = MaterialTheme.typography.bodySmall, color = TextWhite)
                            Spacer(modifier = Modifier.height(2.dp))
                        }

                        if (k3.emergencySteps.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            SafetyWarningBanner(warningText = k3.emergencySteps.joinToString("\n"))
                        }
                    }
                }
            }

            "QUIZ" -> {
                item {
                    QuizSection()
                }
            }

            "FLASHCARD" -> {
                item {
                    FlashcardSection()
                }
            }

            "TOOLS" -> {
                items(TbsmStaticDatabase.tools) { tool ->
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(tool.name, fontWeight = FontWeight.Bold, color = TextWhite)
                            StatusBadge(text = tool.category, statusType = "INFO")
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(tool.functionDesc, style = MaterialTheme.typography.bodySmall, color = MetallicSilver)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Cara Baca: ${tool.howToRead}", style = MaterialTheme.typography.labelSmall, color = MaroonLight)
                    }
                }
            }
        }
    }
}

@Composable
fun QuizSection() {
    var currentIndex by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var hasAnswered by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }

    val question = TbsmStaticDatabase.quizzes.getOrNull(currentIndex)

    if (question != null) {
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Soal ${currentIndex + 1} dari ${TbsmStaticDatabase.quizzes.size}", style = MaterialTheme.typography.labelMedium, color = MaroonLight)
                Text("Skor: $score", style = MaterialTheme.typography.labelMedium, color = TextWhite)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(question.question, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
            Spacer(modifier = Modifier.height(16.dp))

            question.options.forEachIndexed { idx, opt ->
                val btnColor = when {
                    hasAnswered && idx == question.correctIndex -> StatusSuccess
                    hasAnswered && idx == selectedOption -> StatusError
                    else -> GraphiteSurfaceVariant
                }

                Button(
                    onClick = {
                        if (!hasAnswered) {
                            selectedOption = idx
                            hasAnswered = true
                            if (idx == question.correctIndex) {
                                score += 20
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = btnColor)
                ) {
                    Text(opt, color = TextWhite)
                }
            }

            if (hasAnswered) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = question.explanation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MetallicSilver
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        hasAnswered = false
                        selectedOption = null
                        if (currentIndex < TbsmStaticDatabase.quizzes.size - 1) {
                            currentIndex++
                        } else {
                            currentIndex = 0
                            score = 0
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (currentIndex < TbsmStaticDatabase.quizzes.size - 1) "Soal Berikutnya" else "Ulangi Kuis")
                }
            }
        }
    }
}

@Composable
fun FlashcardSection() {
    var currentIndex by remember { mutableStateOf(0) }
    var isRevealed by remember { mutableStateOf(false) }

    val card = TbsmStaticDatabase.flashcards.getOrNull(currentIndex)

    if (card != null) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            onClick = { isRevealed = !isRevealed }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Flashcard ${card.category} • Ketuk untuk Balik", style = MaterialTheme.typography.labelSmall, color = MaroonLight)

                Text(
                    text = if (isRevealed) card.backAnswer else card.frontQuestion,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isRevealed) Color(0xFFFF8A80) else TextWhite
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            if (currentIndex > 0) {
                                currentIndex--
                                isRevealed = false
                            }
                        },
                        enabled = currentIndex > 0
                    ) {
                        Text("Sebelumnya")
                    }
                    TextButton(
                        onClick = {
                            if (currentIndex < TbsmStaticDatabase.flashcards.size - 1) {
                                currentIndex++
                                isRevealed = false
                            }
                        },
                        enabled = currentIndex < TbsmStaticDatabase.flashcards.size - 1
                    ) {
                        Text("Berikutnya")
                    }
                }
            }
        }
    }
}
