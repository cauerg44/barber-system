CREATE TABLE IF NOT EXISTS tb_appointment (
    id SERIAL PRIMARY KEY,
    client_name VARCHAR(50) NOT NULL,
    barber_id BIGINT NOT NULL,
    payment_id INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AGUARDANDO',
    total_value NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (barber_id) REFERENCES tb_barbers(id),
    FOREIGN KEY (payment_id) REFERENCES tb_payment(id),
    CONSTRAINT status_check CHECK (status IN ('AGUARDANDO', 'CORTANDO', 'FINALIZADO'))
);