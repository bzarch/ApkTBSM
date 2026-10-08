package com.example.calculator

import java.util.Locale
import kotlin.math.PI

object EngineCalculatorEngine {
    // 1. Engine Displacement: CC = (pi/4) * bore^2 * stroke * numCylinders / 1000
    fun calculateDisplacement(boreMm: Double, strokeMm: Double, cylinders: Int = 1): Pair<Double, Double> {
        val volumePerCylCc = (PI / 4.0) * (boreMm * boreMm) * strokeMm / 1000.0
        val totalCc = volumePerCylCc * cylinders
        return Pair(volumePerCylCc, totalCc)
    }

    // 2. Bore Calculator: bore = sqrt( (targetCc * 1000) / ( (pi/4) * stroke * cylinders ) )
    fun calculateRequiredBore(targetCc: Double, strokeMm: Double, cylinders: Int = 1): Double {
        val volPerCyl = targetCc / cylinders
        return kotlin.math.sqrt((volPerCyl * 1000.0) / ((PI / 4.0) * strokeMm))
    }

    // 3. Stroke Calculator: stroke = (targetCc * 1000) / ( (pi/4) * bore^2 * cylinders )
    fun calculateRequiredStroke(targetCc: Double, boreMm: Double, cylinders: Int = 1): Double {
        val volPerCyl = targetCc / cylinders
        return (volPerCyl * 1000.0) / ((PI / 4.0) * (boreMm * boreMm))
    }

    // 4. Compression Ratio: CR = (Swept + Clearance) / Clearance
    fun calculateCompressionRatio(sweptVolumeCc: Double, clearanceVolumeCc: Double): Double {
        if (clearanceVolumeCc <= 0.0) return 0.0
        return (sweptVolumeCc + clearanceVolumeCc) / clearanceVolumeCc
    }

    // 5. Clearance Volume: Clearance = Swept / (CR - 1)
    fun calculateClearanceVolume(sweptVolumeCc: Double, compressionRatio: Double): Double {
        if (compressionRatio <= 1.0) return 0.0
        return sweptVolumeCc / (compressionRatio - 1.0)
    }

    // 6. Mean Piston Speed (m/s) = (2 * stroke (m) * RPM) / 60
    fun calculatePistonSpeed(strokeMm: Double, rpm: Double): Double {
        val strokeMeters = strokeMm / 1000.0
        return (2.0 * strokeMeters * rpm) / 60.0
    }

    // 7. Tire Diameter from format: width (mm) / aspect ratio (%) / rim (inch)
    // Example: 90/90-14 -> width 90mm, sidewall = 90 * 0.90 = 81mm, rim = 14 * 25.4 = 355.6mm
    // Total diameter = (2 * sidewall) + rim diameter
    fun calculateTireDiameter(widthMm: Double, aspectRatioPercent: Double, rimInch: Double): Double {
        val sidewallMm = widthMm * (aspectRatioPercent / 100.0)
        val rimMm = rimInch * 25.4
        return (2.0 * sidewallMm) + rimMm
    }

    // 8. Wheel Circumference (mm) = PI * Diameter
    fun calculateCircumference(diameterMm: Double): Double {
        return PI * diameterMm
    }

    // 9. RPM -> Speed (km/h)
    // Speed = (RPM / (Primary * Gear * Final)) * (Circumference in meters * 60) / 1000
    fun calculateSpeedFromRpm(
        rpm: Double,
        primaryRatio: Double,
        gearRatio: Double,
        finalRatio: Double,
        tireDiameterMm: Double
    ): Double {
        val totalReduction = primaryRatio * gearRatio * finalRatio
        if (totalReduction <= 0.0) return 0.0
        val wheelRpm = rpm / totalReduction
        val circumferenceMeters = (PI * tireDiameterMm) / 1000.0
        val metersPerMinute = wheelRpm * circumferenceMeters
        return (metersPerMinute * 60.0) / 1000.0 // km/h
    }

    // 10. Speed -> RPM
    fun calculateRpmFromSpeed(
        speedKmh: Double,
        primaryRatio: Double,
        gearRatio: Double,
        finalRatio: Double,
        tireDiameterMm: Double
    ): Double {
        val circumferenceMeters = (PI * tireDiameterMm) / 1000.0
        if (circumferenceMeters <= 0.0) return 0.0
        val metersPerMinute = (speedKmh * 1000.0) / 60.0
        val wheelRpm = metersPerMinute / circumferenceMeters
        val totalReduction = primaryRatio * gearRatio * finalRatio
        return wheelRpm * totalReduction
    }

    // 11. Sprocket Ratio = Rear Sprocket / Front Sprocket
    fun calculateSprocketRatio(frontTeeth: Double, rearTeeth: Double): Double {
        if (frontTeeth <= 0.0) return 0.0
        return rearTeeth / frontTeeth
    }

    // 12. Compare Sprocket
    fun compareSprockets(oldFront: Double, oldRear: Double, newFront: Double, newRear: Double): Triple<Double, Double, Double> {
        val oldRatio = calculateSprocketRatio(oldFront, oldRear)
        val newRatio = calculateSprocketRatio(newFront, newRear)
        val relativeChangePercent = if (oldRatio > 0.0) ((newRatio - oldRatio) / oldRatio) * 100.0 else 0.0
        return Triple(oldRatio, newRatio, relativeChangePercent)
    }

