package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.TbsmDao
import com.example.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserProfile::class,
        Vehicle::class,
        Customer::class,
        ServiceRecord::class,
        WorkOrder::class,
        InventoryPart::class,
        CalculatorHistory::class,
        DiagnosisHistory::class,
        Note::class,
        ServiceReminder::class,
        EnvVariable::class,
        Achievement::class,
        QuizScore::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tbsmDao(): TbsmDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tbsm_afgo_database"
                )
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateInitialDemoData(database.tbsmDao())
                    }
                }
            }
        }

        suspend fun populateInitialDemoData(dao: TbsmDao) {
            // Profile
            dao.saveUserProfile(
                UserProfile(
                    id = 1,
                    name = "Budi Pratama",
                    studentClass = "XII TBSM 1",
                    major = "Teknik dan Bisnis Sepeda Motor",
                    school = "SMK AFGO",
                    isOnboarded = true,
                    isLowEndMode = false,
                    isAiEnabled = true
                )
            )

            // Demo Vehicles
            val v1 = dao.insertVehicle(
                Vehicle(
                    name = "Honda Beat FI eSP",
                    brand = "Honda",
                    model = "Beat Street 110",
                    year = 2021,
                    engineType = "4-Tak SOHC eSP 109.5cc",
                    mileage = 24500,
                    licensePlate = "B 3821 KTF",
                    notes = "Motor praktik bengkel lab sekolah SMK AFGO"
                )
            )
            val v2 = dao.insertVehicle(
                Vehicle(
                    name = "Yamaha NMAX 155",
                    brand = "Yamaha",
                    model = "NMAX Connected",
                    year = 2022,
                    engineType = "4-Tak SOHC 4-Valve VVA 155cc Liquid Cooled",
                    mileage = 18200,
                    licensePlate = "B 4910 SGF",
                    notes = "Kendaraan milik instruktur bengkel"
                )
            )
            dao.insertVehicle(
                Vehicle(
                    name = "Suzuki Satria F150 FI",
                    brand = "Suzuki",
                    model = "Satria F150",
                    year = 2020,
                    engineType = "4-Tak DOHC 4-Valve 147.3cc Liquid Cooled",
                    mileage = 31000,
                    licensePlate = "D 6721 UMN",
                    notes = "Unit latihan tune-up DOHC"
                )
            )

            // Demo Customers
            val c1 = dao.insertCustomer(
                Customer(
                    name = "Pak Rahmat Hidayat",
                    phone = "081288992211",
                    address = "Jl. Merdeka No. 12, Lingkungan Sekolah",
                    vehicleId = v1,
                    notes = "Pelanggan setia servis rutin berkala"
                )
            )
            dao.insertCustomer(
                Customer(
                    name = "Ibu Siti Aminah",
                    phone = "085712345678",
                    address = "Komplek Guru SMK AFGO Blok B",
                    vehicleId = v2,
                    notes = "Minta ganti oli rutin tiap 2000 km"
                )
            )

            // Demo Service Records
            dao.insertServiceRecord(
                ServiceRecord(
                    customerName = "Pak Rahmat Hidayat",
                    vehicleName = "Honda Beat FI eSP",
                    vehicleId = v1,
                    date = "2026-10-01",
                    mileage = 24000,
                    complaint = "Tarikan awal berat dan getar pada CVT",
                    inspection = "Roller CVT aus gepeng 2 biji, v-belt ada retak mikro, kampas ganda kotor berdebu",
                    workPerformed = "Servis CVT berkala, pembersihan rumah roller, penggantian v-belt dan roller standar",
                    partsReplaced = "V-Belt K44, Roller Set 15g (6 pcs)",
                    technician = "Budi Pratama (Siswa TBSM)",
                    notes = "Akselerasi kembali halus, torsi bawah responsif",
                    status = "COMPLETED",
                    costService = 45000.0,
                    costParts = 175000.0,
                    totalCost = 220000.0
                )
            )
            dao.insertServiceRecord(
                ServiceRecord(
                    customerName = "Ibu Siti Aminah",
                    vehicleName = "Yamaha NMAX 155",
                    vehicleId = v2,
                    date = "2026-10-05",
                    mileage = 18200,
                    complaint = "Servis berkala ganti oli dan cek busi",
                    inspection = "Oli mesin hitam encer, elektroda busi normal agak kerak tipis, kampas rem depan 60%",
                    workPerformed = "Ganti oli mesin sintetis 10W-40, pembersihan saringan udara, cek celah busi 0.8mm",
                    partsReplaced = "Yamalube Super Matic 1L, Busi CPR8EA-9",
                    technician = "Ahmad Dani (Teknisi Lab)",
                    notes = "Suara mesin halus, siap pakai",
                    status = "COMPLETED",
                    costService = 35000.0,
                    costParts = 95000.0,
                    totalCost = 130000.0
                )
            )

            // Demo Work Orders
            dao.insertWorkOrder(
                WorkOrder(
                    customerName = "Pak Hendra",
                    vehicleName = "Honda Vario 125 eSP",
                    complaint = "Lampu MIL berkedip 7 kali saat kunci kontak ON",
                    inspectionResult = "DTC 7: Sensor EOT/ECT sinyal tidak wajar / terputus",
                    workDetails = "Pengecekan konektor sensor suhu dan pengukuran kontinuitas kabel",
                    partsNeeded = "Sensor EOT baru jika soket normal",
                    technician = "Budi Pratama",
                    status = "INSPECTION",
                    notes = "Kendaraan sedang di paddock 2 bengkel AFGO"
                )
            )

            // Demo Inventory Parts
            val parts = listOf(
                InventoryPart(partName = "Busi NGK CPR9EA-9", partCode = "BSI-NGK-01", category = "Pengapian", stock = 24, minStock = 5, buyPrice = 18000.0, sellPrice = 25000.0, location = "Rak A-1", notes = "Untuk Vario, Beat, Scoopy eSP"),
                InventoryPart(partName = "Busi NGK CR6HSA", partCode = "BSI-NGK-02", category = "Pengapian", stock = 18, minStock = 5, buyPrice = 15000.0, sellPrice = 22000.0, location = "Rak A-1", notes = "Untuk bebek Supra X 125, Jupiter Z"),
                InventoryPart(partName = "Oli MPX2 Matic 0.8L", partCode = "OIL-AHM-01", category = "Pelumas", stock = 30, minStock = 6, buyPrice = 42000.0, sellPrice = 52000.0, location = "Rak D-1", notes = "Oli standar matic Honda"),
                InventoryPart(partName = "Oli Yamalube Matic 0.8L", partCode = "OIL-YMH-01", category = "Pelumas", stock = 20, minStock = 6, buyPrice = 43000.0, sellPrice = 53000.0, location = "Rak D-2", notes = "Oli matic Yamaha"),
                InventoryPart(partName = "V-Belt Honda Beat eSP K44", partCode = "BLT-HND-44", category = "CVT", stock = 2, minStock = 4, buyPrice = 90000.0, sellPrice = 120000.0, location = "Rak B-2", status = "LOW STOCK", notes = "Stok menipis, segera reorder"),
                InventoryPart(partName = "Roller CVT Standar Beat (15g)", partCode = "RLR-HND-15", category = "CVT", stock = 8, minStock = 3, buyPrice = 38000.0, sellPrice = 55000.0, location = "Rak B-3", notes = "1 set isi 6 butir"),
                InventoryPart(partName = "Kampas Rem Depan Cakram Honda", partCode = "PAD-HND-01", category = "Pengereman", stock = 15, minStock = 5, buyPrice = 35000.0, sellPrice = 50000.0, location = "Rak C-1", notes = "Bisa untuk Beat, Vario, Blade"),
                InventoryPart(partName = "Kampas Rem Belakang Tromol Honda", partCode = "SHU-HND-01", category = "Pengereman", stock = 12, minStock = 4, buyPrice = 32000.0, sellPrice = 45000.0, location = "Rak C-2", notes = "Tromol matic dan bebek"),
                InventoryPart(partName = "Filter Udara K44 Beat FI", partCode = "FLT-HND-44", category = "Mesin", stock = 14, minStock = 4, buyPrice = 38000.0, sellPrice = 50000.0, location = "Rak E-1", notes = "Filter kertas lapis minyak / viscous"),
                InventoryPart(partName = "Aki Kering GS Astra GTZ5S 12V 3.5Ah", partCode = "BAT-GS-5S", category = "Kelistrikan", stock = 6, minStock = 2, buyPrice = 210000.0, sellPrice = 260000.0, location = "Rak K-1", notes = "Standar motor matic & bebek")
            )
            parts.forEach { dao.insertInventoryPart(it) }

            // Demo Notes
            dao.insertNote(
                Note(
                    title = "Standar Celah Busi Motor Bebek & Matic",
                    content = "Celah busi standar pabrikan Honda & Yamaha umumnya: 0.80 - 0.90 mm. Jangan gunakan obeng ketok untuk menyetel; gunakan feeler gauge dan ketuk lembut elektroda massa.",
                    category = "Learning Note",
                    isPinned = true,
                    isFavorite = true
                )
            )
            dao.insertNote(
                Note(
                    title = "K3 Praktikum Las & Bongkar Mesin Lab AFGO",
                    content = "1. Wajib sepatu safety tertutup dan baju wearpack rapi.\n2. Siapkan APAR Powder di dekat paddock sebelum flushing bahan bakar.\n3. Jangan meniup debu rem tromol dengan kompresor (gunakan brake cleaner agar tidak terhirup asbes).",
                    category = "Workshop Note",
                    isPinned = true,
                    isFavorite = false
                )
            )

            // Demo Reminders
            dao.insertReminder(
                ServiceReminder(
                    title = "Ganti Oli Mesin Berkala (Honda Beat)",
                    vehicleName = "Honda Beat FI eSP",
                    targetDate = "2026-11-01",
                    targetMileage = 26500,
                    currentMileage = 24500,
                    intervalType = "Mileage",
                    isCompleted = false,
                    notes = "Gunakan oli SAE 10W-30 JASO MB"
                )
            )
            dao.insertReminder(
                ServiceReminder(
                    title = "Pemeriksaan & Kuras Minyak Rem DOT 4",
                    vehicleName = "Yamaha NMAX 155",
                    targetDate = "2026-10-25",
                    targetMileage = 20000,
                    currentMileage = 18200,
                    intervalType = "Date",
                    isCompleted = false,
                    notes = "Cek kekeruhan minyak rem master depan dan belakang"
                )
            )

            // Demo Achievements
            val achievements = listOf(
                Achievement("first_calc", "Master Kalkulasi", "Gunakan kalkulator otomotif pertama kali", "calculate", true, System.currentTimeMillis()),
                Achievement("first_quiz", "Pengetahuan Pertama", "Selesaikan kuis TBSM pertama kali", "quiz", true, System.currentTimeMillis()),
                Achievement("engine_basic", "Pakar Mesin 4-Tak", "Pelajari siklus dan komponen ruang bakar", "engine", true, System.currentTimeMillis()),
                Achievement("electrical_basic", "Spesialis Kelistrikan", "Kuasai hukum Ohm dan pemeriksaan sistem starter", "flash", false),
                Achievement("efi_basic", "Ahli Diagnostik EFI", "Pahami sensor TPS, MAP, CKP, dan sistem injeksi", "settings", false),
                Achievement("workshop_ready", "Mekanik Bengkel Mandiri", "Selesaikan 1 Work Order bengkel secara tuntas", "build", true, System.currentTimeMillis()),
                Achievement("tbsm_learner", "Siswa Teladan AFGO", "Membaca 10 materi pembelajaran TBSM", "school", false),
                Achievement("diagnostic_beginner", "Detektif Kerusakan", "Menyelesaikan alur diagnosis problem solving", "troubleshoot", false)
            )
            dao.insertAchievements(achievements)

            // Demo Env Variables
            dao.saveEnvVariable(
                EnvVariable(
                    name = "AI_PROVIDER",
                    value = "Google Gemini",
                    description = "Provider AI untuk asisten teknis TBSM",
                    isSecret = false
                )
            )
            dao.saveEnvVariable(
                EnvVariable(
                    name = "AI_MODEL",
                    value = "gemini-3.5-flash",
                    description = "Model utama reasoning otomotif",
                    isSecret = false
                )
            )
            dao.saveEnvVariable(
                EnvVariable(
                    name = "GEMINI_API_KEY",
                    value = "AIzaSyDEMO_SECURE_KEY_CONFIGURED",
                    description = "Kunci otentikasi Google Cloud AI Studio",
                    isSecret = true
                )
            )
        }
    }
}
