package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.education.TbsmStaticDatabase
import com.example.ui.components.GlassCard
import com.example.ui.components.SafetyWarningBanner
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun ComponentDetailScreen(
    componentId: String,
    onBack: () -> Unit
) {
    val comp = TbsmStaticDatabase.components.find { it.id == componentId }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextWhite)
                }
                Text("Detail Komponen", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextWhite)
            }
        }

        if (comp == null) {
            item {
                Text("Komponen tidak ditemukan.", color = TextMuted)
            }
        } else {
            item {
                GlassCard(modifier = Modifier.fillMaxWidth(), borderColor = MaroonLight) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(comp.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TextWhite)
                            Text(comp.englishName, style = MaterialTheme.typography.titleSmall, color = MaroonLight)
                        }
                        StatusBadge(text = comp.category, statusType = "INFO")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Lokasi Penempatan: ${comp.location}", style = MaterialTheme.typography.bodySmall, color = MetallicSilverMuted)
                }
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text("Fungsi Utama", fontWeight = FontWeight.Bold, color = TextWhite)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(comp.functionDesc, style = MaterialTheme.typography.bodyMedium, color = MetallicSilver)

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Prinsip & Mekanisme Kerja", fontWeight = FontWeight.Bold, color = TextWhite)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(comp.workingPrinciple, style = MaterialTheme.typography.bodyMedium, color = MetallicSilver)
                }
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text("Gejala Kerusakan & Masalah Umum", fontWeight = FontWeight.Bold, color = MaroonLight)
                    Spacer(modifier = Modifier.height(8.dp))
                    comp.damageSymptoms.forEach { sym ->
                        Text("• $sym", style = MaterialTheme.typography.bodyMedium, color = TextWhite)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Prosedur Pemeriksaan SOP Bengkel", fontWeight = FontWeight.Bold, color = MetallicSilver)
                    Spacer(modifier = Modifier.height(8.dp))
                    comp.inspectionMethod.forEachIndexed { idx, insp ->
                        Text("${idx + 1}. $insp", style = MaterialTheme.typography.bodySmall, color = TextWhite)
                    }
                }
            }

            item {
                SafetyWarningBanner(warningText = comp.k3Warning)
            }
        }
    }
}

@Composable
fun MaterialDetailScreen(
    materialId: String,
    onBack: () -> Unit
) {
    val mat = TbsmStaticDatabase.materials.find { it.id == materialId }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextWhite)
                }
                Text("Buku Materi TBSM", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextWhite)
            }
        }

        if (mat == null) {
            item {
                Text("Materi tidak ditemukan.", color = TextMuted)
            }
        } else {
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text(mat.category, style = MaterialTheme.typography.labelSmall, color = MaroonLight)
                    Text(mat.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TextWhite)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(mat.summary, style = MaterialTheme.typography.bodyMedium, color = MetallicSilver)
                }
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text("Isi Pembahasan Lengkap", fontWeight = FontWeight.Bold, color = TextWhite)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(mat.fullContent, style = MaterialTheme.typography.bodyMedium, color = MetallicSilver)
                }
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text("Istilah Teknis Penting (Glossary)", fontWeight = FontWeight.Bold, color = MaroonLight)
                    Spacer(modifier = Modifier.height(8.dp))
                    mat.keyTerms.forEach { (term, def) ->
                        Text("• $term: $def", style = MaterialTheme.typography.bodySmall, color = TextWhite)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

            item {
                SafetyWarningBanner(warningText = mat.safetyNote)
            }
        }
    }
}

@Composable
fun PracticumDetailScreen(
    practicumId: String,
    onBack: () -> Unit
) {
    val prak = TbsmStaticDatabase.practicumModules.find { it.id == practicumId }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextWhite)
                }
                Text("Lembar Kerja Praktikum", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextWhite)
            }
        }

        if (prak == null) {
            item {
                Text("Modul praktikum tidak ditemukan.", color = TextMuted)
            }
        } else {
            item {
                GlassCard(modifier = Modifier.fillMaxWidth(), borderColor = MaroonLight) {
                    Text(prak.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextWhite)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Tujuan Praktikum:", fontWeight = FontWeight.SemiBold, color = MaroonLight)
                    prak.objectives.forEach { obj ->
                        Text("• $obj", style = MaterialTheme.typography.bodySmall, color = TextWhite)
                    }
                }
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text("Alat & Bahan Praktik", fontWeight = FontWeight.Bold, color = TextWhite)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Alat: " + prak.toolsNeeded.joinToString(", "), style = MaterialTheme.typography.bodySmall, color = MetallicSilver)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Bahan: " + prak.materialsNeeded.joinToString(", "), style = MaterialTheme.typography.bodySmall, color = MetallicSilver)
                }
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text("Langkah Kerja & Prosedur SOP", fontWeight = FontWeight.Bold, color = MaroonLight)
                    Spacer(modifier = Modifier.height(8.dp))
                    prak.procedures.forEachIndexed { idx, p ->
                        Text("${idx + 1}. $p", style = MaterialTheme.typography.bodySmall, color = TextWhite)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text("Standar Ukuran Pengukuran Lapangan", fontWeight = FontWeight.Bold, color = TextWhite)
                    Spacer(modifier = Modifier.height(8.dp))
                    prak.standardMeasurements.forEach { (item, std) ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(item, style = MaterialTheme.typography.bodySmall, color = MetallicSilver)
                            Text(std, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaroonLight)
                        }
                        Divider(color = SurfaceBorder, modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}
