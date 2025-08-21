INSERT INTO indexer (name, valueMin, valueMax) VALUES
('speed', 10, 100),
('strength', 0, 10),
('accurate', 0, 1)
ON DUPLICATE KEY UPDATE name=VALUES(name), valueMin=VALUES(valueMin), valueMax=VALUES(valueMax);
