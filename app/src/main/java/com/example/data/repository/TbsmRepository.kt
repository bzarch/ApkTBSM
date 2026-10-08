package com.example.data.repository

import com.example.data.dao.TbsmDao
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

class TbsmRepository(private val dao: TbsmDao) {
    // User Profile
    val userProfile: Flow<UserProfile?> = dao.getUserProfile()
    suspend fun getUserProfileOnce() = dao.getUserProfileOnce()
    suspend fun saveUserProfile(profile: UserProfile) = dao.saveUserProfile(profile)

    // Vehicles
    val allVehicles: Flow<List<Vehicle>> = dao.getAllVehicles()
    suspend fun getVehicleById(id: Long) = dao.getVehicleById(id)
    suspend fun insertVehicle(vehicle: Vehicle) = dao.insertVehicle(vehicle)
    suspend fun updateVehicle(vehicle: Vehicle) = dao.updateVehicle(vehicle)
    suspend fun deleteVehicle(vehicle: Vehicle) = dao.deleteVehicle(vehicle)

    // Customers
    val allCustomers: Flow<List<Customer>> = dao.getAllCustomers()
    suspend fun insertCustomer(customer: Customer) = dao.insertCustomer(customer)
    suspend fun updateCustomer(customer: Customer) = dao.updateCustomer(customer)
    suspend fun deleteCustomer(customer: Customer) = dao.deleteCustomer(customer)

    // Service Records
    val allServiceRecords: Flow<List<ServiceRecord>> = dao.getAllServiceRecords()
    fun getServiceRecordsForVehicle(vehicleId: Long) = dao.getServiceRecordsForVehicle(vehicleId)
    suspend fun insertServiceRecord(record: ServiceRecord) = dao.insertServiceRecord(record)
    suspend fun updateServiceRecord(record: ServiceRecord) = dao.updateServiceRecord(record)
    suspend fun deleteServiceRecord(record: ServiceRecord) = dao.deleteServiceRecord(record)

    // Work Orders
    val allWorkOrders: Flow<List<WorkOrder>> = dao.getAllWorkOrders()
    suspend fun insertWorkOrder(order: WorkOrder) = dao.insertWorkOrder(order)
    suspend fun updateWorkOrder(order: WorkOrder) = dao.updateWorkOrder(order)
    suspend fun deleteWorkOrder(order: WorkOrder) = dao.deleteWorkOrder(order)

    // Inventory Parts
    val allInventoryParts: Flow<List<InventoryPart>> = dao.getAllInventoryParts()
    val lowStockParts: Flow<List<InventoryPart>> = dao.getLowStockParts()
    suspend fun insertInventoryPart(part: InventoryPart) = dao.insertInventoryPart(part)
    suspend fun updateInventoryPart(part: InventoryPart) = dao.updateInventoryPart(part)
    suspend fun deleteInventoryPart(part: InventoryPart) = dao.deleteInventoryPart(part)

    // Calculator History
    val calculatorHistory: Flow<List<CalculatorHistory>> = dao.getCalculatorHistory()
    suspend fun insertCalculatorHistory(history: CalculatorHistory) = dao.insertCalculatorHistory(history)
    suspend fun clearCalculatorHistory() = dao.clearCalculatorHistory()

    // Diagnosis History
    val diagnosisHistory: Flow<List<DiagnosisHistory>> = dao.getDiagnosisHistory()
    suspend fun insertDiagnosisHistory(history: DiagnosisHistory) = dao.insertDiagnosisHistory(history)

    // Notes
    val allNotes: Flow<List<Note>> = dao.getAllNotes()
    suspend fun insertNote(note: Note) = dao.insertNote(note)
    suspend fun updateNote(note: Note) = dao.updateNote(note)
    suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    // Reminders
    val allReminders: Flow<List<ServiceReminder>> = dao.getAllReminders()
    suspend fun insertReminder(reminder: ServiceReminder) = dao.insertReminder(reminder)
    suspend fun updateReminder(reminder: ServiceReminder) = dao.updateReminder(reminder)
    suspend fun deleteReminder(reminder: ServiceReminder) = dao.deleteReminder(reminder)

    // Env Variables
    val allEnvVariables: Flow<List<EnvVariable>> = dao.getAllEnvVariables()
    suspend fun getEnvVariable(name: String) = dao.getEnvVariable(name)
    suspend fun saveEnvVariable(env: EnvVariable) = dao.saveEnvVariable(env)
    suspend fun deleteEnvVariable(name: String) = dao.deleteEnvVariable(name)

    // Achievements
    val allAchievements: Flow<List<Achievement>> = dao.getAllAchievements()
    suspend fun unlockAchievement(id: String) {
        // Find and unlock
    }

    // Quiz Scores
    val allQuizScores: Flow<List<QuizScore>> = dao.getAllQuizScores()
    suspend fun insertQuizScore(score: QuizScore) = dao.insertQuizScore(score)
}
