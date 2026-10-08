package com.example.education

object TbsmStaticDatabase {

    val components = listOf(
        ComponentItem(
            id = "piston",
            name = "Piston & Ring Piston",
            englishName = "Piston & Piston Rings",
            category = "Mesin",
            functionDesc = "Menerima tekanan hasil pembakaran campuran bahan bakar dan udara di ruang bakar, lalu meneruskannya ke connecting rod untuk memutar kruk as.",
            workingPrinciple = "Bergerak translasi bolak-balik (naik-turun) dari Titik Mati Bawah (TMB) ke Titik Mati Atas (TMA) dengan toleransi celah dinding silinder yang sangat presisi.",
            location = "Di dalam silinder liner / dinding blok silinder.",
            damageSymptoms = listOf("Knalpot keluar asap putih pedih (oli terbakar)", "Mesin boros oli / volume oli cepat habis", "Kompresi mesin drop dan tarikan loyo", "Suara ketukan logam halus saat gas dibuka"),
            inspectionMethod = listOf("Ukur celah celah ring piston (ring end gap) dengan feeler gauge di dalam silinder", "Ukur diameter piston pada posisi 10mm dari tepi bawah rok piston menggunakan mikrometer luar", "Periksa keovalan dan ketirusan dinding silinder dengan cylinder bore gauge"),
            maintenanceTips = listOf("Ganti oli mesin secara teratur sesuai jadwal", "Gunakan saringan udara bersih agar debu abrasif tidak mengikis piston", "Hindari menggeber mesin saat baru dinyalakan"),
            k3Warning = "Tunggu mesin dingin sebelum melepas blok silinder. Gunakan kain majun saat melepas pen piston agar kancing sirklipp tidak terpental ke mata.",
            relatedComponents = listOf("Silinder Blok", "Connecting Rod", "Pin Piston", "Ring Kompresi", "Ring Oli")
        ),
        ComponentItem(
            id = "camshaft",
            name = "Noken As (Camshaft)",
            englishName = "Camshaft",
            category = "Mesin",
            functionDesc = "Mengatur waktu buka dan tutup katup (klep) isap dan buang secara sinkron dengan putaran kruk as.",
            workingPrinciple = "Bumbungan kem (lobe) menekan rocker arm atau mangkok katup sesuai profil durasi dan tinggi angkat (lift) katup.",
            location = "Di dalam kepala silinder (cylinder head).",
            damageSymptoms = listOf("Suara gemericik kasar di area head silinder", "Tenaga mesin ngempos pada putaran RPM tinggi", "Top speed menurun drastis"),
            inspectionMethod = listOf("Ukur tinggi bubungan (cam lift) in dan ex dengan mikrometer luar", "Periksa keausan permukaan lobe dari baret / aus termakan", "Periksa clearance bearing noken as"),
            maintenanceTips = listOf("Pastikan saluran oli menuju silinder kop tidak tersumbat kotoran", "Gunakan oli dengan viskositas yang tepat"),
            k3Warning = "Pastikan posisi timing gear berada pada tanda sejajar (tanda TMA) sebelum melepas rantai keteng agar klep tidak bertabrakan dengan piston.",
            relatedComponents = listOf("Rocker Arm", "Klep / Katup", "Rantai Keteng (Timing Chain)", "Sprocket Kem")
        ),
        ComponentItem(
            id = "spark_plug",
            name = "Busi (Spark Plug)",
            englishName = "Spark Plug",
            category = "Pengapian",
            functionDesc = "Memercikkan bunga api listrik tegangan tinggi untuk membakar campuran bahan bakar dan udara di akhir langkah kompresi.",
            workingPrinciple = "Menerima tegangan tinggi (15.000 - 25.000 Volt) dari koil pengapian, lalu melompati celah elektroda pusat ke elektroda massa.",
            location = "Tertanam pada silinder head mengarah ke ruang bakar.",
            damageSymptoms = listOf("Mesin susah hidup saat pagi hari", "Mesin brebet / tersendat saat digas mendadak", "Konsumsi BBM menjadi lebih boros"),
            inspectionMethod = listOf("Ukur celah elektroda dengan feeler gauge (standar: 0.80 - 0.90 mm)", "Amati warna elektroda: Coklat bata = ideal, Hitam basah = boros/oli bocor, Putih kering = campuran terlalu kurus/miskin"),
            maintenanceTips = listOf("Ganti busi setiap 8.000 - 10.000 km", "Gunakan kunci busi ukuran pas agar keramik isolator tidak retak"),
            k3Warning = "Jangan menyentuh busi atau cop busi saat mesin di-starter karena berisiko tersengat tegangan puluhan ribu volt.",
            relatedComponents = listOf("Ignition Coil", "Cop Busi", "Kabel Busi", "ECU / CDI")
        ),
        ComponentItem(
            id = "regulator_rectifier",
            name = "Kiprok / Regulator Rectifier",
            englishName = "Regulator Rectifier",
            category = "Kelistrikan",
            functionDesc = "Mengubah arus bolak-balik (AC) dari spul generator menjadi arus searah (DC) serta membatasi tegangan pengisian ke aki agar tidak melebihi 14.8 Volt.",
            workingPrinciple = "Dioda penyearah meratakan gelombang AC, sedangkan sirkuit zener/SCR membuang kelebihan tegangan ke massa saat mencapai batas cutoff regulator.",
            location = "Di rangka bodi bagian samping atau depan di bawah tameng.",
            damageSymptoms = listOf("Aki sering tekor padahal aki baru", "Aki kembung mendidih karena overcharge", "Bohlam lampu utama sering putus berulang kali saat digas tinggi"),
            inspectionMethod = listOf("Ukur tegangan pengisian di kutub aki pada 3000-5000 RPM (standar: 13.5 - 14.8 V DC)", "Jika tegangan tembus > 15.5 V, regulator rusak overcharge", "Jika tegangan < 12.8 V saat mesin hidup, penyearah tidak mengisi"),
            maintenanceTips = listOf("Pastikan sirip pendingin kiprok tidak tertutup lumpur", "Pastikan baut massa kiprok ke rangka kencang dan tidak berkarat"),
            k3Warning = "Kiprok beroperasi dalam suhu panas. Jangan sentuh bodi kiprok setelah mesin menyala lama.",
            relatedComponents = listOf("Spul Magnet (Stator)", "Aki / Baterai", "Sekering Utama", "Kabel Pengisian")
        ),
        ComponentItem(
            id = "sensor_tps",
            name = "Sensor TPS (Throttle Position Sensor)",
            englishName = "Throttle Position Sensor",
            category = "EFI",
            functionDesc = "Mendeteksi sudut bukaan katup gas (throttle valve) dan mengirimkan sinyal voltase proporsional ke ECU.",
            workingPrinciple = "Berupa potensiometer resistansi variabel yang menerima input referensi 5 Volt dari ECU dan menghasilkan tegangan output 0.5V (tertutup) hingga 4.5V (terbuka penuh).",
            location = "Terpasang pada poros kupu-kupu Throttle Body.",
            damageSymptoms = listOf("Tarikan motor ngempos pada putaran bukaan gas tertentu", "Indikator lampu MIL berkedip (kedipan 8 pada Honda)", "Mesin tersendat saat akselerasi awal"),
            inspectionMethod = listOf("Gunakan diagnostic scanner atau multimeter digital pada kabel sinyal TPS", "Putar gas perlahan dan amati kenaikan tegangan harus linear mulus tanpa lonjakan mendadak", "Periksa tegangan input referensi 5.0 Volt dari ECU"),
            maintenanceTips = listOf("Jangan menyemprotkan cairan keras langsung ke soket TPS saat membersihkan throttle body", "Lakukan reset TP (Throttle Position) setelah ganti/servis"),
            k3Warning = "Pastikan kontak OFF sebelum melepas atau memasang soket sensor agar tidak terjadi korsleting internal ECU.",
            relatedComponents = listOf("Throttle Body", "ECU", "Injektor", "Kabel Gas")
        ),
        ComponentItem(
            id = "cvt_belt_roller",
            name = "V-Belt & Roller CVT",
            englishName = "CVT Drive Belt & Weight Rollers",
            category = "CVT",
            functionDesc = "Meneruskan putaran dari puli depan (drive pulley) ke puli belakang (driven pulley) secara otomatis menyesuaikan rasio kecepatan dan beban putaran mesin.",
            workingPrinciple = "Gaya sentrifugal mendorong roller menaiki jalur puli sehingga dinding puli primer menjepit v-belt ke diameter lebih besar saat RPM naik.",
            location = "Di dalam bak CVT sebelah kiri mesin matic.",
            damageSymptoms = listOf("Akselerasi lemot dan bergetar hebat di kecepatan rendah", "Timbul bunyi gemeretak atau dengung dari bak CVT", "Kecepatan maksimal (top speed) menurun drastis"),
            inspectionMethod = listOf("Ukur lebar v-belt dengan vernier caliper (batas servis: toleransi aus ~1-2 mm dari standar)", "Periksa retakan pada lekukan v-belt dengan menekuk terbalik", "Timbang dan periksa kebulatan roller (ganti jika aus gepeng/peang)"),
            maintenanceTips = listOf("Servis berkala CVT tiap 8.000 km, bersihkan debu kampas ganda", "Ganti v-belt tiap 20.000 - 24.000 km", "Gunakan grease khusus CVT berdaya tahan panas tinggi (High Temp Polyurea Grease)"),
            k3Warning = "Gunakan treker penahan puli khusus saat mengencangkan mur puli CVT. Jangan menahan menggunakan obeng pada sirip puli kipas karena sirip rentan patah.",
            relatedComponents = listOf("Pulley Primer", "Pulley Sekunder", "Kampas Kopling Ganda", "Rumah Roller (Variator)")
        ),
        ComponentItem(
            id = "brake_master_caliper",
            name = "Caliper & Master Rem Cakram",
            englishName = "Disc Brake Master Cylinder & Caliper",
            category = "Pengereman",
            functionDesc = "Mengubah gaya tekan tangan mekanis menjadi tekanan fluida hidrolik untuk menjepit piringan cakram sehingga kendaraan melambat.",
            workingPrinciple = "Hukum Pascal: Piston master silinder menekan fluida minyak rem melalui selang, mendorong piston kaliper menjepit brake pad ke rotor cakram.",
            location = "Master di setang kemudi kanan/kiri, kaliper di suspensi depan/belakang dekat tromol roda.",
            damageSymptoms = listOf("Rem terasa amblas (ngempos) saat ditekan", "Handle rem membal dan rem tidak pakem", "Roda seret berputar karena piston kaliper macet tidak mau balik"),
            inspectionMethod = listOf("Periksa ketebalan kampas rem (brake pad minimal 1.5 - 2.0 mm)", "Periksa kebocoran minyak rem di seal debu piston kaliper", "Periksa kekeruhan fluida dan lakukan bleeding angin palsu"),
            maintenanceTips = listOf("Kuras dan ganti minyak rem DOT 3/DOT 4 setiap 2 tahun atau 20.000 km", "Lumasi pin kaliper (sliding pin) dengan grease tahan air"),
            k3Warning = "Minyak rem bersifat korosif terhadap cat bodi motor. Segera bilas dengan air bersih jika minyak rem tumpah ke bodi.",
            relatedComponents = listOf("Piringan Cakram", "Brake Pad", "Selang Rem", "Minyak Rem DOT 4")
        )
    )

