package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.calculator.ElectricalCalculatorEngine
import com.example.calculator.EngineCalculatorEngine
import com.example.calculator.UnitConverterEngine
import com.example.ui.components.GlassCard
import com.example.ui.components.SafetyWarningBanner
import com.example.ui.theme.*
import com.example.ui.viewmodel.MainViewModel

@Composable
fun ToolsScreen(
    viewModel: MainViewModel,
    onNavigate: (String, String?, String?) -> Unit
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("ENGINE_CC") }

    val categories = listOf(
        Pair("ENGINE_CC", "Displacement (CC)"),
        Pair("COMPRESSION", "Kompresi (CR)"),
        Pair("PISTON_SPEED", "Piston Speed"),
        Pair("SPROCKET", "Rasio Sprocket"),
        Pair("OHM_LAW", "Hukum Ohm"),
        Pair("BATTERY", "Estimasi Aki"),
        Pair("CONVERTER", "Konverter Unit")
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
                text = "Automotive Calculator Center",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Text(
                text = "Kalkulator teknis mesin, rasio gir, kelistrikan, dan konversi standar bengkel TBSM",
                style = MaterialTheme.typography.bodySmall,
                color = MetallicSilverMuted
            )
        }

        // Category Filter Tabs
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { cat ->
                    FilterChip(
                        selected = selectedCategory == cat.first,
                        onClick = { selectedCategory = cat.first },
                        label = { Text(cat.second) },
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

        // Selected Calculator Card
        item {
            when (selectedCategory) {
                "ENGINE_CC" -> EngineCcCalculator(viewModel, context)
                "COMPRESSION" -> CompressionRatioCalculator(viewModel, context)
                "PISTON_SPEED" -> PistonSpeedCalculator(viewModel, context)
                "SPROCKET" -> SprocketRatioCalculator(viewModel, context)
                "OHM_LAW" -> OhmsLawCalculator(viewModel, context)
                "BATTERY" -> BatteryEstimatorCalculator(viewModel, context)
                "CONVERTER" -> UnitConverterScreen(context)
            }
        }

        // AI Explanation Trigger
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = MaroonAccent.copy(alpha = 0.5f),
                onClick = {
                    onNavigate("AI_CHAT", null, null)
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.SmartToy, contentDescription = null, tint = MaroonLight)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Konsultasi Perhitungan dengan AI", fontWeight = FontWeight.Bold, color = TextWhite)
                            Text("Tanyakan bore-up aman, porting-polish, atau timing pengapian", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                        }
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MetallicSilver)
                }
            }
        }
    }
}

