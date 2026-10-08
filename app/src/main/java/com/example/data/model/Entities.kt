package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val name: String = "Siswa TBSM",
    val studentClass: String = "XII TBSM 1",
    val major: String = "Teknik dan Bisnis Sepeda Motor",
    val school: String = "SMK AFGO",
    val isOnboarded: Boolean = false,
    val isLowEndMode: Boolean = false,
    val isAiEnabled: Boolean = false
)

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val brand: String,
    val model: String,
    val year: Int,
    val engineType: String, // e.g. "4-Tak SOHC 110cc EFI", "Matic 125cc eSP"
    val mileage: Int,
    val licensePlate: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val phone: String = "",
    val address: String = "",
    val vehicleId: Long? = null,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "service_records")
data class ServiceRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerName: String,
    val vehicleName: String,
    val vehicleId: Long? = null,
    val date: String,
    val mileage: Int,
    val complaint: String,
    val inspection: String,
    val workPerformed: String,
    val partsReplaced: String,
    val technician: String,
    val notes: String,
    val status: String, // WAITING, INSPECTION, WORKING, COMPLETED
    val costService: Double = 0.0,
    val costParts: Double = 0.0,
    val totalCost: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "work_orders")
data class WorkOrder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerName: String,
    val vehicleName: String,
    val complaint: String,
    val inspectionResult: String,
    val workDetails: String,
    val partsNeeded: String,
    val technician: String,
    val status: String, // WAITING, INSPECTION, WORKING, COMPLETED
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "inventory_parts")
data class InventoryPart(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val partName: String,
    val partCode: String,
    val category: String, // Mesin, Pengapian, CVT, Pengereman, Pelumas, Kelistrikan
    val stock: Int,
    val minStock: Int = 3,
    val buyPrice: Double,
    val sellPrice: Double,
    val location: String = "Rak A-1",
    val notes: String = "",
    val status: String = "IN STOCK" // IN STOCK, LOW STOCK, OUT OF STOCK
)

@Entity(tableName = "calculator_history")
data class CalculatorHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val calculatorType: String,
    val title: String,
    val inputsSummary: String,
    val resultSummary: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "diagnosis_history")
data class DiagnosisHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val symptom: String,
    val vehicleName: String = "",
    val pathTaken: String,
    val finalResult: String,
    val possibleCauses: String,
    val recommendedActions: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val category: String, // Quick Note, Workshop Note, Learning Note, Diagnosis Note
    val isPinned: Boolean = false,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "reminders")
data class ServiceReminder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String, // Ganti Oli Mesin, Cek Rem, Ganti Busi, dll
    val vehicleName: String,
    val targetDate: String = "",
    val targetMileage: Int = 0,
    val currentMileage: Int = 0,
    val intervalType: String = "Date", // Date / Mileage
    val isCompleted: Boolean = false,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "env_variables")
data class EnvVariable(
    @PrimaryKey val name: String,
    val value: String,
    val description: String = "",
    val isSecret: Boolean = true,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long = 0
)

@Entity(tableName = "quiz_scores")
data class QuizScore(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quizCategory: String,
    val level: String,
    val score: Int,
    val totalQuestions: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val timestamp: Long = System.currentTimeMillis()
)
