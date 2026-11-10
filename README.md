# ⚡ EV Charging Management System (EvChargingApp)

A full-stack desktop application built with **Java** and **MySQL** to manage electric vehicle charging stations, priority-based queues, session billing, multi-method payments, customer loyalty rewards, and driver reviews.

---

## 🚀 Key Features

- **Authentication & Registration System**: Secure, dark-themed login and account creation with BCrypt salted password hashing, input validation, and MySQL persistence.
- **Driver & Vehicle Profiles**: Manage driver identities, vehicle battery capacities (kWh), current battery levels, and connector types (CCS2, Type 2, CHAdeMO, GB/T).
- **Connector Compatibility Engine**: Verifies physical connector compatibility between vehicle and charger before permitting bookings.
- **Priority Queue Dispatching**: Dynamic queue algorithm that automatically prioritizes:
  1. `EMERGENCY` bookings first.
  2. Vehicles with the lowest battery percentage.
- **Charging Sessions & Billing**: Real-time metering (kWh consumed) and automated bill calculation based on station price rates.
- **Payment Processing & Audit Trail**: Multi-method payments (`UPI`, `CARD`, `WALLET`, `CASH`) with transaction logging in MySQL.
- **Loyalty Rewards Program**: Automatic calculation (1 loyalty point per ₹10 spent) with transaction audit records (`EARNED` and `REDEEMED`).
- **Verified Reviews**: 1–5 star ratings and driver feedback allowed only on completed charging bookings.
- **Modern Dark-Themed GUI**: Built in Java Swing using custom typography, responsive card components, and live MySQL indicators.

---

## 📁 Package Architecture

```
com.evcharge.app
 ├── AppTheme.java               # Custom dark theme and component styling
 ├── DBConnection.java           # Thread-safe JDBC connection manager
 ├── LoginFrame.java             # User login window (Entry Point)
 ├── RegisterFrame.java          # User registration window
 ├── MainFrame.java              # Main Swing dashboard with navigation & logout
 ├── Main.java                   # Console-based runner & workflow demonstration
 ├── AppTestSuite.java           # Automated test suite (47 assertions)
 ├── AuthScenariosTest.java      # 12-scenario authentication verification test
 │
 ├── model/                      # Domain Entities
 │    ├── User.java              # Authenticated user entity
 │    ├── Driver.java
 │    ├── Vehicle.java
 │    ├── ChargingLocation.java
 │    ├── Charger.java
 │    ├── Booking.java
 │    ├── ChargingSession.java
 │    ├── Payment.java
 │    └── Review.java
 │
 ├── dao/                        # Data Access Objects (CRUD with MySQL)
 │    ├── UserDAO.java           # User credentials & account storage
 │    ├── DriverDAO.java
 │    ├── VehicleDAO.java
 │    ├── ChargerDAO.java
 │    ├── BookingDAO.java
 │    ├── ChargingSessionDAO.java
 │    ├── PaymentDAO.java
 │    ├── LoyaltyDAO.java
 │    └── ReviewDAO.java
 │
 └── service/                    # Business Logic Layer
      ├── AuthService.java       # Credential validation & BCrypt hashing
      ├── BookingQueue.java      # Comparator-based priority queue
      ├── BookingService.java    # Validation, dispatching, and status state machine
      └── LoyaltyService.java    # Points calculation and redemption
```

---

## 🗄️ Database Setup

The database schema is defined in [`schema.sql`](schema.sql).

### 1. Initialize the Database
Open MySQL Workbench, MySQL CLI, or your preferred SQL client and run:
```sql
SOURCE /path/to/EvChargingApp/schema.sql;
```
This creates the `ev_charging_app` database with all 10 tables, foreign key constraints, check constraints, and seed data.

### 2. Configure Credentials
Update [`db.properties`](db.properties) with your MySQL connection details:
```properties
db.url=jdbc:mysql://localhost:3306/ev_charging_app?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.user=root
db.password=YOUR_PASSWORD
```

---

## 💻 How to Run

### Option 1: Run via Eclipse IDE
1. Open Eclipse and import the `EvChargingApp` project.
2. The project `.classpath` is already pre-configured to include `lib/jbcrypt-0.4.jar` and the MySQL Connector/J driver.
3. Right-click **`LoginFrame.java`** (or **`MainFrame.java`**) $\rightarrow$ **Run As** $\rightarrow$ **Java Application** to launch the application. The login page will appear first.
4. Demo account available: **`ananya@example.com`** / **`Password123!`**, or click **Create Account** to register a new user.
5. Run **`AppTestSuite.java`** or **`AuthScenariosTest.java`** to run automated unit, integration, and database tests.

### Option 2: Run via Command Line

**Run the Swing GUI (Starts with Login Page):**
```powershell
java -cp "bin;lib/*;C:/Users/User/Downloads/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0.jar" com.evcharge.app.LoginFrame
```

**Run the Console Demo:**
```powershell
java -cp "bin;lib/*;C:/Users/User/Downloads/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0.jar" com.evcharge.app.Main
```

**Run the Automated Test Suite (47 Tests):**
```powershell
java -cp "bin;lib/*;C:/Users/User/Downloads/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0.jar" com.evcharge.app.AppTestSuite
```

**Run the 12-Scenario Authentication Verification:**
```powershell
java -cp "bin;lib/*;C:/Users/User/Downloads/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0/mysql-connector-j-26.10.0.jar" com.evcharge.app.AuthScenariosTest
```

### Option 3: Run with Maven
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.evcharge.app.MainFrame"
```

---

## 🧪 Test Suite Summary
The project includes an automated test runner ([`AppTestSuite.java`](src/com/evcharge/app/AppTestSuite.java)) covering:
- **Priority Queue**: Verifies EMERGENCY leapfrogging and low-battery tiebreaking.
- **Connector Compatibility**: Tests matching, mismatching, and case-insensitivity.
- **Loyalty Program**: Verifies exact mathematical award rules and payment status guards.
- **Lifecycle Transitions**: Validates `PENDING` $\rightarrow$ `CONFIRMED` $\rightarrow$ `CHARGING` $\rightarrow$ `COMPLETED`.
- **Review Rules**: Ensures reviews cannot be submitted for incomplete bookings or invalid star ratings.
- **Live Database Connection**: Asserts MySQL table accessibility and connectivity.
