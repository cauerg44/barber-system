CREATE TABLE IF NOT EXISTS tb_appointment_services (
    appointment_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    PRIMARY KEY (appointment_id, service_id),
    FOREIGN KEY (appointment_id) REFERENCES tb_appointment(id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES tb_barber_services(id) ON DELETE CASCADE
);