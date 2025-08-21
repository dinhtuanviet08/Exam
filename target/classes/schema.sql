-- Schema from exam
CREATE DATABASE IF NOT EXISTS player_evaluation;
USE player_evaluation;

CREATE TABLE IF NOT EXISTS indexer (
    index_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    valueMin FLOAT NOT NULL,
    valueMax FLOAT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS player (
    player_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    full_name VARCHAR(128) NOT NULL,
    age VARCHAR(10) NOT NULL,
    index_id INT NOT NULL,
    CONSTRAINT fk_player_indexer FOREIGN KEY (index_id) REFERENCES indexer(index_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS player_index (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT NOT NULL,
    index_id INT NOT NULL,
    value FLOAT NOT NULL,
    CONSTRAINT fk_pi_player FOREIGN KEY (player_id) REFERENCES player(player_id) ON DELETE CASCADE,
    CONSTRAINT fk_pi_indexer FOREIGN KEY (index_id) REFERENCES indexer(index_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
