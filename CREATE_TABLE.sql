
CREATE TABLE game_sales
(
    id int AUTO_INCREMENT PRIMARY KEY,
    game_no int NOT NULL,
    game_name VARCHAR(20) NOT NULL,
    game_code VARCHAR(5) NOT NULL,
    type INT NOT NULL,
    cost_price DECIMAL(5,2) NOT NULL,
    tax DECIMAL(5,2) NOT NULL,
    sale_price DECIMAL(5,2) NOT NULL,
    date_of_sale TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE import_log (
    id int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    total_records INT,
    status VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
