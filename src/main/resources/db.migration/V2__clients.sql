CREATE TABLE clients (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         surname VARCHAR(255),
                         name VARCHAR(255),
                         date_of_birth VARCHAR(255),
                         gender VARCHAR(50),
                         citizenship VARCHAR(255),
                         passport_series VARCHAR(50),
                         passport_number VARCHAR(50),
                         passport_start_date VARCHAR(255),
                         passport_end_date VARCHAR(255),
                         email VARCHAR(255),
                         number VARCHAR(50),
                         user_id BIGINT,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);