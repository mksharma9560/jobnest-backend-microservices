CREATE TABLE IF NOT EXISTS company_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    industry VARCHAR(25) NOT NULL,
    location VARCHAR(25) NOT NULL,
    average_Rating DOUBLE NOT NULL
);