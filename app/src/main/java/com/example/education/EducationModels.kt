package com.example.education

data class ComponentItem(
    val id: String,
    val name: String,
    val englishName: String,
    val category: String, // Mesin, Kelistrikan, EFI, Bahan Bakar, Transmisi, CVT, Rem, Suspensi, Roda
    val functionDesc: String,
    val workingPrinciple: String,
    val location: String,
    val damageSymptoms: List<String>,
    val inspectionMethod: List<String>,
    val maintenanceTips: List<String>,
    val k3Warning: String,
    val relatedComponents: List<String>
)

data class TbsmMaterial(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val fullContent: String,
    val keyTerms: List<Pair<String, String>>, // Istilah -> Penjelasan
    val checkQuestions: List<String>,
    val safetyNote: String
)

data class PracticumModule(
    val id: String,
    val title: String,
    val objectives: List<String>,
    val toolsNeeded: List<String>,
    val materialsNeeded: List<String>,
    val k3Notes: List<String>,
    val preparation: List<String>,
    val procedures: List<String>,
    val standardMeasurements: List<Pair<String, String>>, // Item ukur -> Standar
    val conclusionGuideline: String
)

data class WorkshopTool(
    val id: String,
    val name: String,
    val englishName: String,
    val category: String, // Hand Tools, Special Service Tools (SST), Measuring Tools, Diagnostic
    val functionDesc: String,
    val howToUse: List<String>,
    val howToRead: String,
    val maintenanceTips: List<String>,
    val commonMistakes: List<String>,
    val safetyRule: String
)

data class QuizQuestion(
    val id: String,
    val category: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class FlashcardItem(
    val id: String,
    val category: String,
    val frontQuestion: String,
    val backAnswer: String
)

data class K3Topic(
    val id: String,
    val title: String,
    val category: String, // APD, Fire Safety, Chemical, Workshop Housekeeping
    val summary: String,
    val guidelines: List<String>,
    val emergencySteps: List<String>
)
