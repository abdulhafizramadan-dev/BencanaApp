
# ME14-BencanaApp-GG3MEGP0532-Abdul-Hafiz-Ramadan

[Link Drive](https://drive.google.com/drive/folders/1nC7YggnOvqr4NtVukrQlp-sqGJCjyRYY?usp=sharing)

[Demo Youtube](https://youtu.be/FGBRncG_s6M)

## Introduction

Halo semua nya perkenalkan saya `Abdul Hafiz Ramadan` dengan id `GG3MEGP0532`, peserta generasi GIGIH 3.0 dengan learning track mobile engineer, kelas ME 14. Disini saya mempresentasikan aplikasi BencanaApp yang merupakan final task dari generasi  gigih 3.0.

## BencanaApp
Disaster Map adalah aplikasi yang membantu pengguna untuk mengawasi berbagai bencana alam yang terjadi di Indonesia, seperti banjir, gempa bumi, kebakaran, kabut asap, angin kencang, dan aktivitas gunung berapi. Aplikasi ini menawarkan informasi real-time tentang bencana-bencana tersebut, sehingga pengguna dapat mengetahui data terkini. Selain itu, aplikasi ini juga memiliki preferensi notifikasi. Jika preferensi notifikasi diaktifkan, pengguna akan mendapatkan notifikasi tepat waktu tentang daerah yang terdampak banjir di Jakarta, dengan menyajikan status banjir secara real-time.

## Features
Aplikasi bencana app memiliki fitur utama:
 - Menampilkan daftar bencana dalam periode waktu tertentu
 - Memfilter bencana seperti :(banjir, gempa bumi, kebakaran,
 - kabut asap, gunung berapi, dll) ✅
 - Memfilter bencana berdasarkan area ✅
 - Menampilkan bencana di dalam peta ✅
 - Notifikasi berdasarkan ketinggian air
 - Mendukung tema terang/gelap ✅
 - Mendukung animasi loading ✅


## Dependencies
Aplikasi bencapa app dibangun menggunakan: 
|Nama| Versi |
|--|--|
| Kotlin | 1.7.20 |
| Compose BOM | 2022.10.00 |
| Hilt | 2.44 |
| Material Design 2 | 2 |
| Material Design 3 | 3 |
| Compose Destinations | 1.7.41-beta |
| Google Maps SDK for Compose | 2.11.5 |
| Accompanist Permissions | 0.31.5-beta |
| Datastore Preferences | 1.0.0 |
| Compose Shimmer | 1.0.5 |
| desugar_jdk_libs_nio | 2.0.2 |
| Retrofit 2 | 2.9.0 |
| Gson | 2.9.0 |

# Getting Started

## Prerequisites
- Android Studio Giraffe | 2022.3.1 Canary 11
- MinSdk 24

## Clone Project
Untuk memulai proyek, kloning proyek menggunakan perintah berikut:
```
git clone https://github.com/GG-3-0-Mobile-Engineering/ME14-BencanaApp-GG3MEGP0532-Abdul-Hafiz-Ramadan.git
```

## Set Up Google Maps API Key
Sebelum menjalankan aplikasi, Anda perlu mendapatkan Google Maps API Key dari [sini](https://developers.google.com/maps/documentation/android-sdk/get-api-key). 
Tambahkan Kunci Google Maps API dan URL Peta Bencana API ke file local.properties di proyek.
```
MAPS_API_KEY={ADD_YOUR_API_KEY}
```
