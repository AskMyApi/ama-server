CREATE USER IF NOT EXISTS 'application'@'%' IDENTIFIED BY 'application';
GRANT ALL PRIVILEGES ON ama.* TO 'application'@'%';
FLUSH PRIVILEGES;