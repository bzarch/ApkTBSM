package com.example.data.dao

import androidx.room.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TbsmDao {
    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfileOnce(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfile)

    // Vehicles
    @Query("SELECT * FROM vehicles ORDER BY id DESC")
    fun getAllVehicles(): Flow<List<Vehicle>>

    @Query("SELECT * FROM vehicles WHERE id = :id LIMIT 1")
    suspend fun getVehicleById(id: Long): Vehicle?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicle: Vehicle): Long

    @Update
    suspend fun updateVehicle(vehicle: Vehicle)

    @Delete
    suspend fun deleteVehicle(vehicle: Vehicle)

    // Customers
    @Query("SELECT * FROM customers ORDER BY id DESC")
    fun getAllCustomers(): Flow<List<Customer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer): Long

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    // Service Records
    @Query("SELECT * FROM service_records ORDER BY id DESC")
    fun getAllServiceRecords(): Flow<List<ServiceRecord>>

    @Query("SELECT * FROM service_records WHERE vehicleId = :vehicleId ORDER BY id DESC")
    fun getServiceRecordsForVehicle(vehicleId: Long): Flow<List<ServiceRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServiceRecord(record: ServiceRecord): Long

    @Update
    suspend fun updateServiceRecord(record: ServiceRecord)

    @Delete
    suspend fun deleteServiceRecord(record: ServiceRecord)

    // Work Orders
    @Query("SELECT * FROM work_orders ORDER BY id DESC")
    fun getAllWorkOrders(): Flow<List<WorkOrder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkOrder(order: WorkOrder): Long

    @Update
    suspend fun updateWorkOrder(order: WorkOrder)

    @Delete
    suspend fun deleteWorkOrder(order: WorkOrder)

    // Inventory Parts
    @Query("SELECT * FROM inventory_parts ORDER BY category ASC, partName ASC")
    fun getAllInventoryParts(): Flow<List<InventoryPart>>

    @Query("SELECT * FROM inventory_parts WHERE stock <= minStock")
    fun getLowStockParts(): Flow<List<InventoryPart>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventoryPart(part: InventoryPart): Long

    @Update
    suspend fun updateInventoryPart(part: InventoryPart)

    @Delete
    suspend fun deleteInventoryPart(part: InventoryPart)

    // Calculator History
    @Query("SELECT * FROM calculator_history ORDER BY timestamp DESC LIMIT 50")
    fun getCalculatorHistory(): Flow<List<CalculatorHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalculatorHistory(history: CalculatorHistory)

    @Query("DELETE FROM calculator_history")
    suspend fun clearCalculatorHistory()

    // Diagnosis History
    @Query("SELECT * FROM diagnosis_history ORDER BY timestamp DESC LIMIT 50")
    fun getDiagnosisHistory(): Flow<List<DiagnosisHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiagnosisHistory(history: DiagnosisHistory)

    // Notes
    @Query("SELECT * FROM notes ORDER BY isPinned DESC, updatedAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    // Reminders
    @Query("SELECT * FROM reminders ORDER BY isCompleted ASC, id DESC")
    fun getAllReminders(): Flow<List<ServiceReminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ServiceReminder): Long

    @Update
    suspend fun updateReminder(reminder: ServiceReminder)

    @Delete
    suspend fun deleteReminder(reminder: ServiceReminder)

    // Environment Variables
    @Query("SELECT * FROM env_variables ORDER BY name ASC")
    fun getAllEnvVariables(): Flow<List<EnvVariable>>

    @Query("SELECT * FROM env_variables WHERE name = :name LIMIT 1")
    suspend fun getEnvVariable(name: String): EnvVariable?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEnvVariable(env: EnvVariable)

    @Query("DELETE FROM env_variables WHERE name = :name")
    suspend fun deleteEnvVariable(name: String)

    // Achievements
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<Achievement>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements: List<Achievement>)

    @Update
    suspend fun updateAchievement(achievement: Achievement)

    // Quiz Scores
    @Query("SELECT * FROM quiz_scores ORDER BY timestamp DESC")
    fun getAllQuizScores(): Flow<List<QuizScore>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizScore(score: QuizScore)
}
