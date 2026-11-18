# Repositori ini berisi dua aplikasi

1. **Checklist Kepatuhan UU 27/2026** — `index.html` di root.
2. **KerjaKu** — aplikasi To Do, Tugas & Notes (versi HTML/PWA dan Android) — folder `kerjaku/` dan `android/`.

---

## KerjaKu

Aplikasi produktivitas offline bergaya Notion: **To Do** (dengan jam, kategori, prioritas), **Tugas** (deadline, langkah-langkah + progres, pin ke Home), dan **Notes** (warna, sematkan). Dilengkapi pencarian, kalender bulanan, notifikasi deadline, pengingat sesuai jam, tema gelap, serta ekspor/impor cadangan JSON. Semua data tersimpan di perangkat, tanpa akun dan tanpa server.

### Desain (v2): konsep Notion + color theory

**Pola Notion yang dipakai**
- Setiap menu adalah *halaman*: cover, ikon emoji besar, judul, lalu baris **properti** (Tanggal, Progres, Status).
- *Callout* 💡, heading yang bisa dilipat (toggle), dan **blok to-do** dengan checkbox persegi.
- **Database view**: To Do (tab Hari ini / Semua / Selesai, dikelompokkan per tanggal), Tugas (**Board** per status atau **List**), Notes (**Gallery**).
- Membuka item = *page peek*: judul besar, properti berupa **tag** berwarna, ikon emoji bisa diganti, dan **tersimpan otomatis** (tanpa tombol Simpan).
- Breadcrumb `KerjaKu / Halaman`, ikon garis tipis monokrom, sudut kecil (3–6 px), garis tipis dan bukan bayangan tebal.

**Color theory**
- **60-30-10**: 60% netral hangat (`#ffffff` dan teks `#37352f`, hue ±45° yang terasa hangat, bukan abu-abu dingin), 30% permukaan abu-abu lembut (blok, tag, hover), 10% aksen biru `#2383e2` hanya untuk interaksi (checkbox, tombol Selesai, tanggal terpilih).
- **Skema split-complementary** untuk tiga jenis konten: To Do = **biru** (aksi, satu keluarga dengan aksen), Tugas = **ungu** (analog dengan biru, masih "kerja"), Notes = **oranye** (komplementer, hangat untuk "ide").
- **Warna semantik konsisten** di semua layar: merah = prioritas Tinggi atau terlambat, kuning = Sedang, abu-abu = Rendah (sengaja diredam agar tidak bersaing), hijau = selesai.
- **Palet 9 warna Notion** (gray, brown, orange, yellow, green, blue, purple, pink, red), masing-masing punya varian teks, latar blok, dan tag untuk mode terang & gelap. Semua token ada di objek `PALETTE` di bagian atas `kerjaku/index.html`.
- **Cover Home** memakai gradasi warna *analog* yang berubah mengikuti waktu: pagi kuning→oranye, siang biru→toska, sore oranye→merah muda, malam indigo→ungu.
- Teks sekunder `#73726e` di atas putih memiliki kontras ±4,8:1 (lolos WCAG AA).

### Fitur tambahan (v2.1)

- **To Do berulang** — properti *Ulangi*: Harian, Hari kerja, Mingguan, Bulanan. Saat dicentang, To Do berikutnya otomatis dibuat di tanggal selanjutnya (ditandai 🔁); membatalkan centang menariknya kembali.
- **Folder untuk Notes** — properti *Folder* di setiap catatan; halaman Notes punya tab per folder (ala view Notion), plus tab *Tanpa folder*.
- **Statistik mingguan** — bagian *📈 Minggu Ini* di Home: jumlah selesai (dibanding minggu lalu), hari beruntun, kategori teratas, dan grafik batang Senin–Minggu (ketuk batang untuk detail).
- **Pengingat di Android walau aplikasi ditutup** — To Do berjam dan deadline tugas dijadwalkan lewat alarm sistem, dan dijadwalkan ulang otomatis setelah HP dinyalakan ulang. Aktifkan di *Pengaturan → Pengingat*. Di Android 13+ alarm bisa meleset beberapa menit kecuali izin *Alarm & pengingat* diberikan di pengaturan aplikasi. Versi web/browser tetap hanya mengingatkan selama aplikasi terbuka.

### Versi HTML (web / PWA)

- Buka `kerjaku/index.html` langsung di browser, atau lewat GitHub Pages:
  `https://arissetiyono7-eng.github.io/checklist-uu27/kerjaku/` (aktif bila Pages diterbitkan dari branch yang memuat folder ini).
- Di Chrome Android: menu ⋮ → **Tambahkan ke layar utama** / **Instal aplikasi**. Setelah dibuka sekali, aplikasi tetap jalan tanpa internet.

### Versi Android (APK)

- **Unduh langsung:** `rilis/KerjaKu.apk`, atau dari halaman **Releases** (tag `kerjaku-latest`) yang diperbarui otomatis oleh GitHub Actions setiap ada perubahan di `kerjaku/` atau `android/`.
- Pasang di HP: buka berkas APK → izinkan *Instal dari sumber tidak dikenal*.
- Aplikasi Android memuat file HTML yang sama dari dalam APK (WebView), jadi tampilan dan fiturnya identik dan **sepenuhnya offline**. Tambahan khusus Android: notifikasi sistem untuk pengingat, simpan cadangan ke folder *Download*, tombol Back menutup lembar yang terbuka.

### Membangun APK sendiri

```bash
cd android
./gradlew assembleRelease      # hasil: app/build/outputs/apk/release/app-release.apk
```

Butuh JDK 17+ dan Android SDK (atau cukup buka folder `android/` di Android Studio).

> APK ditandatangani dengan `android/kerjaku-debug.keystore` (kunci debug publik, kata sandi `android`) supaya update dari GitHub bisa dipasang tanpa menghapus aplikasi lama. Untuk rilis ke Play Store, ganti dengan keystore pribadi yang **tidak** di-commit.

### Struktur

- `kerjaku/index.html` — seluruh aplikasi (HTML + CSS + JS, satu file).
- `kerjaku/sw.js`, `manifest.webmanifest`, ikon — dukungan offline & instal PWA.
- `android/` — proyek Android (Java, WebView + jembatan notifikasi/berkas).
- `.github/workflows/kerjaku-android.yml` — build APK otomatis.

---

## Checklist Kepatuhan UU 27/2026

Alat bantu untuk mencocokkan kebijakan perusahaan dengan UU Nomor 27 Tahun 2026 tentang Pelindungan Ketenagakerjaan: 146 butir dalam 20 kategori.

### Cara memakai

Buka `index.html` di browser. Tidak perlu instalasi atau server.

- Semua isian (status, catatan, PIC, target, profil perusahaan) tersimpan hanya di browser yang dipakai.
- Untuk pindah perangkat atau bergantian dengan rekan, pakai menu **Data & laporan → Unduh cadangan**, lalu **Pulihkan dari cadangan** di perangkat lain.

### Isi repositori

- `index.html` — aplikasi (satu file, termasuk data butir).
- `arsip/` — versi sebelumnya, untuk pembanding.

### Catatan

Teks sumber UU hasil OCR, jadi nomor pasal dan angka perlu dicek ke salinan resmi. Checklist ini alat bantu penelusuran, bukan nasihat hukum.
