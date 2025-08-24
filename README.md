# Send Money App

## Overview
This is a Jetpack Compose-based Android app for sending money, with features like service selection, form validation, request saving, and bilingual support (English/Arabic) with RTL/LTR layouts.
## Architecture
The app follows **MVVM (Model-View-ViewModel) architecture** with elements of **Clean Architecture**.

### Layers
1. **Data Layer** (`data/` package):
   - **Model**: Data classes (e.g., `ServiceData`, `SavedRequest`) for structuring app data.
   - **Repository**: `SendMoneyRepository` handles data access.

2. **Domain Layer** :
   -  simple logic is in ViewModels.

3. **UI Layer** (`ui/` package):
   - **View (Composables)**: Screens (e.g., `LoginScreen.kt`) render UI reactively, observing ViewModel state.
   - **ViewModel**: Each screen has a ViewModel (e.g., `LoginViewModel.kt`) managing state, logic, and repository interactions. Uses Hilt for injection and Kotlin Flow for reactive updates.
   - **Common**: Reusable components.
   - **Navigation**: `SendMoneyApp.kt` handles `NavHost` for routing.

4. **DI Layer** (`di/` package):
   - Hilt modules provide dependencies like repository and `LanguageManager`.

5. **Util Layer** (`util/` package):
   - Helpers like `LanguageManager.kt` for localization/RTL and `ResourceUtils.kt` for JSON reading.

  **Instructions** :
  - Tap the 'Send Money App' text to autofill credentials and log in.
  - 
### Video Demo
[Click to watch video](./media/Mansoor_Task.webm)
