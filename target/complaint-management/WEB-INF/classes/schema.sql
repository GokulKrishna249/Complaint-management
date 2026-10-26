-- ============================================================
-- Complaint Management System - Database Schema
-- MySQL 8.x
-- ============================================================

CREATE DATABASE IF NOT EXISTS complaint_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE complaint_db;

-- ── Users ─────────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS users (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100)  NOT NULL,
    email         VARCHAR(150)  NOT NULL UNIQUE,
    password_hash VARCHAR(255)  NOT NULL,
    role          ENUM('CUSTOMER','STAFF','ADMIN') NOT NULL DEFAULT 'CUSTOMER',
    active        TINYINT(1)    NOT NULL DEFAULT 1,
    created_at    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ── Complaints ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS complaints (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(200)  NOT NULL,
    description       TEXT          NOT NULL,
    category          VARCHAR(100)  NOT NULL,
    status            ENUM('OPEN','ASSIGNED','IN_PROGRESS','RESOLVED','CLOSED','ESCALATED')
                          NOT NULL DEFAULT 'OPEN',
    priority          ENUM('LOW','MEDIUM','HIGH','CRITICAL')
                          NOT NULL DEFAULT 'MEDIUM',
    customer_id       INT           NOT NULL,
    assigned_staff_id INT           NULL,
    resolution_notes  TEXT          NULL,
    created_at        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    resolved_at       DATETIME      NULL,
    FOREIGN KEY (customer_id)       REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_staff_id) REFERENCES users(id) ON DELETE SET NULL
);

-- ── Status History ─────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS status_history (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    complaint_id      INT NOT NULL,
    old_status        VARCHAR(30) NOT NULL,
    new_status        VARCHAR(30) NOT NULL,
    changed_by_user_id INT NOT NULL,
    remarks           TEXT NULL,
    changed_at        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (complaint_id)        REFERENCES complaints(id) ON DELETE CASCADE,
    FOREIGN KEY (changed_by_user_id)  REFERENCES users(id)
);

-- ── Seed: default admin account (password: Admin@1234) ────────────────────
INSERT IGNORE INTO users (name, email, password_hash, role)
VALUES ('Administrator', 'admin@cms.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        'ADMIN');

-- ── Indexes ────────────────────────────────────────────────────────────────
CREATE INDEX idx_complaints_status      ON complaints(status);
CREATE INDEX idx_complaints_customer    ON complaints(customer_id);
CREATE INDEX idx_complaints_staff       ON complaints(assigned_staff_id);
CREATE INDEX idx_status_history_complaint ON status_history(complaint_id);
