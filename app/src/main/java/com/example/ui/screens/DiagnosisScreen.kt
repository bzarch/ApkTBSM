package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.diagnostic.DiagnosticDecisionTrees
import com.example.diagnostic.DiagnosisResult
import com.example.ui.components.GlassCard
import com.example.ui.components.SafetyWarningBanner
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@Composable
fun DiagnosisScreen(
    viewModel: MainViewModel,
    onNavigate: (String, String?, String?) -> Unit
) {
    var activeStepId by remember { mutableStateOf("start") }
    var historySteps by remember { mutableStateOf<List<String>>(emptyList()) }
    var finalResult by remember { mutableStateOf<DiagnosisResult?>(null) }

    val currentStep = DiagnosticDecisionTrees.trees[activeStepId]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Diagnostic Assistant",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "Decision Tree Troubleshooting Berdasarkan Gejala Nyata",
                        style = MaterialTheme.typography.bodySmall,
                        color = MetallicSilverMuted
                    )
                }

                if (historySteps.isNotEmpty() || finalResult != null) {
                    TextButton(onClick = {
                        activeStepId = "start"
                        historySteps = emptyList()
                        finalResult = null
                    }) {
                        Text("Reset Alur", color = MaroonLight)
                    }
                }
            }
        }

        // Active Troubleshooting Tree
        if (finalResult != null) {
            val res = finalResult!!
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = if (res.status == "PERINGATAN") StatusError else StatusWarning
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = res.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        StatusBadge(text = res.status, statusType = res.status)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Kemungkinan Penyebab Utama:", fontWeight = FontWeight.SemiBold, color = MaroonLight)
                    res.possibleCauses.forEach { cause ->
                        Text("• $cause", style = MaterialTheme.typography.bodyMedium, color = TextWhite)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Langkah Pemeriksaan Prioritas:", fontWeight = FontWeight.SemiBold, color = MetallicSilver)
                    res.inspectionSteps.forEachIndexed { idx, step ->
                        Text("${idx + 1}. $step", style = MaterialTheme.typography.bodySmall, color = TextWhite)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Alat yang Diperlukan:", fontWeight = FontWeight.SemiBold, color = MetallicSilverMuted)
                    Text(res.recommendedTools.joinToString(", "), style = MaterialTheme.typography.bodySmall, color = TextMuted)

                    Spacer(modifier = Modifier.height(12.dp))
                    SafetyWarningBanner(warningText = res.safetyWarning)

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onNavigate("AI_CHAT", null, null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
                    ) {
                        Icon(imageVector = Icons.Default.SmartToy, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("KONSULTASIKAN KE AI TBSM")
                    }
                }
            }
        } else if (currentStep != null) {
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    if (historySteps.isNotEmpty()) {
                        Text(
                            text = "Langkah ${historySteps.size + 1} Alur Diagnosa",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaroonLight
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentStep.question,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentStep.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MetallicSilver
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Pilih Kondisi di Lapangan:",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    currentStep.options.forEach { opt ->
                        OutlinedButton(
                            onClick = {
                                if (opt.result != null) {
                                    finalResult = opt.result
                                } else if (opt.nextStepId != null) {
                                    historySteps = historySteps + activeStepId
                                    activeStepId = opt.nextStepId
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = GraphiteSurfaceVariant.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = opt.label,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = TextWhite
                            )
                        }
                    }

                    if (historySteps.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(
                            onClick = {
                                val prev = historySteps.last()
                                historySteps = historySteps.dropLast(1)
                                activeStepId = prev
                            }
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = MetallicSilverMuted)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Kembali ke Pertanyaan Sebelumnya", color = MetallicSilverMuted)
                        }
                    }
                }
            }
        }

        // Multimeter Guide Quick Action
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavigate("MULTIMETER_GUIDE", null, null) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = MaroonLight)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Panduan Multimeter & Pengukuran", fontWeight = FontWeight.Bold, color = TextWhite)
                            Text("Langkah ukur tegangan aki, resistansi koil, kontinuitas", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                        }
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MetallicSilver)
                }
            }
        }
    }
}