    // 13. Power / Torque / RPM
    // Power (HP) = (Torque (Nm) * RPM) / 7127
    // Torque (Nm) = (Power (HP) * 7127) / RPM
    // RPM = (Power (HP) * 7127) / Torque (Nm)
    fun calculatePowerTorqueRpm(powerHp: Double?, torqueNm: Double?, rpm: Double?): Triple<Double, Double, Double> {
        return when {
            powerHp == null && torqueNm != null && rpm != null && rpm > 0 -> {
                val p = (torqueNm * rpm) / 7127.0
                Triple(p, torqueNm, rpm)
            }
            torqueNm == null && powerHp != null && rpm != null && rpm > 0 -> {
                val t = (powerHp * 7127.0) / rpm
                Triple(powerHp, t, rpm)
            }
            rpm == null && powerHp != null && torqueNm != null && torqueNm > 0 -> {
                val r = (powerHp * 7127.0) / torqueNm
                Triple(powerHp, torqueNm, r)
            }
            else -> Triple(powerHp ?: 0.0, torqueNm ?: 0.0, rpm ?: 0.0)
        }
    }
}

object ElectricalCalculatorEngine {
    // 1. Ohm's Law
    fun calculateOhmsLaw(voltageV: Double?, currentA: Double?, resistanceOhm: Double?): Triple<Double, Double, Double> {
        return when {
            voltageV == null && currentA != null && resistanceOhm != null -> {
                Triple(currentA * resistanceOhm, currentA, resistanceOhm)
            }
            currentA == null && voltageV != null && resistanceOhm != null && resistanceOhm > 0 -> {
                Triple(voltageV, voltageV / resistanceOhm, resistanceOhm)
            }
            resistanceOhm == null && voltageV != null && currentA != null && currentA > 0 -> {
                Triple(voltageV, currentA, voltageV / currentA)
            }
            else -> Triple(voltageV ?: 0.0, currentA ?: 0.0, resistanceOhm ?: 0.0)
        }
    }

    // 2. Power: P = V * I, Energy: Wh = V * A * h
    fun calculatePower(voltageV: Double, currentA: Double): Double = voltageV * currentA

    fun calculateEnergyWh(voltageV: Double, currentA: Double, hours: Double): Double = voltageV * currentA * hours

    // 3. Battery Estimator: Runtime (hours) = (Capacity (Ah) * Voltage (V) * 0.85 efficiency) / Load (Watt)
    fun estimateBatteryRuntimeHours(voltageV: Double, capacityAh: Double, loadWatts: Double): Double {
        if (loadWatts <= 0.0) return 0.0
        val totalWattHours = voltageV * capacityAh
        return (totalWattHours * 0.85) / loadWatts
    }

    // 4. Voltage Drop: Vdrop = I * R
    fun calculateVoltageDrop(currentA: Double, resistanceOhm: Double): Double = currentA * resistanceOhm

    // 5. Cable Helper: Approximate copper wire cross-section (mm2)
    // Formula: S = (2 * L * I) / (gamma * Vdrop), copper conductivity gamma ~ 56 m/(ohm*mm2)
    fun recommendCableGaugeMm2(voltageV: Double, currentA: Double, lengthMeters: Double, allowedDropPercent: Double = 3.0): Double {
        val allowedDropV = voltageV * (allowedDropPercent / 100.0)
        if (allowedDropV <= 0.0) return 0.75
        val crossSection = (2.0 * lengthMeters * currentA) / (56.0 * allowedDropV)
        return kotlin.math.max(0.75, crossSection)
    }
}

object UnitConverterEngine {
    fun convertLength(value: Double, from: String, to: String): Double {
        val inMm = when (from) {
            "mm" -> value
            "cm" -> value * 10.0
            "m" -> value * 1000.0
            "km" -> value * 1000000.0
            "inch" -> value * 25.4
            "ft" -> value * 304.8
            else -> value
        }
        return when (to) {
            "mm" -> inMm
            "cm" -> inMm / 10.0
            "m" -> inMm / 1000.0
            "km" -> inMm / 1000000.0
            "inch" -> inMm / 25.4
            "ft" -> inMm / 304.8
            else -> inMm
        }
    }

    fun convertPressure(value: Double, from: String, to: String): Double {
        val inKpa = when (from) {
            "Pa" -> value / 1000.0
            "kPa" -> value
            "bar" -> value * 100.0
            "psi" -> value * 6.89476
            else -> value
        }
        return when (to) {
            "Pa" -> inKpa * 1000.0
            "kPa" -> inKpa
            "bar" -> inKpa / 100.0
            "psi" -> inKpa / 6.89476
            else -> inKpa
        }
    }

    fun convertTorque(value: Double, from: String, to: String): Double {
        val inNm = when (from) {
            "Nm" -> value
            "kgf·m" -> value * 9.80665
            "lb-ft" -> value * 1.35582
            else -> value
        }
        return when (to) {
            "Nm" -> inNm
            "kgf·m" -> inNm / 9.80665
            "lb-ft" -> inNm / 1.35582
            else -> inNm
        }
    }

    fun convertPower(value: Double, from: String, to: String): Double {
        val inHp = when (from) {
            "HP" -> value
            "kW" -> value * 1.34102
            "W" -> value * 0.00134102
            else -> value
        }
        return when (to) {
            "HP" -> inHp
            "kW" -> inHp / 1.34102
            "W" -> inHp / 0.00134102
            else -> inHp
        }
    }

    fun convertTemperature(value: Double, from: String, to: String): Double {
        if (from == to) return value
        return if (from == "°C" && to == "°F") {
            (value * 9.0 / 5.0) + 32.0
        } else if (from == "°F" && to == "°C") {
            (value - 32.0) * 5.0 / 9.0
        } else {
            value
        }
    }
}
