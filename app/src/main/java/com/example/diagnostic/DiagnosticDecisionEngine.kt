package com.example.diagnostic

data class DiagnosisStep(
    val id: String,
    val question: String,
    val description: String,
    val options: List<DiagnosisOption>
)

data class DiagnosisOption(
    val label: String,
    val nextStepId: String? = null,
    val result: DiagnosisResult? = null
)

data class DiagnosisResult(
    val title: String,
    val status: String, // PERINGATAN, NORMAL, PERLU PEMERIKSAAN
    val possibleCauses: List<String>,
    val inspectionSteps: List<String>,
    val recommendedTools: List<String>,
    val safetyWarning: String = "Pastikan kunci kontak OFF saat melepas soket dan jauhkan bahan bakar dari percikan api."
)

object DiagnosticDecisionTrees {
    val trees = mapOf(
        "motor_tidak_hidup" to DiagnosisStep(
            id = "start",
            question = "Apakah motor starter berputar saat tombol starter ditekan?",
            description = "Perhatikan suara dinamo starter dan putaran kruk as saat tombol ditekan.",
            options = listOf(
                DiagnosisOption(label = "Ya, Starter Berputar Kuat", nextStepId = "starter_putar_kuat"),
                DiagnosisOption(label = "Starter Lemah / Cetek-Cetek", nextStepId = "starter_lemah"),
                DiagnosisOption(label = "Tidak Ada Respon Sama Sekali", nextStepId = "starter_mati_total")
            )
        ),
        "starter_putar_kuat" to DiagnosisStep(
            id = "starter_putar_kuat",
            question = "Apakah ada percikan api biru pada elektroda busi saat dites ke massa?",
            description = "Lepas busi, tancapkan ke cop busi, tempelkan ulir ke blok silinder, lalu starter.",
            options = listOf(
                DiagnosisOption(
                    label = "Ada Percikan Api Biru Terang",
                    nextStepId = "cek_bensin_kompresi"
                ),
                DiagnosisOption(
                    label = "Tidak Ada Api / Api Merah Lemah",
                    result = DiagnosisResult(
                        title = "Kegagalan Sistem Pengapian",
                        status = "PERLU PEMERIKSAAN",
                        possibleCauses = listOf(
                            "Busi mati / kotor elektroda korslet",
                            "Cop busi (spark plug cap) bocor / resistansi putus",
                            "Ignition coil rusak / kabel koil terkelupas",
                            "Sensor CKP (Pick Up Coil) kotor atau putus",
                            "ECU / CDI tidak mengirim sinyal pengapian",
                            "Kabel massa (ground) koil kendor"
                        ),
                        inspectionSteps = listOf(
                            "Ganti busi baru standar dengan celah 0.8-0.9 mm",
                            "Ukur resistansi cop busi dengan multimeter (standar ~4-6 kOhm)",
                            "Ukur tegangan puncak primer koil saat di-starter",
                            "Cek konektor sensor CKP di dekat bak magnet",
                            "Periksa sekering IGNITION / MAIN FUSE"
                        ),
                        recommendedTools = listOf("Multimeter Digital", "Kunci Busi 16mm", "Feeler Gauge", "Test Lamp")
                    )
                )
            )
        ),
        "cek_bensin_kompresi" to DiagnosisStep(
            id = "cek_bensin_kompresi",
            question = "Apakah busi basah oleh bensin dan tekanan kompresi terasa padat?",
            description = "Cek bau bensin pada ruang bakar dan rasakan tendangan kompresi saat diengkol kick starter.",
            options = listOf(
                DiagnosisOption(
                    label = "Busi Kering & Tidak Ada Bau Bensin",
                    result = DiagnosisResult(
                        title = "Suplai Bahan Bakar Tersumbat (Fuel Delivery Failure)",
                        status = "PERLU PEMERIKSAAN",
                        possibleCauses = listOf(
                            "Fuel pump mati / tekanan bensin drop (< 294 kPa)",
                            "Saringan bensin (fuel suction filter) tersumbat total",
                            "Injektor buntu atau soket injektor lepas",
                            "Tangki bahan bakar kosong atau kran vakum macet (karburator)",
                            "Relay fuel pump putus kontak"
                        ),
                        inspectionSteps = listOf(
                            "Dengarkan suara dengung fuel pump selama 2-3 detik saat kontak ON",
                            "Ukur tekanan fuel pump menggunakan Fuel Pressure Gauge (harus ~3 bar / 43 psi)",
                            "Tes semprotan injektor dengan memberi sinyal jumper tegangan sesaat",
                            "Cek arus dan massa soket injektor dengan test lamp LED"
                        ),
                        recommendedTools = listOf("Fuel Pressure Gauge", "Multimeter", "Obeng Plus/Minus", "Pembersih Injektor")
                    )
                ),
                DiagnosisOption(
                    label = "Kompresi Terasa Ringan / Busi Basah Banjir",
                    result = DiagnosisResult(
                        title = "Hilang Kompresi (Loss Compression) / Banjir Bahan Bakar",
                        status = "PERINGATAN",
                        possibleCauses = listOf(
                            "Klep bocor / terganjal kerak karbon ruang bakar",
                            "Setelan celah klep terlalu rapat (klep menekan terus)",
                            "Ring piston aus, macet atau silinder baret",
                            "Gasket silinder kop bocor",
                            "Injektor bocor mengalir terus sehingga mesin banjir bensin"
                        ),
                        inspectionSteps = listOf(
                            "Lakukan pengukuran kompresi silinder menggunakan Compression Tester (standar ~10-12 bar)",
                            "Lakukan 'wet test' (teteskan 3cc oli mesin lewat lubang busi lalu ukur ulang kompresi)",
                            "Setel celah renggang klep in & ex sesuai buku manual",
                            "Jika kompresi tetap rendah, lakukan pembongkaran silinder head untuk skir klep"
                        ),
                        recommendedTools = listOf("Compression Tester Kit", "Feeler Gauge", "Kunci Setel Klep", "Kunci Momen")
                    )
                )
            )
        ),
        "starter_lemah" to DiagnosisStep(
            id = "starter_lemah",
            question = "Berapa tegangan baterai (aki) saat kondisi diam (kunci kontak OFF)?",
            description = "Ukur pada kutub (+) dan (-) aki menggunakan multimeter skala 20V DC.",
            options = listOf(
                DiagnosisOption(
                    label = "Tegangan < 12.0 Volt",
                    result = DiagnosisResult(
                        title = "Tegangan Baterai Lemah (Undercharged Battery)",
                        status = "PERLU PEMERIKSAAN",
                        possibleCauses = listOf(
                            "Aki tekor (discharge) karena jarang dipakai",
                            "Sistem pengisian (spul / kiprok regulator) rusak",
                            "Arus bocor (parasitic draw) pada kabel instalasi",
                            "Sel aki kering sudah aus / rusak sulfatasi"
                        ),
                        inspectionSteps = listOf(
                            "Lakukan pengisian ulang aki (slow charging) selama beberapa jam",
                            "Ukur tegangan pengisian saat mesin hidup (harus 13.5V - 14.8V di 3000 RPM)",
                            "Ukur kebocoran arus kelistrikan dengan multimeter mode Ampere saat kontak OFF (maks 1mA)",
                            "Lakukan battery load test jika ada load tester"
                        ),
                        recommendedTools = listOf("Multimeter Digital", "Battery Charger", "Battery Tester")
                    )
                ),
                DiagnosisOption(
                    label = "Tegangan Normal (> 12.4 Volt), Dinamo Starter Berat",
                    result = DiagnosisResult(
                        title = "Kerusakan Komponen Motor Starter / Mekanis Mesin",
                        status = "PERLU PEMERIKSAAN",
                        possibleCauses = listOf(
                            "Brush (arang carbon brush starter) sudah aus tipis",
                            "Komutator dinamo starter kotor / gosong",
                            "Bushing dinamo starter aus sehingga armature seret",
                            "One way starter / starter clutch macet"
                        ),
                        inspectionSteps = listOf(
                            "Bongkar dinamo starter dan ukur panjang carbon brush (ganti jika < batas servis)",
                            "Bersihkan komutator dengan amplas halus grit 1000",
                            "Periksa kontinuitas kumparan armature terhadap poros massa (tidak boleh korslet)"
                        ),
                        recommendedTools = listOf("Multimeter", "Kunci Ring 8 & 10", "Jangka Sorong", "Solder Listrik")
                    )
                )
            )
        ),
        "starter_mati_total" to DiagnosisStep(
            id = "starter_mati_total",
            question = "Apakah lampu klakson atau lampu indikator spedometer menyala saat kontak ON?",
            description = "Periksa apakah kelistrikan utama motor aktif saat kunci kontak diputar ke ON.",
            options = listOf(
                DiagnosisOption(
                    label = "Tidak Ada Lampu Menyala Sama Sekali",
                    result = DiagnosisResult(
                        title = "Putus Jalur Utama Kelistrikan (Main Power Outage)",
                        status = "PERINGATAN",
                        possibleCauses = listOf(
                            "Sekering utama (Main Fuse 15A/20A) putus",
                            "Kutub terminal aki kendor atau korosi berat",
                            "Kabel massa bodi utama terputus",
                            "Kunci kontak rusak / kontak internal switch aus"
                        ),
                        inspectionSteps = listOf(
                            "Buka kotak sekering dan cek fisik serta kontinuitas sekering utama",
                            "Kencangkan baut terminal aki dan bersihkan kerak putih dengan air panas",
                            "Ukur tegangan masuk dan keluar pada soket kunci kontak"
                        ),
                        recommendedTools = listOf("Multimeter Digital", "Test Lamp 12V", "Obeng Plus", "Ampelas Kecil")
                    )
                ),
                DiagnosisOption(
                    label = "Spedometer Nyala, Tapi Starter Tidak Merespon",
                    result = DiagnosisResult(
                        title = "Sirkuit Starter Terputus (Starter Safety Circuit Interrupted)",
                        status = "PERLU PEMERIKSAAN",
                        possibleCauses = listOf(
                            "Switch standar samping (Side Stand Switch) kotor / macet",
                            "Switch handel rem kiri/kanan kotor atau putus",
                            "Tombol starter kotor atau korosi",
                            "Relay starter (bendik starter) rusak gulungan solenoidnya"
                        ),
                        inspectionSteps = listOf(
                            "Cek switch standar samping dengan menyemprotkan penetrant cleaner",
                            "Pastikan lampu rem menyala saat handel rem ditarik",
                            "Jumper kontak bendik starter sesaat (jika berputar, berarti relay atau jalur pemicunya rusak)",
                            "Ukur tegangan pemicu dari ECU ke relay starter"
                        ),
                        recommendedTools = listOf("Multimeter Digital", "Kabel Jumper Aman", "Contact Cleaner Spray")
                    )
                )
            )
        )
    )
}