@Composable
fun EngineCcCalculator(viewModel: MainViewModel, context: Context) {
    var boreText by remember { mutableStateOf("50.0") }
    var strokeText by remember { mutableStateOf("55.6") }
    var cylText by remember { mutableStateOf("1") }

    var resultCc by remember { mutableStateOf<Double?>(null) }

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Text("Engine Displacement (Kapasitas CC)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = boreText,
            onValueChange = { boreText = it },
            label = { Text("Diameter Bore (mm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().testTag("input_bore")
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = strokeText,
            onValueChange = { strokeText = it },
            label = { Text("Langkah Stroke (mm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().testTag("input_stroke")
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cylText,
            onValueChange = { cylText = it },
            label = { Text("Jumlah Silinder") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    val bore = boreText.toDoubleOrNull() ?: 0.0
                    val stroke = strokeText.toDoubleOrNull() ?: 0.0
                    val cyl = cylText.toIntOrNull() ?: 1
                    if (bore > 0 && stroke > 0) {
                        val res = EngineCalculatorEngine.calculateDisplacement(bore, stroke, cyl)
                        resultCc = res.second
                        viewModel.recordCalculatorHistory(
                            title = "Engine CC",
                            type = "ENGINE",
                            inputs = "Bore: ${bore}mm, Stroke: ${stroke}mm, Cyl: $cyl",
                            result = String.format("%.2f CC", res.second)
                        )
                    }
                },
                modifier = Modifier.weight(1f).testTag("btn_calc_cc"),
                colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
            ) {
                Text("HITUNG")
            }

            OutlinedButton(
                onClick = {
                    boreText = ""
                    strokeText = ""
                    resultCc = null
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("RESET")
            }
        }

        if (resultCc != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaroonDark.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Hasil Perhitungan:", style = MaterialTheme.typography.labelMedium, color = MetallicSilver)
                    Text(
                        String.format("%.2f CC", resultCc),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF8A80)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Rumus: CC = 0.7854 × Bore² × Stroke × Silinder / 1000",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        TextButton(onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("CC Result", String.format("%.2f CC", resultCc)))
                            Toast.makeText(context, "Hasil disalin!", Toast.LENGTH_SHORT).show()
                        }) {
                            Text("Salin Hasil", color = TextWhite)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CompressionRatioCalculator(viewModel: MainViewModel, context: Context) {
    var sweptText by remember { mutableStateOf("109.5") }
    var clearanceText by remember { mutableStateOf("11.5") }
    var resultCr by remember { mutableStateOf<Double?>(null) }

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Text("Perbandingan Kompresi (Compression Ratio)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = sweptText,
            onValueChange = { sweptText = it },
            label = { Text("Volume Silinder / Swept Volume (cc)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = clearanceText,
            onValueChange = { clearanceText = it },
            label = { Text("Volume Ruang Bakar / Clearance Volume (cc)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val swept = sweptText.toDoubleOrNull() ?: 0.0
                val clear = clearanceText.toDoubleOrNull() ?: 0.0
                if (swept > 0 && clear > 0) {
                    val cr = EngineCalculatorEngine.calculateCompressionRatio(swept, clear)
                    resultCr = cr
                    viewModel.recordCalculatorHistory(
                        title = "Compression Ratio",
                        type = "ENGINE",
                        inputs = "Swept: ${swept}cc, Clearance: ${clear}cc",
                        result = String.format("%.2f : 1", cr)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
        ) {
            Text("HITUNG KOMPRESI")
        }

        if (resultCr != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Rasio Kompresi: ${String.format("%.2f : 1", resultCr)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaroonLight)
            Text("Rumus: CR = (Swept Volume + Clearance Volume) / Clearance Volume", style = MaterialTheme.typography.bodySmall, color = TextMuted)
        }
    }
}

@Composable
fun PistonSpeedCalculator(viewModel: MainViewModel, context: Context) {
    var strokeText by remember { mutableStateOf("55.6") }
    var rpmText by remember { mutableStateOf("8500") }
    var resultSpeed by remember { mutableStateOf<Double?>(null) }

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Text("Mean Piston Speed (Kecepatan Rata-rata Piston)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = strokeText,
            onValueChange = { strokeText = it },
            label = { Text("Langkah Stroke (mm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = rpmText,
            onValueChange = { rpmText = it },
            label = { Text("Putaran Mesin (RPM)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val s = strokeText.toDoubleOrNull() ?: 0.0
                val r = rpmText.toDoubleOrNull() ?: 0.0
                if (s > 0 && r > 0) {
                    resultSpeed = EngineCalculatorEngine.calculatePistonSpeed(s, r)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
        ) {
            Text("HITUNG PISTON SPEED")
        }

        if (resultSpeed != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Kecepatan Piston: ${String.format("%.2f m/s", resultSpeed)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaroonLight)
            Text("Batas aman material harian umumnya < 21 m/s. Balap kompetisi: 24 - 26 m/s.", style = MaterialTheme.typography.bodySmall, color = MetallicSilver)
        }
    }
}

@Composable
fun SprocketRatioCalculator(viewModel: MainViewModel, context: Context) {
    var frontTeeth by remember { mutableStateOf("14") }
    var rearTeeth by remember { mutableStateOf("42") }
    var ratioResult by remember { mutableStateOf<Double?>(null) }

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Text("Final Sprocket Gear Ratio", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = frontTeeth,
            onValueChange = { frontTeeth = it },
            label = { Text("Jumlah Mata Gir Depan (Front Sprocket)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = rearTeeth,
            onValueChange = { rearTeeth = it },
            label = { Text("Jumlah Mata Gir Belakang (Rear Sprocket)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val f = frontTeeth.toDoubleOrNull() ?: 0.0
                val r = rearTeeth.toDoubleOrNull() ?: 0.0
                if (f > 0 && r > 0) {
                    ratioResult = EngineCalculatorEngine.calculateSprocketRatio(f, r)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
        ) {
            Text("HITUNG RASIO GIR")
        }

        if (ratioResult != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Rasio Gir Akhir: ${String.format("%.3f", ratioResult)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaroonLight)
            Text(
                if ((ratioResult ?: 0.0) > 3.0) "Karakter: Torsi akselerasi ringan / responsif tanjakan (Rasio Berat)"
                else "Karakter: Top speed panjang / nafas gir panjang (Rasio Ringan)",
                style = MaterialTheme.typography.bodySmall,
                color = TextWhite
            )
        }
    }
}

@Composable
fun OhmsLawCalculator(viewModel: MainViewModel, context: Context) {
    var voltText by remember { mutableStateOf("12.0") }
    var currentText by remember { mutableStateOf("2.5") }
    var resText by remember { mutableStateOf("") }

    var resultResistance by remember { mutableStateOf<Double?>(null) }

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Text("Hukum Ohm (Ohm's Law) - V = I × R", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = voltText,
            onValueChange = { voltText = it },
            label = { Text("Tegangan (Volt)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = currentText,
            onValueChange = { currentText = it },
            label = { Text("Kuat Arus (Ampere)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val v = voltText.toDoubleOrNull()
                val i = currentText.toDoubleOrNull()
                if (v != null && i != null && i > 0) {
                    resultResistance = v / i
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
        ) {
            Text("HITUNG HAMBATAN (OHM)")
        }

        if (resultResistance != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Hambatan (R): ${String.format("%.2f Ohm (Ω)", resultResistance)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaroonLight)
            val power = (voltText.toDoubleOrNull() ?: 0.0) * (currentText.toDoubleOrNull() ?: 0.0)
            Text("Daya Beban (Power): ${String.format("%.2f Watt", power)}", style = MaterialTheme.typography.bodyMedium, color = TextWhite)
        }
    }
}

@Composable
fun BatteryEstimatorCalculator(viewModel: MainViewModel, context: Context) {
    var voltText by remember { mutableStateOf("12.0") }
    var ahText by remember { mutableStateOf("3.5") }
    var loadText by remember { mutableStateOf("25") }
    var runtimeResult by remember { mutableStateOf<Double?>(null) }

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Text("Estimasi Ketahanan Aki Motor (Battery Estimator)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = voltText,
            onValueChange = { voltText = it },
            label = { Text("Tegangan Aki (Volt)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = ahText,
            onValueChange = { ahText = it },
            label = { Text("Kapasitas Aki (Ampere Hour / Ah)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = loadText,
            onValueChange = { loadText = it },
            label = { Text("Beban Kelistrikan (Watt)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val v = voltText.toDoubleOrNull() ?: 12.0
                val ah = ahText.toDoubleOrNull() ?: 0.0
                val w = loadText.toDoubleOrNull() ?: 0.0
                if (ah > 0 && w > 0) {
                    runtimeResult = ElectricalCalculatorEngine.estimateBatteryRuntimeHours(v, ah, w)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary)
        ) {
            Text("ESTIMASI WAKTU TAHAN")
        }

        if (runtimeResult != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Estimasi Ketahanan: ${String.format("%.1f Jam", runtimeResult)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaroonLight)
            Text("Catatan: Kondisi aki nyata dan suhu dingin dapat menurunkan kapasitas efektif hingga 20-30%.", style = MaterialTheme.typography.bodySmall, color = TextMuted)
        }
    }
}

@Composable
fun UnitConverterScreen(context: Context) {
    var inputValue by remember { mutableStateOf("100") }
    var selectedType by remember { mutableStateOf("PRESSURE") } // PRESSURE, TORQUE, POWER

    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Text("Konversi Satuan Teknis Otomotif", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextWhite)
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("PRESSURE" to "Tekanan (psi/bar)", "TORQUE" to "Torsi (Nm/kgf)", "POWER" to "Daya (HP/kW)").forEach {
                FilterChip(
                    selected = selectedType == it.first,
                    onClick = { selectedType = it.first },
                    label = { Text(it.second) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Nilai Input") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
        val v = inputValue.toDoubleOrNull() ?: 0.0

        when (selectedType) {
            "PRESSURE" -> {
                val bar = UnitConverterEngine.convertPressure(v, "psi", "bar")
                val kpa = UnitConverterEngine.convertPressure(v, "psi", "kPa")
                Text("Jika input $v psi:", fontWeight = FontWeight.SemiBold, color = TextWhite)
                Text("• ${String.format("%.3f bar", bar)}", color = MaroonLight)
                Text("• ${String.format("%.1f kPa", kpa)}", color = MetallicSilver)
            }
            "TORQUE" -> {
                val kgfm = UnitConverterEngine.convertTorque(v, "Nm", "kgf·m")
                val lbft = UnitConverterEngine.convertTorque(v, "Nm", "lb-ft")
                Text("Jika input $v Nm:", fontWeight = FontWeight.SemiBold, color = TextWhite)
                Text("• ${String.format("%.3f kgf·m", kgfm)}", color = MaroonLight)
                Text("• ${String.format("%.2f lb-ft", lbft)}", color = MetallicSilver)
            }
            "POWER" -> {
                val kw = UnitConverterEngine.convertPower(v, "HP", "kW")
                val watt = UnitConverterEngine.convertPower(v, "HP", "W")
                Text("Jika input $v HP:", fontWeight = FontWeight.SemiBold, color = TextWhite)
                Text("• ${String.format("%.2f kW", kw)}", color = MaroonLight)
                Text("• ${String.format("%.0f Watt", watt)}", color = MetallicSilver)
            }
        }
    }
}
