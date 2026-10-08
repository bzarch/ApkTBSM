package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.InventoryPart
import com.example.data.model.ServiceRecord
import com.example.data.model.Vehicle
import com.example.data.model.WorkOrder
import com.example.ui.components.GlassCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@Composable
fun GarageScreen(
    viewModel: MainViewModel,
    onNavigate: (String, String?, String?) -> Unit
) {
    val vehicles by viewModel.vehicles.collectAsState()
    val services by viewModel.serviceRecords.collectAsState()
    val workOrders by viewModel.workOrders.collectAsState()
    val inventory by viewModel.inventoryParts.collectAsState()

    var activeSubTab by remember { mutableStateOf("VEHICLES") } // VEHICLES, SERVICES, WORK_ORDERS, INVENTORY, BUSINESS

    var showAddVehicleDialog by remember { mutableStateOf(false) }

    val tabs = listOf(
        Pair("VEHICLES", "Garasi Motor"),
        Pair("SERVICES", "Riwayat Servis"),
        Pair("WORK_ORDERS", "Work Order"),
        Pair("INVENTORY", "Stok Sparepart"),
        Pair("BUSINESS", "Kas Bengkel")
    )

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
                        text = "Workshop & Garage Manager",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "Manajemen bengkel digital, armada servis, dan bisnis TBSM",
                        style = MaterialTheme.typography.bodySmall,
                        color = MetallicSilverMuted
                    )
                }

                if (activeSubTab == "VEHICLES") {
                    IconButton(
                        onClick = { showAddVehicleDialog = true },
                        modifier = Modifier.testTag("btn_add_vehicle")
                    ) {
                        Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add", tint = MaroonLight)
                    }
                }
            }
        }

        // Sub Navigation Tabs
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tabs) { tab ->
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
            "VEHICLES" -> {
                if (vehicles.isEmpty()) {
                    item {
                        GlassCard(modifier = Modifier.fillMaxWidth()) {
                            Text("Belum ada kendaraan di garasi.", color = TextMuted)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { showAddVehicleDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
                            ) {
                                Text("TAMBAH MOTOR")
                            }
                        }
                    }
                } else {
                    items(vehicles) { v ->
                        GlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onNavigate("VEHICLE_DETAIL", v.id.toString(), null) }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(v.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                                    Text("${v.brand} ${v.model} • Tahun ${v.year}", style = MaterialTheme.typography.bodySmall, color = MaroonLight)
                                    Text("Odometer: ${v.mileage} km | Pelat: ${v.licensePlate}", style = MaterialTheme.typography.bodySmall, color = MetallicSilver)
                                }
                                Icon(imageVector = Icons.Default.TwoWheeler, contentDescription = null, tint = MetallicSilver)
                            }
                        }
                    }
                }
            }

            "SERVICES" -> {
                items(services) { s ->
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(s.vehicleName, fontWeight = FontWeight.Bold, color = TextWhite)
                            StatusBadge(text = s.status, statusType = s.status)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Keluhan: ${s.complaint}", style = MaterialTheme.typography.bodySmall, color = MaroonLight)
                        Text("Pekerjaan: ${s.workPerformed}", style = MaterialTheme.typography.bodySmall, color = MetallicSilver)
                        Text("Mekanik: ${s.technician} • Biaya: Rp ${String.format("%,.0f", s.totalCost)}", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                    }
                }
            }

            "WORK_ORDERS" -> {
                items(workOrders) { wo ->
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(wo.customerName, fontWeight = FontWeight.Bold, color = TextWhite)
                            StatusBadge(text = wo.status, statusType = wo.status)
                        }
                        Text("Unit: ${wo.vehicleName}", style = MaterialTheme.typography.bodySmall, color = MaroonLight)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Diagnosa: ${wo.inspectionResult}", style = MaterialTheme.typography.bodySmall, color = MetallicSilver)
                        Text("Tindakan: ${wo.workDetails}", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                    }
                }
            }

            "INVENTORY" -> {
                items(inventory) { item ->
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(item.partName, fontWeight = FontWeight.Bold, color = TextWhite)
                                Text("${item.partCode} • ${item.category}", style = MaterialTheme.typography.bodySmall, color = MetallicSilverMuted)
                            }
                            StatusBadge(text = "Stok: ${item.stock}", statusType = if (item.stock <= item.minStock) "WARNING" else "SUCCESS")
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Harga Jual: Rp ${String.format("%,.0f", item.sellPrice)}", style = MaterialTheme.typography.bodySmall, color = MaroonLight)
                            Text("Lokasi: ${item.location}", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                        }
                    }
                }
            }

            "BUSINESS" -> {
                item {
                    val totalIncome = services.sumOf { it.totalCost }
                    val totalPartsCost = services.sumOf { it.costParts }
                    val estimatedProfit = totalIncome - (totalPartsCost * 0.7)

                    GlassCard(modifier = Modifier.fillMaxWidth(), borderColor = MaroonLight) {
                        Text("Ringkasan Keuangan Bengkel Latihan TBSM", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Omset Servis:", color = MetallicSilver)
                            Text("Rp ${String.format("%,.0f", totalIncome)}", fontWeight = FontWeight.Bold, color = TextWhite)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Biaya Sparepart:", color = MetallicSilver)
                            Text("Rp ${String.format("%,.0f", totalPartsCost)}", fontWeight = FontWeight.Bold, color = StatusWarning)
                        }
                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = SurfaceBorder)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Estimasi Keuntungan Bersih:", fontWeight = FontWeight.Bold, color = TextWhite)
                            Text("Rp ${String.format("%,.0f", estimatedProfit)}", fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                        }
                    }
                }
            }
        }
    }

    // Add Vehicle Dialog
    if (showAddVehicleDialog) {
        var nameInput by remember { mutableStateOf("") }
        var brandInput by remember { mutableStateOf("Honda") }
        var modelInput by remember { mutableStateOf("") }
        var yearInput by remember { mutableStateOf("2022") }
        var engineTypeInput by remember { mutableStateOf("4-Tak 110cc EFI") }
        var mileageInput by remember { mutableStateOf("15000") }
        var plateInput by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddVehicleDialog = false },
            title = { Text("Tambah Unit Kendaraan Garasi", color = TextWhite) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Nama Motor (misal: Beat Street)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = brandInput,
                        onValueChange = { brandInput = it },
                        label = { Text("Merek (Honda, Yamaha, Suzuki)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = modelInput,
                        onValueChange = { modelInput = it },
                        label = { Text("Model / Varian") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = plateInput,
                        onValueChange = { plateInput = it },
                        label = { Text("Nomor Pelat Polisi") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nameInput.isNotBlank()) {
                            viewModel.saveVehicle(
                                Vehicle(
                                    name = nameInput,
                                    brand = brandInput,
                                    model = modelInput.ifBlank { nameInput },
                                    year = yearInput.toIntOrNull() ?: 2022,
                                    engineType = engineTypeInput,
                                    mileage = mileageInput.toIntOrNull() ?: 0,
                                    licensePlate = plateInput
                                )
                            )
                            showAddVehicleDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
                ) {
                    Text("SIMPAN")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddVehicleDialog = false }) {
                    Text("BATAL")
                }
            },
            containerColor = GraphiteSurface
        )
    }
}
