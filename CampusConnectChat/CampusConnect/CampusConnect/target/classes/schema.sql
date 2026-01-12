CREATE TABLE IF NOT EXISTS chat_message (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            item_id BIGINT NOT NULL,
                                            sender_email VARCHAR(255) NOT NULL,
    receiver_email VARCHAR(255),
    message TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );