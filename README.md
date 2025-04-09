<h1 align="center">🎓 Student Result Publication App</h1>
<p align="center">
  Android app for publishing and viewing academic results, integrated with <strong>bdapps OTP & Subscription APIs</strong>.
</p>


 
---

## 📘 Project Overview

This application enables students to check their exam results by entering their:
- 📌 Roll number
- 📌 Class
- 📌 Date of birth
- 📌 EIIN (Educational Institution Identification Number)

Educational institutions can register and submit results securely after verifying their phone number and subscribing via the **bdapps platform**.

---

## 💡 Why This App Is Useful

This application is especially valuable for educational institutions across the country, as it provides a simple, secure, and efficient way to **create and publish exam results** — without the need for a traditional web portal or manual processing.

Here's how it works:

> Educational institutions can **easily register** using their **EIIN (Educational Institute Identification Number)**. The associated **phone number is already stored** in the system's database.  
>
> ✅ An **OTP is sent** to that number for secure verification.  
> ✅ After verifying the OTP, the institution sets a **secure password**.  
> ✅ Next, the institution is prompted to **subscribe via bdapps**, ensuring only authorized use.  
> ✅ Upon successful subscription, the institution gains **access to the result database**.  
> ✅ Using **MySQL Workbench**, the institution can **upload subject-wise results** directly.  
> ✅ Students can then instantly **view their results via the mobile app**, using just their **roll number, class, date of birth, and EIIN**.

---

## ✨ Features

- 🔐 **Secure OTP verification** via bdapps
- 📞 **Subscription validation** using bdapps Subscribe API
- 🏫 **Institution portal** for uploading subject-wise results
- 📲 **Student-friendly UI** for quick result access
- ⚙️ **Node.js REST API** connected to a cloud-hosted MySQL database
- 📡 **Retrofit integration** in Android for seamless communication

---

## 🧪 Tech Stack

| Layer       | Tech                                |
|-------------|-------------------------------------|
| Frontend    | Jetpack Compose, Kotlin             |
| Backend     | Node.js, Express.js                 |
| Database    | MySQL (hosted on Azure)             |
| Android API | Retrofit                            |
| bdapps APIs | OTP API, Subscribe API              |

---

## 🧩 Architecture Diagram

```
Student App (Android)
      |
      | ———> [Node.js REST API]
                         |
                         |—> MySQL DB (Azure)
                         |—> bdapps OTP API
                         |—> bdapps Subscribe API
```

---

## 🗃️ Database Schema

> Replace this with your actual schema diagram

<p align="center">
  <img src="https://via.placeholder.com/800x400.png?text=Database+Schema" alt="Database Schema" width="600"/>
</p>

---




## ⚙️ Setup & Installation

### 🔧 Backend Setup

```bash
# Clone the repo
git clone https://github.com/md-abdullah-92/Rest-Api-VDS-Reader.git

# Install dependencies
npm install

# Create .env file
touch .env
```

Add the following to `.env`:

```env
DB_HOST=hostabdullah.mysql.database.azure.com
DB_USER=abdullah2020331092
DB_PASS=your-password
DB_NAME=mydb
BDAPPS_APP_ID=your-app-id
BDAPPS_APP_SECRET=your-app-secret
```

Then start the server:
```bash
node index.js
```

### 📱 Android Setup

- Open the Android project in **Android Studio**
- Run on emulator or physical device
- Ensure network access to the REST API

---

## 📡 API Endpoints

| Method | Endpoint             | Description                          |
|--------|----------------------|--------------------------------------|
| POST   | `/register`          | Register institution by EIIN         |
| POST   | `/verify-otp`        | Verify OTP from bdapps               |
| POST   | `/subscribe`         | Check subscription status            |
| POST   | `/submit-results`    | Submit subject-wise exam results     |
| GET    | `/get-results`       | Retrieve student result via filters  |

---

## 🔄 App Workflow

1. **Institution Registration**: Using EIIN and phone number → OTP sent via bdapps.
2. **OTP Verification**: Enter OTP → set password.
3. **Subscription**: Prompted to subscribe via bdapps → on success, gain DB access.
4. **Result Upload**: Upload results via SQL Workbench (or planned admin panel).
5. **Student View**: Students enter info and view results through app.

---
## 📲 Screenshots
<p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image1.png" alt="App Screenshot" width="250"/>
  <p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image2.png" alt="App Screenshot" width="250"/>
  <p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image3.png" alt="App Screenshot" width="250"/>
  <p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image4.png" alt="App Screenshot" width="250"/>
  <p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image5.png" alt="App Screenshot" width="250"/>
  <p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image6.png" alt="App Screenshot" width="250"/>
  <p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image7.png" alt="App Screenshot" width="250"/>
  <p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image8.png" alt="App Screenshot" width="250"/>
  <p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image9.png" alt="App Screenshot" width="250"/>
  <p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image10.png" alt="App Screenshot" width="250"/>
  <p align="center">
  <img src="https://github.com/md-abdullah-92/BDAPPS2020331092/blob/master/appimage/screen/image11.png" alt="App Screenshot" width="250"/>

## 🙌 Author

**👨‍💻 MD Abdullah Al Mahadi Apurbo**  
- 🎓 CSE, Shahjalal University of Science & Technology (SUST)  
- 🌐 [LinkedIn](https://linkedin.com/in/abdullah-al-mahadi-apurbo-88261b292)  
- 💻 [GitHub](https://github.com/md-abdullah-92)
