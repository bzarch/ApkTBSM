package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.AIProvider
import com.example.ai.GoogleGeminiAIProvider
import com.example.ai.OfflineTBSMProvider
import com.example.calculator.EngineCalculatorEngine
import com.example.data.database.AppDatabase
import com.example.data.model.*
import com.example.data.repository.TbsmRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MainUiState(
    val currentTab: String = "HOME", // HOME, TOOLS, DIAGNOSIS, BELAJAR, GARAGE
    val currentSubScreen: String? = null, // e.g. "CALCULATOR_DETAIL", "COMPONENT_DETAIL", "SETTINGS", "AI_CHAT", etc.
    val selectedItemId: String? = null,
    val selectedItemCategory: String? = null,
    val searchQuery: String = "",
    val activeAiProvider: String = "Google Gemini",
    val isAiEnabled: Boolean = true,
    val isLowEndMode: Boolean = false,
    val isTestingConnection: Boolean = false,
    val connectionTestResult: String? = null
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    val repository = TbsmRepository(database.tbsmDao())

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    // Database reactive streams
    val userProfile: StateFlow<UserProfile?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val vehicles: StateFlow<List<Vehicle>> = repository.allVehicles
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customers: StateFlow<List<Customer>> = repository.allCustomers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val serviceRecords: StateFlow<List<ServiceRecord>> = repository.allServiceRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val workOrders: StateFlow<List<WorkOrder>> = repository.allWorkOrders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val inventoryParts: StateFlow<List<InventoryPart>> = repository.allInventoryParts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notes: StateFlow<List<Note>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val reminders: StateFlow<List<ServiceReminder>> = repository.allReminders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val envVariables: StateFlow<List<EnvVariable>> = repository.allEnvVariables
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val achievements: StateFlow<List<Achievement>> = repository.allAchievements
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val calculatorHistory: StateFlow<List<CalculatorHistory>> = repository.calculatorHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // AI Providers
    private var geminiApiKey: String = ""
    private val geminiProvider = GoogleGeminiAIProvider { geminiApiKey }
    private val offlineProvider = OfflineTBSMProvider()

    // AI Chat History State
    private val _chatMessages = MutableStateFlow<List<Pair<String, String>>>(
        listOf(
            Pair("assistant", "Halo rekan teknisi TBSM AFGO! Saya asisten otomotif digital Anda. Silakan tanyakan problem motor, diagnosa kelistrikan, sistem injeksi EFI, perhitungan mesin, atau materi praktikum.")
        )
    )
    val chatMessages: StateFlow<List<Pair<String, String>>> = _chatMessages.asStateFlow()
    val isAiThinking = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            // Load custom env API key if exists
            val keyEnv = repository.getEnvVariable("GEMINI_API_KEY")
            if (keyEnv != null && keyEnv.value.isNotBlank()) {
                geminiApiKey = keyEnv.value
            }
            // Ensure demo data seed is ready
            val profile = repository.getUserProfileOnce()
            if (profile == null) {
                AppDatabase.populateInitialDemoData(database.tbsmDao())
            }
        }
    }

    fun selectTab(tab: String) {
        _uiState.update { it.copy(currentTab = tab, currentSubScreen = null, selectedItemId = null) }
    }

    fun navigateToSubScreen(subScreen: String, itemId: String? = null, category: String? = null) {
        _uiState.update { it.copy(currentSubScreen = subScreen, selectedItemId = itemId, selectedItemCategory = category) }
    }

    fun navigateBack(): Boolean {
        if (_uiState.value.currentSubScreen != null) {
            _uiState.update { it.copy(currentSubScreen = null, selectedItemId = null) }
            return true
        }
        return false
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun toggleLowEndMode() {
        val current = _uiState.value.isLowEndMode
        _uiState.update { it.copy(isLowEndMode = !current) }
        viewModelScope.launch {
            userProfile.value?.let { p ->
                repository.saveUserProfile(p.copy(isLowEndMode = !current))
            }
        }
    }

    fun toggleAiMode() {
        val current = _uiState.value.isAiEnabled
        _uiState.update { it.copy(isAiEnabled = !current) }
        viewModelScope.launch {
            userProfile.value?.let { p ->
                repository.saveUserProfile(p.copy(isAiEnabled = !current))
            }
        }
    }

    fun setAiProvider(provider: String) {
        _uiState.update { it.copy(activeAiProvider = provider) }
    }

    fun testAiConnection() {
        _uiState.update { it.copy(isTestingConnection = true, connectionTestResult = null) }
        viewModelScope.launch {
            val provider: AIProvider = if (_uiState.value.activeAiProvider == "Google Gemini") geminiProvider else offlineProvider
            val res = provider.testConnection()
            _uiState.update { it.copy(isTestingConnection = false, connectionTestResult = res.second) }
        }
    }

    fun sendChatMessage(message: String) {
        if (message.isBlank()) return
        val currentList = _chatMessages.value.toMutableList()
        currentList.add(Pair("user", message))
        _chatMessages.value = currentList
        isAiThinking.value = true

        viewModelScope.launch {
            val provider: AIProvider = if (_uiState.value.isAiEnabled && _uiState.value.activeAiProvider == "Google Gemini") {
                geminiProvider
            } else {
                offlineProvider
            }

            val systemPrompt = """
                Kamu adalah TBSM AFGO AI, asisten digital bidang Teknik dan Bisnis Sepeda Motor SMK AFGO.
                Gunakan Bahasa Indonesia teknis yang tepat dan komunikatif untuk siswa SMK otomotif.
                Sertakan aspek K3 (Keselamatan Kerja), alat yang diperlukan, dan tahapan troubleshooting yang sistematis.
            """.trimIndent()

            val aiResponse = provider.generateResponse(systemPrompt, message)
            val updated = _chatMessages.value.toMutableList()
            updated.add(Pair("assistant", aiResponse))
            _chatMessages.value = updated
            isAiThinking.value = false
        }
    }

    fun clearChat() {
        _chatMessages.value = listOf(
            Pair("assistant", "Riwayat chat telah dibersihkan. Silakan tanyakan hal baru seputar TBSM AFGO.")
        )
    }

    // CRUD Shortcuts
    fun saveVehicle(vehicle: Vehicle) = viewModelScope.launch {
        if (vehicle.id == 0L) repository.insertVehicle(vehicle) else repository.updateVehicle(vehicle)
    }

    fun deleteVehicle(vehicle: Vehicle) = viewModelScope.launch {
        repository.deleteVehicle(vehicle)
    }

    fun saveServiceRecord(record: ServiceRecord) = viewModelScope.launch {
        if (record.id == 0L) repository.insertServiceRecord(record) else repository.updateServiceRecord(record)
    }

    fun saveWorkOrder(workOrder: WorkOrder) = viewModelScope.launch {
        if (workOrder.id == 0L) repository.insertWorkOrder(workOrder) else repository.updateWorkOrder(workOrder)
    }

    fun saveInventoryPart(part: InventoryPart) = viewModelScope.launch {
        if (part.id == 0L) repository.insertInventoryPart(part) else repository.updateInventoryPart(part)
    }

    fun saveNote(note: Note) = viewModelScope.launch {
        if (note.id == 0L) repository.insertNote(note) else repository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun saveReminder(reminder: ServiceReminder) = viewModelScope.launch {
        if (reminder.id == 0L) repository.insertReminder(reminder) else repository.updateReminder(reminder)
    }

    fun saveEnvVariable(name: String, value: String, description: String, isSecret: Boolean) = viewModelScope.launch {
        val env = EnvVariable(name = name, value = value, description = description, isSecret = isSecret)
        repository.saveEnvVariable(env)
        if (name == "GEMINI_API_KEY") {
            geminiApiKey = value
        }
    }

    fun deleteEnvVariable(name: String) = viewModelScope.launch {
        repository.deleteEnvVariable(name)
    }

    fun recordCalculatorHistory(title: String, type: String, inputs: String, result: String) = viewModelScope.launch {
        repository.insertCalculatorHistory(
            CalculatorHistory(
                calculatorType = type,
                title = title,
                inputsSummary = inputs,
                resultSummary = result
            )
        )
    }
}