    val materials = listOf(
        TbsmMaterial(
            id = "siklus_mesin_4_tak",
            title = "Prinsip Kerja & Siklus Mesin 4-Tak (Four Stroke Engine)",
            category = "Mesin",
            summary = "Siklus lengkap 4 langkah kerja mesin bensin: Isap, Kompresi, Usaha, dan Buang yang diselesaikan dalam 2 putaran kruk as (720 derajat).",
            fullContent = """
                Mesin 4-tak (Four-Stroke Engine) adalah mesin pembakaran dalam di mana satu siklus kerja lengkap terdiri dari 4 langkah piston dan 2 putaran kruk as (poros engkol):
                
                1. Langkah Isap (Suction Stroke):
                Piston bergerak dari TMA ke TMB. Katup isap (inlet valve) terbuka, katup buang tertutup. Ruang bakar mengalami penurunan tekanan sehingga campuran udara dan bahan bakar terhisap masuk ke dalam silinder.
                
                2. Langkah Kompresi (Compression Stroke):
                Piston bergerak dari TMB ke TMA. Kedua katup (inlet dan exhaust) tertutup rapat. Campuran bahan bakar dan udara dimampatkan di ruang bakar hingga tekanan dan suhunya meningkat drastis sebelum titik penyalaan busi.
                
                3. Langkah Usaha / Pembakaran (Power Stroke):
                Beberapa derajat sebelum TMA, busi memercikkan bunga api listrik. Terjadi pembakaran cepat yang menghasilkan gas bertekanan dan suhu tinggi. Tekanan ledakan ini mendorong piston kuat-kuat dari TMA menuju TMB dan memutar poros engkol melalui connecting rod.
                
                4. Langkah Buang (Exhaust Stroke):
                Piston bergerak dari TMB kembali ke TMA. Katup buang (exhaust valve) terbuka sedangkan katup isap tertutup. Gas sisa pembakaran didorong keluar melalui exhaust port menuju knalpot.
            """.trimIndent(),
            keyTerms = listOf(
                "TMA (Top Dead Center)" to "Titik henti terjauh piston di bagian atas silinder",
                "TMB (Bottom Dead Center)" to "Titik henti terjauh piston di bagian bawah silinder",
                "Valve Overlapping" to "Kondisi di mana kedua katup (in dan ex) sedikit terbuka bersamaan di akhir langkah buang dan awal langkah isap untuk efisiensi pembilasan",
                "Swept Volume" to "Volume ruang silinder yang dilewati piston dari TMB ke TMA"
            ),
            checkQuestions = listOf(
                "Berapa derajat putaran poros engkol yang dibutuhkan untuk menyelesaikan satu siklus lengkap mesin 4-tak?",
                "Pada langkah manakah kedua katup dalam keadaan tertutup rapat?",
                "Mengapa percikan busi diberikan beberapa derajat sebelum piston tiba tepat di TMA?"
            ),
            safetyNote = "Saat memutar kruk as secara manual untuk pengecekan celah klep, pastikan kunci kontak selalu dalam keadaan OFF."
        ),
        TbsmMaterial(
            id = "dasar_sistem_efi",
            title = "Dasar & Cara Kerja Sistem Electronic Fuel Injection (EFI)",
            category = "EFI",
            summary = "Pengontrolan suplai bahan bakar secara elektronik menggunakan sensor, Electronic Control Unit (ECU), dan aktuator injektor berpresisi tinggi.",
            fullContent = """
                Sistem EFI (Electronic Fuel Injection) menggantikan fungsi karburator mekanis dengan sistem injeksi bahan bakar elektronik yang lebih akurat, efisien, dan ramah lingkungan.
                
                Arsitektur Utama EFI terdiri dari 3 blok utama:
                1. Sensor (Input / Pendeteksi Kondisi):
                Mendeteksi parameter kerja mesin seperti sudut putaran throttle (TPS), tekanan udara manifold (MAP), suhu mesin (ECT/EOT), putaran mesin (CKP), dan kandungan oksigen sisa pembakaran (O2 Sensor).
                
                2. Electronic Control Unit / ECU (Otak / Pemroses):
                Menerima sinyal analog dari sensor, mengubahnya menjadi data digital, lalu menghitung durasi semprotan injektor (Injection Duration) dan waktu pengapian (Ignition Timing) berdasarkan peta kalibrasi (Fuel Map).
                
                3. Aktuator (Output / Eksekutor):
                Komponen fisik yang digerakkan oleh ECU, antara lain: Injektor (menyemprotkan bensin kabut bertekanan), Pompa Bensin (Fuel Pump), Coil Pengapian, dan Idle Speed Control (ISC).
                
                Sistem Closed-Loop vs Open-Loop:
                - Open-Loop: ECU menyemprotkan bensin murni berdasarkan peta tabel standar tanpa koreksi sensor O2 (misal saat mesin dingin atau gas pol).
                - Closed-Loop: ECU membaca data sensor O2 knalpot lalu terus mengoreksi durasi injektor mendekati rasio stoikiometri ideal (14.7 : 1).
            """.trimIndent(),
            keyTerms = listOf(
                "Stoikiometri" to "Rasio perbandingan campuran udara dan bensin ideal secara kimia (14.7 gram udara : 1 gram bensin)",
                "DTC (Diagnostic Trouble Code)" to "Kode kerusakan sistem elektronik yang tersimpan di memori ECU",
                "Fuel Map" to "Tabel data matriks durasi injeksi bahan bakar yang diprogram di ECU berdasarkan RPM dan beban mesin"
            ),
            checkQuestions = listOf(
                "Sebutkan 3 sensor utama yang mengukur beban dan putaran mesin pada sistem injeksi!",
                "Apa perbedaan cara kerja kondisi Closed-Loop dibanding Open-Loop pada sistem EFI?",
                "Berapa standar tekanan bahan bakar (fuel pressure) umum pada motor matic Honda injeksi?"
            ),
            safetyNote = "Sebelum melepas selang bensin bertekanan EFI, buang dulu tekanan sisa dengan melepas soket fuel pump lalu menyalakan mesin sesaat hingga mati sendiri."
        ),
        TbsmMaterial(
            id = "sistem_cvt_matic",
            title = "Cara Kerja dan Komponen Transmisi Otomatis CVT",
            category = "CVT",
            summary = "Continuously Variable Transmission (CVT) mentransfer daya mesin secara halus tanpa perpindahan gigi manual menggunakan sabuk dan puli variabel sentrifugal.",
            fullContent = """
                Transmisi CVT sepeda motor matic terdiri atas dua puli dengan jarak celah yang dapat berubah-ubah (variabel):
                
                1. Puli Primer (Drive Pulley):
                Terpasang langsung pada poros kruk as mesin. Di dalamnya terdapat roller sentrifugal dan movable drive face. Saat putaran mesin meningkat, gaya sentrifugal melempar roller ke arah luar dinding ramp plate, mendorong puli bergerak maju dan menjepit v-belt ke diameter lingkar yang lebih besar.
                
                2. Puli Sekunder (Driven Pulley):
                Terpasang di poros roda belakang/gir reduksi akhir. Menggunakan pegas torsi (torque spring) besar dan movable driven face yang saling mengimbangi tarikan v-belt dari puli primer.
                
                3. Kopling Sentrifugal (Centrifugal Clutch):
                Terletak di puli sekunder. Terdiri dari kanvas kopling ganda (clutch shoe) dan mangkok kopling (clutch outer). Mengembang dan mengunci mangkok kopling saat putaran mencapai sekitar 2.500 - 3.000 RPM.
                
                Perubahan Rasio:
                - Saat Awal / Nanjak (Low Gear): Puli depan diameter kecil, puli belakang diameter besar (Torsi tinggi).
                - Saat Kecepatan Tinggi (High Gear): Puli depan diameter besar, puli belakang membuka ke diameter kecil (Kecepatan tinggi).
            """.trimIndent(),
            keyTerms = listOf(
                "Torque Cam" to "Alur berbentuk sudut pada puli belakang yang menghasilkan efek downshift otomatis saat motor mendapat beban tanjakan",
                "Roller Weight" to "Pemberat bulat berlapis teflon penghasil gaya sentrifugal pendorong puli primer",
                "Grease High-Temp" to "Gemuk pelumas khusus CVT yang tidak mencair pada temperatur operasi tinggi"
            ),
            checkQuestions = listOf(
                "Komponen apa yang bertugas menjepit v-belt di puli primer saat RPM mesin meningkat?",
                "Mengapa v-belt CVT tidak boleh terkena oli atau grease sedikit pun?",
                "Apa dampak pemakaian roller CVT yang terlalu enteng dibanding standar pabrikan?"
            ),
            safetyNote = "Jangan sekali-kali menyalakan mesin dengan bak CVT terbuka saat mur puli belum dikencangkan dengan torsi yang benar."
        )
    )

