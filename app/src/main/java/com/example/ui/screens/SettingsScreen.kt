package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.GlassCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val envVars by viewModel.envVariables.collectAsState()

    var showAddEnvDialog by remember { mutableStateOf(false) }

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
                Text("Pengaturan & Konfigurasi", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextWhite)
            }
        }

        // Performance & Low-End Mode
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("Performa & Tampilan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Low-End Mode", fontWeight = FontWeight.SemiBold, color = TextWhite)
                        Text("Mengurangi efek blur & animasi untuk HP spesifikasi minim", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                    }
                    Switch(
                        checked = uiState.isLowEndMode,
                        onCheckedChange = { viewModel.toggleLowEndMode() },
                        colors = SwitchDefaults.colors(checkedThumbColor = MaroonLight, checkedTrackColor = MaroonDark)
                    )
                }
            }
        }

        // AI Configuration
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("Konfigurasi AI Assistant", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Aktifkan Fitur AI", fontWeight = FontWeight.SemiBold, color = TextWhite)
                        Text("Bila non-aktif, aplikasi memakai database lokal offline", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                    }
                    Switch(
                        checked = uiState.isAiEnabled,
                        onCheckedChange = { viewModel.toggleAiMode() },
                        colors = SwitchDefaults.colors(checkedThumbColor = MaroonLight, checkedTrackColor = MaroonDark)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text("Pilih Provider AI:", style = MaterialTheme.typography.labelMedium, color = MetallicSilver)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Google Gemini", "Offline KB").forEach { prov ->
                        FilterChip(
                            selected = uiState.activeAiProvider == prov,
                            onClick = { viewModel.setAiProvider(prov) },
                            label = { Text(prov) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaroonPrimary,
                                selectedLabelColor = TextWhite
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.testAiConnection() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = GraphiteSurfaceVariant)
                ) {
                    if (uiState.isTestingConnection) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = MaroonLight)
                    } else {
                        Text("UJI KONEKSI AI (TEST CONNECTION)", color = TextWhite)
                    }
                }

                if (uiState.connectionTestResult != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.connectionTestResult ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (uiState.connectionTestResult?.contains("Berhasil") == true || uiState.connectionTestResult?.contains("siap") == true) StatusSuccess else StatusError
                    )
                }
            }
        }

        // Environment Variables Manager
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Environment Variables (ENV)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                    IconButton(onClick = { showAddEnvDialog = true }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add ENV", tint = MaroonLight)
                    }
                }
                Text("Kunci rahasia API dimasking dan disimpan aman secara lokal", style = MaterialTheme.typography.bodySmall, color = TextMuted)

                Spacer(modifier = Modifier.height(12.dp))
                envVars.forEach { env ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(env.name, fontWeight = FontWeight.Bold, color = MaroonLight)
                            Text(
                                text = if (env.isSecret) "••••••••••••••••" else env.value,
                                style = MaterialTheme.typography.bodySmall,
                                color = MetallicSilver
                            )
                        }
                        IconButton(onClick = { viewModel.deleteEnvVariable(env.name) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = StatusError.copy(alpha = 0.7f))
                        }
                    }
                    Divider(color = SurfaceBorder.copy(alpha = 0.5f))
                }
            }
        }

        // About & School Info
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Text("Tentang TBSM AFGO", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Versi: 1.0.0 (Build Produksi Rilis)", style = MaterialTheme.typography.bodySmall, color = MaroonLight)
                Text("Sekolah: SMK AFGO", style = MaterialTheme.typography.bodySmall, color = TextWhite)
                Text("Kompetensi Keahlian: Teknik & Bisnis Sepeda Motor", style = MaterialTheme.typography.bodySmall, color = MetallicSilver)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Didesain khusus untuk pembelajaran teori, ujian kuis, praktikum servis, diagnosa kerusakan motor, dan operasional bengkel.", style = MaterialTheme.typography.bodySmall, color = TextMuted)
            }
        }
    }

    // Add ENV Dialog
    if (showAddEnvDialog) {
        var keyInput by remember { mutableStateOf("") }
        var valueInput by remember { mutableStateOf("") }
        var descInput by remember { mutableStateOf("") }
        var isSecret by remember { mutableStateOf(true) }

        AlertDialog(
            onDismissRequest = { showAddEnvDialog = false },
            title = { Text("Tambah Environment Variable", color = TextWhite) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = keyInput,
                        onValueChange = { keyInput = it },
                        label = { Text("Nama ENV (misal: GEMINI_API_KEY)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = valueInput,
                        onValueChange = { valueInput = it },
                        label = { Text("Nilai ENV") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = descInput,
                        onValueChange = { descInput = it },
                        label = { Text("Deskripsi") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (keyInput.isNotBlank()) {
                            viewModel.saveEnvVariable(keyInput, valueInput, descInput, isSecret)
                            showAddEnvDialog = false
                            Toast.makeText(context, "ENV berhasil disimpan!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
                ) {
                    Text("SIMPAN")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddEnvDialog = false }) {
                    Text("BATAL")
                }
            },
            containerColor = GraphiteSurface
        )
    }
}
