# Repositori ini berisi dua aplikasi

1. **Checklist Kepatuhan UU 27/2026** — `index.html` di root.
2. **KerjaKu** — aplikasi To Do, Tugas & Notes (versi HTML/PWA dan Android) — folder `kerjaku/` dan `android/`.

---

## KerjaKu

Aplikasi produktivitas offline: **To Do** (dengan jam, kategori, prioritas), **Tugas** (deadline, langkah-langkah + progres, pin ke Home), dan **Notes** (warna, sematkan). Dilengkapi pencarian, kalender bulanan, notifikasi deadline, pengingat sesuai jam, tema gelap, serta ekspor/impor cadangan JSON. Semua data tersimpan di perangkat, tanpa akun dan tanpa server.

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