    val tools = listOf(
        WorkshopTool(
            id = "torque_wrench",
            name = "Kunci Torsi / Momen",
            englishName = "Torque Wrench",
            category = "Measuring Tools",
            functionDesc = "Mengencangkan baut atau mur dengan nilai kekencangan (torsi) yang presisi sesuai standar spesifikasi pabrikan servis manual.",
            howToUse = listOf(
                "Putar setelan pengunci di ujung handel ke nilai torsi yang diinginkan",
                "Pastikan arah putaran ratchet searah jarum jam untuk pengencangan",
                "Tarik tuas perlahan dan konstan hingga terdengar bunyi 'klik' satu kali",
                "Segera hentikan tarikan setelah bunyi klik terdengar"
            ),
            howToRead = "Baca garis skala utama (Nm atau kgf·m) lalu sejajarkan dengan garis skala nonius pada handel putar.",
            maintenanceTips = listOf("Setelah selesai dipakai, selalu kembalikan setelan beban ke angka nol / posisi terendah agar pegas internal tidak kendor", "Jangan gunakan untuk membuka baut yang macet keras"),
            commonMistakes = listOf("Menarik tuas berulang kali setelah bunyi klik berbunyi", "Menjatuhkan kunci momen ke lantai bengkel yang merusak kalibrasi"),
            safetyRule = "Gunakan soket ukuran pas dan pastikan posisi berdiri stabil saat menarik torsi tinggi."
        ),
        WorkshopTool(
            id = "vernier_caliper",
            name = "Jangka Sorong (Vernier Caliper)",
            englishName = "Vernier Caliper",
            category = "Measuring Tools",
            functionDesc = "Mengukur dimensi luar, dimensi dalam, dan kedalaman suatu komponen otomotif dengan ketelitian 0.05 mm atau 0.02 mm.",
            howToUse = listOf(
                "Bersihkan rahang ukur jangka sorong dari oli dan kotoran",
                "Rapatkan rahang ukur dan pastikan garis nol skala utama berimpit dengan garis nol nonius",
                "Jepitkan benda kerja tegak lurus dengan rahang ukur, lalu kencangkan baut pengunci"
            ),
            howToRead = "Baca nilai milimeter bulat sebelum angka nol nonius, lalu cari satu garis nonius yang berimpit lurus sempurna dengan garis skala utama.",
            maintenanceTips = listOf("Simpan di kotak wadahnya dan olesi minyak pelindung tipis", "Jangan gunakan rahang geser sebagai palu atau pengungkit"),
            commonMistakes = listOf("Memposisikan rahang miring saat mengukur diameter lubang dalam", "Menekan rahang terlalu kencang hingga melengkung"),
            safetyRule = "Ujung pengukur kedalaman tajam, hati-hati tertusuk saat mengukur."
        ),
        WorkshopTool(
            id = "multimeter_digital",
            name = "Multimeter Digital (AVO Meter)",
            englishName = "Digital Multimeter",
            category = "Diagnostic",
            functionDesc = "Mengukur parameter kelistrikan: Tegangan DC/AC (Volt), Resistansi/Tahanan (Ohm), Arus (Ampere), dan Kontinuitas jalur kabel.",
            howToUse = listOf(
                "Colokkan probe hitam ke COM dan probe merah ke terminal V/Ohm",
                "Pilih selector switch ke mode yang akan diukur (DCV, ACV, Ohm, atau Buzzer Kontinuitas)",
                "Untuk tegangan DC: pasang paralel merah ke (+) dan hitam ke (-)",
                "Untuk hambatan: pastikan komponen tidak sedang dialiri arus listrik"
            ),
            howToRead = "Baca langsung display LCD angka digital lengkap dengan unit satuan (V, mV, kΩ, MΩ).",
            maintenanceTips = listOf("Matikan switch ke OFF setelah digunakan agar baterai 9V internal tidak habis", "Ganti sekering internal jika pengukuran Ampere overload"),
            commonMistakes = listOf("Mengukur tegangan pada mode Ohm atau Ampere yang menyebabkan sekering multimeter putus seketika", "Memegang ujung besi probe dengan jari saat mengukur hambatan tinggi"),
            safetyRule = "Jangan mengukur tegangan AC tinggi jika isolasi kabel probe terdapat retakan atau sobekan."
        )
    )

