-- ============================================================
-- EV CHARGING APPLICATION - DATABASE SCHEMA & INITIAL DATA
-- Database: ev_charging_app
-- ============================================================

CREATE DATABASE IF NOT EXISTS `ev_charging_app`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE `ev_charging_app`;

-- ------------------------------------------------------------
-- 1. DRIVERS TABLE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `drivers` (
  `driver_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `phone_number` VARCHAR(15) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `loyalty_points` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`driver_id`),
  UNIQUE KEY `uq_driver_phone` (`phone_number`),
  UNIQUE KEY `uq_driver_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- 2. VEHICLES TABLE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `vehicles` (
  `vehicle_id` INT NOT NULL AUTO_INCREMENT,
  `driver_id` INT NOT NULL,
  `model` VARCHAR(100) NOT NULL,
  `battery_capacity_kwh` DECIMAL(5,2) NOT NULL,
  `connector_type` VARCHAR(30) NOT NULL,
  `current_battery_percentage` INT NOT NULL,
  PRIMARY KEY (`vehicle_id`),
  KEY `idx_vehicle_driver` (`driver_id`),
  CONSTRAINT `fk_vehicles_driver` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`driver_id`) ON DELETE CASCADE,
  CONSTRAINT `chk_battery_pct` CHECK (`current_battery_percentage` BETWEEN 0 AND 100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- 3. CHARGING LOCATIONS TABLE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `charging_locations` (
  `location_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `location_type` VARCHAR(50) NOT NULL,
  `verified` TINYINT(1) NOT NULL DEFAULT 0,
  `rating` DECIMAL(2,1) NOT NULL DEFAULT 0.0,
  PRIMARY KEY (`location_id`),
  CONSTRAINT `chk_location_rating` CHECK (`rating` BETWEEN 0.0 AND 5.0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- 4. LOCATION AMENITIES TABLE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `location_amenities` (
  `amenity_id` INT NOT NULL AUTO_INCREMENT,
  `location_id` INT NOT NULL,
  `amenity_name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`amenity_id`),
  KEY `idx_amenities_location` (`location_id`),
  CONSTRAINT `fk_amenities_location` FOREIGN KEY (`location_id`) REFERENCES `charging_locations` (`location_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- 5. CHARGERS TABLE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `chargers` (
  `charger_id` INT NOT NULL AUTO_INCREMENT,
  `location_id` INT NOT NULL,
  `charger_code` VARCHAR(30) NOT NULL,
  `charging_mode` VARCHAR(20) NOT NULL,
  `connector_type` VARCHAR(30) NOT NULL,
  `price_per_kwh` DECIMAL(8,2) NOT NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`charger_id`),
  UNIQUE KEY `uq_charger_code` (`charger_code`),
  KEY `idx_charger_location` (`location_id`),
  CONSTRAINT `fk_chargers_location` FOREIGN KEY (`location_id`) REFERENCES `charging_locations` (`location_id`) ON DELETE CASCADE,
  CONSTRAINT `chk_price_per_kwh` CHECK (`price_per_kwh` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- 6. BOOKINGS TABLE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookings` (
  `booking_id` INT NOT NULL AUTO_INCREMENT,
  `driver_id` INT NOT NULL,
  `vehicle_id` INT DEFAULT 1,
  `charger_id` INT NOT NULL,
  `request_type` ENUM('NORMAL','EMERGENCY') NOT NULL,
  `battery_percentage_at_request` INT DEFAULT 100,
  `booking_status` ENUM('PENDING','CONFIRMED','CHARGING','COMPLETED','CANCELLED') NOT NULL DEFAULT 'PENDING',
  `requested_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mode` VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
  PRIMARY KEY (`booking_id`),
  KEY `idx_bookings_driver` (`driver_id`),
  KEY `idx_bookings_vehicle` (`vehicle_id`),
  KEY `idx_bookings_charger` (`charger_id`),
  CONSTRAINT `fk_bookings_driver` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`driver_id`),
  CONSTRAINT `fk_bookings_vehicle` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`),
  CONSTRAINT `fk_bookings_charger` FOREIGN KEY (`charger_id`) REFERENCES `chargers` (`charger_id`),
  CONSTRAINT `chk_booking_battery` CHECK (`battery_percentage_at_request` BETWEEN 0 AND 100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- 7. CHARGING SESSIONS TABLE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `charging_sessions` (
  `session_id` INT NOT NULL AUTO_INCREMENT,
  `booking_id` INT NOT NULL,
  `started_at` DATETIME DEFAULT NULL,
  `ended_at` DATETIME DEFAULT NULL,
  `energy_used_kwh` DECIMAL(8,2) DEFAULT NULL,
  `total_amount` DECIMAL(10,2) DEFAULT NULL,
  `session_status` ENUM('NOT_STARTED','CHARGING','COMPLETED') NOT NULL DEFAULT 'NOT_STARTED',
  PRIMARY KEY (`session_id`),
  UNIQUE KEY `uq_sessions_booking` (`booking_id`),
  CONSTRAINT `fk_sessions_booking` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- 8. PAYMENTS TABLE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `payments` (
  `payment_id` INT NOT NULL AUTO_INCREMENT,
  `session_id` INT NOT NULL,
  `payment_method` ENUM('UPI','CARD','CASH','WALLET') NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `payment_status` ENUM('PENDING','PAID','FAILED') NOT NULL DEFAULT 'PENDING',
  `paid_at` DATETIME DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  UNIQUE KEY `uq_payments_session` (`session_id`),
  CONSTRAINT `fk_payments_session` FOREIGN KEY (`session_id`) REFERENCES `charging_sessions` (`session_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- 9. LOYALTY TRANSACTIONS TABLE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `loyalty_transactions` (
  `loyalty_transaction_id` INT NOT NULL AUTO_INCREMENT,
  `driver_id` INT NOT NULL,
  `payment_id` INT DEFAULT NULL,
  `points` INT NOT NULL,
  `transaction_type` ENUM('EARNED','REDEEMED') NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`loyalty_transaction_id`),
  UNIQUE KEY `uq_loyalty_payment` (`payment_id`),
  KEY `idx_loyalty_driver` (`driver_id`),
  CONSTRAINT `fk_loyalty_driver` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`driver_id`),
  CONSTRAINT `fk_loyalty_payment` FOREIGN KEY (`payment_id`) REFERENCES `payments` (`payment_id`),
  CONSTRAINT `chk_loyalty_points` CHECK (`points` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- 10. REVIEWS TABLE
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `reviews` (
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `booking_id` INT NOT NULL,
  `driver_id` INT NOT NULL,
  `location_id` INT NOT NULL,
  `rating` INT NOT NULL,
  `comment` VARCHAR(500) DEFAULT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  UNIQUE KEY `uq_reviews_booking` (`booking_id`),
  KEY `idx_reviews_driver` (`driver_id`),
  KEY `idx_reviews_location` (`location_id`),
  CONSTRAINT `fk_reviews_booking` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`),
  CONSTRAINT `fk_reviews_driver` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`driver_id`),
  CONSTRAINT `fk_reviews_location` FOREIGN KEY (`location_id`) REFERENCES `charging_locations` (`location_id`),
  CONSTRAINT `chk_review_rating` CHECK (`rating` BETWEEN 1 AND 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- SAMPLE SEED DATA
-- ------------------------------------------------------------
INSERT IGNORE INTO `drivers` (`driver_id`, `name`, `phone_number`, `email`, `loyalty_points`)
VALUES (1, 'Ananya Sharma', '9876543210', 'ananya@example.com', 164),
       (2, 'Rahul Kumar', '9123456789', 'rahul@example.com', 50);

INSERT IGNORE INTO `vehicles` (`vehicle_id`, `driver_id`, `model`, `battery_capacity_kwh`, `connector_type`, `current_battery_percentage`)
VALUES (1, 1, 'Tata Nexon EV', 40.50, 'CCS2', 18),
       (2, 2, 'MG ZS EV', 50.30, 'CCS2', 10);

INSERT IGNORE INTO `charging_locations` (`location_id`, `name`, `address`, `location_type`, `verified`, `rating`)
VALUES (1, 'GreenCharge Cafe', 'MG Road, Bengaluru', 'Cafe', 1, 4.6),
       (2, 'EcoCharge Hub', 'Indiranagar, Bengaluru', 'Commercial Hub', 1, 4.8);

INSERT IGNORE INTO `location_amenities` (`amenity_id`, `location_id`, `amenity_name`, `description`)
VALUES (1, 1, 'Coffee Shop', 'Artisanal coffee and bakery on-site'),
       (2, 1, 'Restroom', 'Clean, accessible restrooms'),
       (3, 1, 'Free Wi-Fi', 'High-speed wireless internet');

INSERT IGNORE INTO `chargers` (`charger_id`, `location_id`, `charger_code`, `charging_mode`, `connector_type`, `price_per_kwh`, `available`)
VALUES (1, 1, 'GC-01', 'Fast', 'CCS2', 18.50, 1),
       (2, 1, 'GC-02', 'Ultra-Fast', 'CCS2', 22.00, 1),
       (3, 2, 'EH-01', 'Fast', 'Type 2', 16.00, 1);

-- ------------------------------------------------------------
-- 11. USERS TABLE (Authentication & Account Management)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `app_users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uq_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Initial Demo User (Password: Password123!)
INSERT IGNORE INTO `app_users` (`user_id`, `full_name`, `email`, `password_hash`)
VALUES (1, 'Ananya Sharma', 'ananya@example.com', '$2a$12$YZPCEGXi29M5xmtluPKgM.fplCD0u1F1c5lQRl.iYGK0QglO1geJa');
