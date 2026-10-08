package com.example.ai

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

interface AIProvider {
    val name: String
    suspend fun testConnection(): Pair<Boolean, String>
    suspend fun generateResponse(systemPrompt: String, userMessage: String, contextData: String = ""): String
}

class GoogleGeminiAIProvider(
    private val apiKeyProvider: () -> String
) : AIProvider {
    override val name: String = "Google Gemini"

    private val client = OkHttpClient.Builder()
        .connectTimeout(45, TimeUnit.SECONDS)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(45, TimeUnit.SECONDS)
        .build()

    override suspend fun testConnection(): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        val apiKey = apiKeyProvider().ifBlank { BuildConfig.GEMINI_API_KEY }
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext Pair(false, "API Key belum diisi di Settings atau Secrets panel.")
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
            val jsonPayload = JSONObject().apply {
                val contents = JSONArray().apply {
                    put(JSONObject().apply {
                        val parts = JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", "Tes koneksi sistem TBSM AFGO. Jawab 'Koneksi Sukses' saja.")
                            })
                        }
                        put("parts", parts)
                    })
                }
                put("contents", contents)
            }

            val body = jsonPayload.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder().url(url).post(body).build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                Pair(true, "Koneksi Google Gemini API Berhasil!")
            } else {
                val code = response.code
                Pair(false, "Gagal koneksi (HTTP $code). Periksa validitas API Key.")
            }
        } catch (e: Exception) {
            Pair(false, "Koneksi gagal: ${e.message ?: "Network timeout"}")
        }
    }

    override suspend fun generateResponse(
        systemPrompt: String,
        userMessage: String,
        contextData: String
    ): String = withContext(Dispatchers.IO) {
        val apiKey = apiKeyProvider().ifBlank { BuildConfig.GEMINI_API_KEY }
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext "Kunci API Gemini belum dikonfigurasi. Silakan tambahkan API Key di menu Settings -> AI Configuration atau gunakan Offline Fallback."
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

            val fullPrompt = buildString {
                if (systemPrompt.isNotBlank()) {
                    append("System Instructions:\n")
                    append(systemPrompt)
                    append("\n\n")
                }
                if (contextData.isNotBlank()) {
                    append("Context Data:\n")
                    append(contextData)
                    append("\n\n")
                }
                append("User Query:\n")
                append(userMessage)
            }

            val jsonPayload = JSONObject().apply {
                val contents = JSONArray().apply {
                    put(JSONObject().apply {
                        val parts = JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", fullPrompt)
                            })
                        }
                        put("parts", parts)
                    })
                }
                put("contents", contents)
            }

            val body = jsonPayload.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder().url(url).post(body).build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val resString = response.body?.string() ?: ""
                val jsonObj = JSONObject(resString)
                val candidates = jsonObj.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val cand = candidates.getJSONObject(0)
                    val content = cand.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).optString("text", "Tidak ada respon teks.")
                    }
                }
                "Respon kosong dari AI."
            } else {
                "Gagal memproses permintaan (HTTP ${response.code}). Silakan coba beberapa saat lagi."
            }
        } catch (e: Exception) {
            "Kesalahan jaringan saat menghubungi AI: ${e.message}"
        }
    }
}

class OfflineTBSMProvider : AIProvider {
    override val name: String = "Offline Knowledge Base"

    override suspend fun testConnection(): Pair<Boolean, String> = Pair(true, "Offline Knowledge Base siap digunakan 100% tanpa internet.")

    override suspend fun generateResponse(
        systemPrompt: String,
        userMessage: String,
        contextData: String
    ): String = withContext(Dispatchers.Default) {
        val query = userMessage.lowercase()
        when {
            query.contains("brebet") || query.contains("tersendat") -> {
                """
                    [ANALISIS OFFLINE TBSM AFGO]
                    Gejala: Mesin Brebet / Tersendat
                    
                    Kemungkinan Penyebab:
                    1. Busi kotor atau celah elektroda melebihi 0.90 mm
                    2. Tekanan pompa bensin (fuel pump) drop di bawah 294 kPa
                    3. Filter udara sangat kotor sehingga pasokan oksigen defisit
                    4. Injektor tersumbat deposit karbon
                    5. TPS (Throttle Position Sensor) aus di titik bukaan tertentu
                    
                    Pemeriksaan Prioritas:
                    1. Cek warna busi dan ukur celah dengan feeler gauge
                    2. Cek fuel pressure gauge di selang injeksi
                    3. Bersihkan throttle body dan injektor menggunakan cleaner
                    
                    Safety: Pastikan mesin dingin saat melepas busi dan jangan merokok di area bensin.
                """.trimIndent()
            }
            query.contains("tidak hidup") || query.contains("mati") || query.contains("mogok") -> {
                """
                    [ANALISIS OFFLINE TBSM AFGO]
                    Gejala: Motor Tidak Bisa Hidup / Mogok
                    
                    3 Syarat Utama Mesin Hidup:
                    1. Kompresi padat (di atas 10 bar / 140 psi)
                    2. Pengapian kuat dan tepat waktu (api busi biru terang)
                    3. Campuran bahan bakar & udara ideal (tekanan fuel pump normal & injektor menyemprot)
                    
                    Langkah Diagnosa Awal:
                    - Periksa apakah aki memiliki tegangan minimal 12.4V saat kontak ON.
                    - Buka busi dan tempelkan ulir ke bodi sambil starter untuk cek percikan api.
                    - Dengarkan bunyi dengung fuel pump selama 2-3 detik saat kunci kontak diputar ke ON.
                """.trimIndent()
            }
            query.contains("oli") -> {
                """
                    [ANALISIS OFFLINE TBSM AFGO]
                    Topik: Sistem Pelumasan & Perawatan Oli
                    
                    Rekomendasi:
                    - Standar oli matic: JASO MB (viskositas SAE 10W-30)
                    - Standar oli bebek/sport (kopling basah): JASO MA/MA2
                    - Kapasitas umum penggantian berkala: 0.8 Liter (matic kecil) / 1.0 Liter (NMAX/PCX)
                    - Interval ganti berkala: 2.000 - 3.000 km atau maksimal 2 bulan pemakaian.
                """.trimIndent()
            }
            query.contains("kalkulator") || query.contains("cc") -> {
                """
                    [ANALISIS OFFLINE TBSM AFGO]
                    Penjelasan Kalkulasi CC Mesin:
                    Rumus: Volume (cc) = 0.7854 × Bore² × Stroke / 1000
                    
                    Bore adalah diameter dalam silinder liner.
                    Stroke adalah panjang langkah piston dari Titik Mati Bawah (TMB) ke Titik Mati Atas (TMA).
                    Penaikan diameter bore (Bore-up) akan meningkatkan kapasitas CC secara kuadratik, sehingga menghasilkan kenaikan torsi yang signifikan.
                """.trimIndent()
            }
            else -> {
                """
                    [ASISTEN TEKNIS TBSM AFGO - MODE OFFLINE]
                    
                    Pertanyaan: "$userMessage"
                    
                    Untuk informasi lengkap seputar topik ini:
                    1. Buka menu 'Belajar' untuk modul teori dan cara kerja komponen.
                    2. Buka menu 'Diagnosis' untuk pohon keputusan troubleshooting interaktif.
                    3. Gunakan 'Kalkulator' untuk perhitungan teknis rasio, piston speed, dan CC.
                    
                    Catatan: Aktifkan koneksi internet dan masukkan Google Gemini API Key di menu Settings jika ingin berkonsultasi langsung dengan AI generasi penuh.
                """.trimIndent()
            }
        }
    }
}
