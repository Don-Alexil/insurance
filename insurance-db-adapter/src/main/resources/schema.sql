CREATE DATABASE IF NOT EXISTS insurances;

ALTER DATABASE insurances
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

USE insurances;

CREATE TABLE IF NOT EXISTS insurance_policy (
    id INT AUTO_INCREMENT PRIMARY KEY,                 -- Policy identifier, Auto-generated, Mandatory, Unique, Not null
    policy_name VARCHAR(255) NOT NULL,                 -- Policy name, String, Not null, Cannot be empty
    policy_status ENUM('ACTIVE', 'INACTIVE') NOT NULL, -- Policy status, Enum, Not null
    start_date DATE NOT NULL,                          -- Coverage start date, Date, Mandatory, Not null
    end_date DATE NOT NULL,                            -- Coverage end date, Date, Mandatory, Not null
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Creation date, Auto-generated, Mandatory, Not null
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- Last update date, Auto-generated, Mandatory, Not null
) engine=Innodb;