    val quizzes = listOf(
        QuizQuestion(
            id = "q1",
            category = "Mesin",
            question = "Pada mesin bensin 4-tak, berapa putaran poros engkol (kruk as) yang dibutuhkan untuk menyelesaikan 1 siklus kerja penuh?",
            options = listOf("1 putaran (360°)", "2 putaran (720°)", "4 putaran (1440°)", "setengah putaran (180°)"),
            correctIndex = 1,
            explanation = "Satu siklus 4-tak (Isap, Kompresi, Usaha, Buang) memerlukan 4 langkah piston atau setara dengan 2 putaran poros engkol (720 derajat)."
        ),
        QuizQuestion(
            id = "q2",
            category = "Kelistrikan",
            question = "Komponen yang bertugas menyearahkan arus AC dari spul dan membatasi tegangan pengisian ke aki adalah:",
            options = listOf("CDI", "Kiprok (Regulator Rectifier)", "Koil Pengapian", "Relay Starter"),
            correctIndex = 1,
            explanation = "Regulator Rectifier (kiprok) menyearahkan gelombang AC menjadi DC dan membatasi voltase maksimal ~14.8V agar aki tidak overcharge."
        ),
        QuizQuestion(
            id = "q3",
            category = "EFI",
            question = "Sensor yang bertugas mendeteksi sudut bukaan katup gas pada Throttle Body sepeda motor injeksi adalah:",
            options = listOf("Sensor MAP", "Sensor O2", "Sensor TPS", "Sensor CKP"),
            correctIndex = 2,
            explanation = "TPS (Throttle Position Sensor) mendeteksi derajat bukaan katup kupu-kupu throttle gas untuk menentukan jumlah bahan bakar yang disemprotkan."
        ),
        QuizQuestion(
            id = "q4",
            category = "CVT",
            question = "Kondisi di mana v-belt CVT berada di diameter terkecil pada puli primer dan diameter terbesar pada puli sekunder terjadi saat:",
            options = listOf("Kecepatan tinggi (Top Speed)", "Putaran awal / akselerasi tanjakan (Low Gear)", "Mesin deselerasi mendadak", "Putaran stasioner (idle) kopling lepas"),
            correctIndex = 1,
            explanation = "Pada saat awal jalan atau tanjakan, puli primer belum terdorong roller sehingga diameter v-belt kecil di depan dan besar di belakang untuk torsi maksimal."
        ),
        QuizQuestion(
            id = "q5",
            category = "K3 & Alat",
            question = "Hal yang wajib dilakukan setelah selesai menggunakan kunci torsi (torque wrench) tipe klik adalah:",
            options = listOf("Melumasi dengan oli kental", "Mengendurkan setelan torsi ke angka nol / skala terendah", "Mengunci posisi torsi tertinggi", "Mengetuk ujung ratchet dengan palu"),
            correctIndex = 1,
            explanation = "Mengendurkan beban ke posisi nol menjaga pegas kalibrasi internal tidak lelah sehingga akurasi torsi tetap terjamin untuk pemakaian berikutnya."
        )
    )

