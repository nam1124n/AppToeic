# TOEIC Test Pro

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)

TOEIC Test Pro is a comprehensive Android application designed to help users prepare for the TOEIC exam effectively. The application provides various practice sections, tracks learning progress, and includes an administrative tool for managing test content.

## ✨ Key Features

* **User Authentication**: Secure sign-in and sign-up using Firebase Authentication.
* **Skill-focused Practice**: Dedicated sections for practicing different TOEIC skills, notably the Writing section (Q1-5, Q6-8) with sample answers and explanations.
* **Daily Learning Streak**: Motivates users by tracking their consecutive days of learning and displaying a weekly streak calendar.
* **Admin Panel**: An exclusive dashboard for administrators to create, read, update, and delete (CRUD) writing practice questions directly to Firebase Firestore, with real-time syncing.
* **Dark & Light Mode**: Full support for system-wide light and dark themes with adaptive UI elements for optimal contrast.
* **Clean & Modern UI**: Built entirely with Jetpack Compose following Material Design 3 guidelines.

## 🛠 Tech Stack & Architecture

This project is built using modern Android development best practices:

* **Language**: [Kotlin](https://kotlinlang.org/)
* **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for building native UI declaratively.
* **Architecture**: **Clean Architecture** with **MVVM** (Model-View-ViewModel) pattern to separate concerns and improve testability and maintainability.
* **Dependency Injection**: [Dagger-Hilt](https://dagger.dev/hilt/) for robust dependency injection.
* **Asynchronous Programming**: **Coroutines** and **StateFlow/SharedFlow** for handling background operations and state management.
* **Local Data Storage**: [Room Database](https://developer.android.com/training/data-storage/room) for caching data and managing streak history locally.
* **Remote Backend & Auth**: 
  * **Firebase Authentication** for user identity.
  * **Firebase Firestore** for storing and syncing practice questions.
* **Navigation**: Jetpack Compose Navigation for seamless screen transitions.

## 🚀 Getting Started

### Prerequisites
* Android Studio (Latest version recommended)
* JDK 17 or higher
* A Firebase Project (for Authentication and Firestore)

### Installation
1. Clone the repository:
   ```bash
   git clone <repository_url>
   ```
2. Open the project in Android Studio.
3. Configure **Firebase**:
   * Add your `google-services.json` file to the `app/` directory.
   * Ensure Firebase Authentication (Email/Password) and Firestore are enabled in your Firebase console.
4. Sync the Gradle files.
5. Build and run the application on an emulator or physical device.

## 👨‍💻 Developed By

[NamPer]
