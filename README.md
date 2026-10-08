# 🏍️ TBSM AFGO — Digital Automotive Assistant
> **Aplikasi Android Profesional untuk Teknik dan Bisnis Sepeda Motor (SMK AFGO)**

[![Android Build](https://img.shields.io/badge/Platform-Android%2024%2B-brightgreen?logo=android)](https://www.android.com/)
[![Language](https://img.shields.io/badge/Kotlin-2.2.10-blue?logo=kotlin)](https://kotlinlang.org/)
[![UI](https://img.shields.io/badge/Jetpack%20Compose-Material%203-purple?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM%20%2B%20Offline--First-orange)]()
[![License](https://img.shields.io/badge/License-MIT-red)]()

---

## 👨‍💻 Developer & Creator
* **Lead Architect & Developer**: **BzOne (M. Akbar Rosyid)**
* **Institusi / Almamater**: **SMK AFGO**
* **Bidang Keahlian**: *Teknik dan Bisnis Sepeda Motor (TBSM) & Mobile Software Engineering*

---

## 📥 Download File APK Siap Pakai

File aplikasi `.apk` yang sudah dikompilasi dan siap langsung diinstall di HP Android:

* 📱 **[Download APK Langsung dari Repository (release-apk/TBSM-AFGO-v1.0.apk)](./release-apk/TBSM-AFGO-v1.0.apk)**
* 📦 **[Download APK dari GitHub Actions Artifacts / Releases](../../actions)**
* 📁 Lokasi file build lokal: `app/build/outputs/apk/debug/app-debug.apk`

---

## 🌟 Fitur Unggulan (All-In-One Automotive Suite)

### 🧮 1. Automotive Calculator Center
Logika perhitungan matematis nyata dan tervalidasi standar teknik otomotif:
* **Engine Displacement (Kapasitas CC)**: `CC = 0.7854 × Bore² × Stroke × Silinder / 1000`.
* **Compression Ratio (CR) & Clearance Volume**: Menghitung rasio kompresi statis ruang bakar silinder.
* **Mean Piston Speed (m/s)**: Kecepatan rata-rata piston untuk keamanan batas rpm mesin harian (<21 m/s) vs balap.
* **Final Sprocket Gear Ratio**: Menghitung rasio gir depan-belakang dan menganalisis karakter akselerasi vs top speed.
* **Hukum Ohm & Electrical Helper**: Kalkulasi Volt, Ampere, Watt beban, dan estimasi waktu tahan aki motor 12V.
* **Technical Unit Converter**: Konversi tekanan (*psi, bar, kPa*), torsi (*Nm, kgf·m, lb-ft*), dan daya (*HP, kW*).

### 🔍 2. Diagnostic Assistant (Troubleshooting Engine)
* **Decision Tree Troubleshooting**: Alur pelacakan kerusakan bertahap berbasis pertanyaan gejala (motor mogok, hilang api busi, fuel pump drop, kompresi bocor, starter mati).
* Memberikan kesimpulan status, alat bengkel yang diperlukan, langkah pemeriksaan SOP, dan peringatan K3.

### 📚 3. Digital Learning Center & K3 Bengkel
* **Buku Digital TBSM**: Teori mendalam siklus mesin 4-tak, cara kerja sistem EFI (Closed-loop/Open-loop), transmisi otomatis CVT, dan sistem pengisian regulator rectifier.
* **Database Komponen**: Piston, Noken As, Busi, Kiprok, Sensor TPS, Injektor, V-Belt, Spul Generator, Radiator, Kaliper Rem.
* **Modul Praktikum SOP**: Panduan langkah demi langkah pemeriksaan busi, pengujian aki motor, dan servis CVT.
* **K3 & Keselamatan Kerja**: Pedoman lengkap APD wearpack, pencegahan kebakaran & penanganan APAR, serta bahaya gas CO & limbah oli B3.
* **Kuis Berskor & Flashcard Interaktif**: Bank soal kejuruan TBSM dengan evaluasi skor otomatis.

### 🏢 4. Workshop & Garage Manager
* **Garasi Digital**: Simpan data banyak armada motor (merek, model, tahun, odometer, nomor pelat).
* **Service Records & Work Orders**: Pencatatan pekerjaan mekanik, keluhan pelanggan, dan estimasi biaya perbaikan.
* **Spareparts Inventory**: Manajemen stok suku cadang dengan peringatan status *Low Stock*.
* **Kas & Omset Bengkel**: Ringkasan omset dan estimasi laba bersih bengkel latihan.

### 🤖 5. AI Automotive Assistant & Keamanan ENV
* **Dual AI Engine**: Dapat dihubungkan ke **Google Gemini 3.5 Flash** untuk konsultasi cerdas dan tetap memiliki **Offline Knowledge Base** saat tanpa koneksi internet.
* **ENV Manager**: Manajemen variabel lingkungan terenkripsi dan terlindungi (*masked API key*) langsung dari dalam menu Settings tanpa risiko kebocoran credential.

---

## 🎨 Desain Visual & UI/UX

* **Tema Utama**: *Industrial Garage + Premium Glass UI*.
* **Palet Warna**: **Maroon Red (Identitas TBSM AFGO)**, Graphite Black, Metallic Silver, dan Dark Gray.
* **Adaptif & Ringan**: Dilengkapi fitur **Low-End Mode** di Settings untuk memastikan performa tetap lancar di ponsel spesifikasi minimum.

---

## 🛠️ Panduan Build Lokal

```bash
# Clone repository
git clone https://github.com/<username>/tbsm-afgo.git
cd tbsm-afgo

# Siapkan file environment
cp .env.example .env

# Jalankan Unit Test
gradle :app:testDebugUnitTest

# Compile & Build Debug APK
gradle assembleDebug

# Lokasi output APK:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## 📄 Lisensi & Hak Cipta

Dibuat dengan ❤️ oleh **BzOne (M. Akbar Rosyid)** untuk kemajuan siswa dan instruktur **Teknik dan Bisnis Sepeda Motor SMK AFGO**.
Semua hak cipta dilindungi undang-undang.