    val flashcards = listOf(
        FlashcardItem(
            id = "fc1",
            category = "Mesin",
            frontQuestion = "Berapa celah standar renggang katup (klep) motor matic Honda 110cc eSP?",
            backAnswer = "Celah Katup Masuk (IN): 0.16 ± 0.02 mm\nCelah Katup Buang (EX): 0.16 ± 0.02 mm (dalam kondisi mesin dingin < 35°C)."
        ),
        FlashcardItem(
            id = "fc2",
            category = "Kelistrikan",
            frontQuestion = "Rumus dasar Hukum Ohm untuk menghitung tegangan (V)?",
            backAnswer = "V = I × R\n(Tegangan Volt = Kuat Arus Ampere × Hambatan Ohm)"
        ),
        FlashcardItem(
            id = "fc3",
            category = "EFI",
            frontQuestion = "Berapa tekanan standar bahan bakar pompa bensin (Fuel Pump) Honda Beat / Vario FI?",
            backAnswer = "Standar: 294 kPa (43 psi / 3.0 kgf/cm²)."
        ),
        FlashcardItem(
            id = "fc4",
            category = "Pengereman",
            frontQuestion = "Apa batas ketebalan servis minimal kampas rem cakram (Brake Pad)?",
            backAnswer = "Batas servis umumnya: 1.5 - 2.0 mm (atau sampai menyentuh alur batas indikator keausan)."
        ),
        FlashcardItem(
            id = "fc5",
            category = "CVT",
            frontQuestion = "Kapan interval penggantian berkala V-Belt CVT yang direkomendasikan pabrikan?",
            backAnswer = "Pemeriksaan setiap 8.000 km dan penggantian rutin setiap 20.000 - 24.000 km."
        )
    )

