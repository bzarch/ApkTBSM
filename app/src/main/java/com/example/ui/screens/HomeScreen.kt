package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.education.TbsmStaticDatabase
import com.example.ui.components.GlassCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigate: (String, String?, String?) -> Unit
) {
    val vehicles by viewModel.vehicles.collectAsState()
    val reminders by viewModel.reminders.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Header with Badge
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaroonDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_tbsm_logo),
                            contentDescription = "TBSM AFGO Logo",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "TBSM AFGO",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Text(
                            text = "Teknik & Bisnis Sepeda Motor SMK AFGO",
                            style = MaterialTheme.typography.bodySmall,
                            color = MetallicSilverMuted
                        )
                    }
                }

                IconButton(
                    onClick = { onNavigate("SETTINGS", null, null) },
                    modifier = Modifier.testTag("btn_settings_header")
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MetallicSilver
                    )
                }
            }
        }

        // Global Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_search_input"),
                placeholder = {
                    Text(
                        "Cari komponen, materi, diagnosis, tools...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = MaroonLight)
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextMuted)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaroonLight,
                    unfocusedBorderColor = SurfaceBorder,
                    focusedContainerColor = GraphiteSurface,
                    unfocusedContainerColor = GraphiteSurface
                )
            )
        }

        // Search Results when query is active
        if (searchQuery.isNotBlank()) {
            val queryLower = searchQuery.lowercase()
            val filteredComponents = TbsmStaticDatabase.components.filter {
                it.name.lowercase().contains(queryLower) || it.englishName.lowercase().contains(queryLower) || it.category.lowercase().contains(queryLower)
            }
            val filteredMaterials = TbsmStaticDatabase.materials.filter {
                it.title.lowercase().contains(queryLower) || it.summary.lowercase().contains(queryLower)
            }
            val filteredTools = TbsmStaticDatabase.tools.filter {
                it.name.lowercase().contains(queryLower) || it.englishName.lowercase().contains(queryLower)
            }

            item {
                Text(
                    text = "Hasil Pencarian Global:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaroonLight,
                    fontWeight = FontWeight.Bold
                )
            }

            if (filteredComponents.isEmpty() && filteredMaterials.isEmpty() && filteredTools.isEmpty()) {
                item {
                    Text(
                        text = "Tidak ditemukan hasil untuk '$searchQuery'. Silakan gunakan kata kunci lain.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )
                }
            } else {
                items(filteredComponents) { comp ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onNavigate("COMPONENT_DETAIL", comp.id, comp.category) }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Build, contentDescription = null, tint = MaroonLight)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(comp.name, fontWeight = FontWeight.SemiBold, color = TextWhite)
                                Text("Komponen Otomotif • ${comp.category}", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                            }
                        }
                    }
                }
                items(filteredMaterials) { mat ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onNavigate("MATERIAL_DETAIL", mat.id, mat.category) }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = MetallicSilver)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(mat.title, fontWeight = FontWeight.SemiBold, color = TextWhite)
                                Text("Materi TBSM • ${mat.category}", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                            }
                        }
                    }
                }
            }
        } else {
            // Quick Action Grid (12 Modul Inti)
            item {
                Text(
                    text = "Akses Cepat TBSM",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            item {
                val actions = listOf(
                    Triple("KALKULATOR", Icons.Default.Calculate, MaroonPrimary),
                    Triple("DIAGNOSIS", Icons.Default.Warning, Color(0xFFC62828)),
                    Triple("ENGINE", Icons.Default.Engineering, Color(0xFF8E24AA)),
                    Triple("ELECTRICAL", Icons.Default.Bolt, Color(0xFFE65100)),
                    Triple("EFI INJEKSI", Icons.Default.Sensors, Color(0xFF00897B)),
                    Triple("CVT MATIC", Icons.Default.Settings, Color(0xFF1E88E5)),
                    Triple("REM & SIKLUS", Icons.Default.Build, Color(0xFF5D4037)),
                    Triple("PRAKTIKUM", Icons.Default.School, MaroonDark),
                    Triple("GARASI", Icons.Default.TwoWheeler, Color(0xFF37474F)),
                    Triple("SERVIS & WO", Icons.Default.ReceiptLong, Color(0xFF00695C)),
                    Triple("INVENTARIS", Icons.Default.Inventory2, Color(0xFF455A64)),
                    Triple("AI ASSISTANT", Icons.Default.SmartToy, MaroonAccent)
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    for (row in actions.chunked(4)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for (action in row) {
                                GlassCard(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(84.dp)
                                        .testTag("quick_${action.first}"),
                                    backgroundColor = GraphiteSurfaceVariant.copy(alpha = 0.8f),
                                    shape = RoundedCornerShape(12.dp),
                                    onClick = {
                                        when (action.first) {
                                            "KALKULATOR" -> onNavigate("TOOLS", "CALCULATORS", null)
                                            "DIAGNOSIS" -> onNavigate("DIAGNOSIS", null, null)
                                            "ENGINE" -> onNavigate("BELAJAR", "COMPONENTS", "Mesin")
                                            "ELECTRICAL" -> onNavigate("TOOLS", "ELECTRICAL", null)
                                            "EFI INJEKSI" -> onNavigate("BELAJAR", "COMPONENTS", "EFI")
                                            "CVT MATIC" -> onNavigate("BELAJAR", "COMPONENTS", "CVT")
                                            "REM & SIKLUS" -> onNavigate("BELAJAR", "COMPONENTS", "Pengereman")
                                            "PRAKTIKUM" -> onNavigate("BELAJAR", "PRACTICUM", null)
                                            "GARASI" -> onNavigate("GARAGE", null, null)
                                            "SERVIS & WO" -> onNavigate("GARAGE", "SERVICE", null)
                                            "INVENTARIS" -> onNavigate("GARAGE", "INVENTORY", null)
                                            "AI ASSISTANT" -> onNavigate("AI_CHAT", null, null)
                                        }
                                    }
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = action.second,
                                            contentDescription = action.first,
                                            tint = action.third,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = action.first,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextWhite,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Daily Tip & Safety Reminder
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = MaroonPrimary.copy(alpha = 0.6f)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = MaroonLight)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Tips Harian TBSM AFGO",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Saat menyetel celah klep, pastikan piston benar-benar di TMA akhir langkah kompresi (kedua rocker arm bebas goyang), bukan TMA langkah buang.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MetallicSilver
                            )
                        }
                    }
                }
            }

            // Recent Vehicle & Service Reminders
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pengingat Servis Terdekat",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    TextButton(onClick = { onNavigate("GARAGE", "REMINDERS", null) }) {
                        Text("Lihat Semua", color = MaroonLight)
                    }
                }
            }

            if (reminders.isEmpty()) {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Belum ada pengingat servis. Tambahkan pengingat ganti oli atau servis CVT di Garasi.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted
                        )
                    }
                }
            } else {
                items(reminders.take(2)) { reminder ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onNavigate("GARAGE", "REMINDERS", null) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(reminder.title, fontWeight = FontWeight.SemiBold, color = TextWhite)
                                Text("${reminder.vehicleName} • Target: ${if (reminder.targetMileage > 0) "${reminder.targetMileage} km" else reminder.targetDate}", style = MaterialTheme.typography.bodySmall, color = MetallicSilverMuted)
                            }
                            StatusBadge(text = if (reminder.isCompleted) "Selesai" else "Mendekati", statusType = if (reminder.isCompleted) "SUCCESS" else "WARNING")
                        }
                    }
                }
            }
        }
    }
}