    val practicumModules = listOf(
        PracticumModule(
            id = "prak_busi",
            title = "Praktikum Pemeriksaan & Penyetelan Celah Busi",
            objectives = listOf(
                "Mampu melepas dan memasang busi dengan prosedur standar SOP",
                "Mampu menganalisis warna elektroda busi untuk diagnosa ruang bakar",
                "Mampu mengukur dan menyetel celah busi sesuai standar pabrikan"
            ),
            toolsNeeded = listOf("Kunci Busi 16mm", "Feeler Gauge", "Sikat Kawat Halus", "Kunci Momen"),
            materialsNeeded = listOf("Busi Sepeda Motor", "Kain Majun", "Cairan Pembersih Karburator / Busi"),
            k3Notes = listOf(
                "Pastikan mesin dingin sebelum melepas busi agar ulir silinder head tidak aus",
                "Gunakan kacamata pelindung saat membersihkan busi dengan sikat kawat"
            ),
            preparation = listOf("Posisikan motor di standar tengah", "Lepas cover bodi pelindung mesin jika diperlukan", "Lepas cop busi dengan menarik bagian karet, bukan kabelnya"),
            procedures = listOf(
                "Kendurkan busi berlawanan arah jarum jam dengan kunci busi",
                "Amati warna elektroda busi dan catat pada lembar kerja praktikum",
                "Bersihkan kerak karbon dengan sikat kawat dan semprot cleaner",
                "Ukur celah dengan bilah feeler gauge ketebalan 0.80 mm dan 0.90 mm",
                "Setel elektroda massa dengan mengetuk lembut jika celah terlalu longgar",
                "Pasang kembali busi dengan tangan hingga rapat, lalu kencangkan dengan kunci momen (torsi ~16 Nm)"
            ),
            standardMeasurements = listOf(
                "Celah Busi Standar" to "0.80 - 0.90 mm",
                "Torsi Pengencangan Busi" to "16 Nm (1.6 kgf·m)",
                "Warna Elektroda Normal" to "Coklat kemerahan / abu-abu kering"
            ),
            conclusionGuideline = "Simpulkan apakah busi masih layak pakai, perlu penyetelan, atau wajib diganti baru berdasarkan hasil pengukuran dan kondisi fisik elektroda."
        ),
        PracticumModule(
            id = "prak_aki",
            title = "Praktikum Pengujian & Perawatan Baterai (Aki Motor)",
            objectives = listOf(
                "Mampu mengukur tegangan baterai (resting voltage)",
                "Mampu mengukur tegangan pengisian regulator (charging voltage)",
                "Mampu mengidentifikasi kondisi kelayakan aki motor"
            ),
            toolsNeeded = listOf("Multimeter Digital", "Obeng Plus Ph2", "Battery Load Tester (jika ada)"),
            materialsNeeded = listOf("Aki Sepeda Motor 12V", "Kain Lap Majun", "Ampelas Halus"),
            k3Notes = listOf(
                "Hati-hati korsleting kutub positif dengan bodi/massa",
                "Lepas kutub negatif (-) terlebih dahulu saat membongkar aki"
            ),
            preparation = listOf("Buka tutup cover aki di dek bawah motor", "Pastikan kunci kontak dalam posisi OFF"),
            procedures = listOf(
                "Setel multimeter ke skala 20V DC",
                "Ukur tegangan tanpa beban (Resting Voltage) pada terminal (+) dan (-)",
                "Putar kontak ke posisi ON dan amati penurunan tegangan",
                "Nyalakan mesin, naikkan putaran mesin ke 3.000 - 5.000 RPM",
                "Ukur tegangan pengisian sistem (Charging Voltage)",
                "Catat seluruh hasil pengukuran pada formulir kerja"
            ),
            standardMeasurements = listOf(
                "Tegangan Aki Sehat (OFF)" to "12.4V - 12.8V DC",
                "Tegangan Drop Saat Starter" to "Tidak boleh drop di bawah 9.6V",
                "Tegangan Pengisian (RPM 3000)" to "13.5V - 14.8V DC"
            ),
            conclusionGuideline = "Tentukan apakah aki membutuhkan charge ulang, sistem pengisian motor bermasalah, atau aki sudah mengalami degradasi sel internal."
        )
    )
}
