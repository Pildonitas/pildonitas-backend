INSERT INTO users (id, username, name, email, password, allergies, role) VALUES
(1, 'maria.garcia', 'María García', 'maria.garcia@email.com', '$2a$12$C8CFsIqDYxhkJloBvWXetOGN..TN7gD1V.VK152fNZ9gPp5IGRXZ2', 'Penicilina, Mariscos', 'USER'),
(2, 'juan.lopez', 'Juan López', 'juan.lopez@email.com', '$2a$12$0LgZWL7WunX05CiJKsRCkO2WSwAbvBmuEpF68DpfhKb48Ak1RGvw2', 'Aspirina, Frutos secos', 'USER'),
(3, 'ana.martinez', 'Ana Martínez', 'ana.martinez@email.com', '$2a$12$E6psnsyisvurSUzHUZQJaeET7tyWj1Tt5UPIUDo8UVQ4xQD/ybCnW', 'Lactosa, Polen', 'USER');

INSERT INTO medications (id, user_id, medication_name, description, dosage, allergies, frequency, status) VALUES
(1, 1, 'Paracetamol 500mg', 'Analgésico y antipirético', '1 comprimido', 'Ninguna', 'Cada 8 horas', 'PENDING'),
(2, 1, 'Ibuprofeno 400mg', 'Antiinflamatorio', '1 comprimido', 'Aspirina', 'Cada 12 horas', 'TAKEN'),
(3, 1, 'Loratadina 10mg', 'Antihistamínico para alergias', '1 comprimido', 'Ninguna', 'Una vez al día', 'PENDING'),
(4, 1, 'Omeprazol 20mg', 'Protector gástrico', '1 cápsula', 'Ninguna', 'Una vez al día', 'TAKEN');

INSERT INTO medications (id, user_id, medication_name, description, dosage, allergies, frequency, status) VALUES
(5, 2, 'Amoxicilina 500mg', 'Antibiótico', '1 comprimido', 'Penicilina', 'Cada 12 horas', 'PENDING'),
(6, 2, 'Metformina 850mg', 'Para diabetes tipo 2', '1 comprimido', 'Ninguna', 'Cada 8 horas', 'TAKEN'),
(7, 2, 'Atorvastatina 20mg', 'Para el colesterol', '1 comprimido', 'Ninguna', 'Una vez al día', 'PENDING'),
(8, 2, 'Aspirina 100mg', 'Anticoagulante', '1 comprimido', 'Aspirina', 'Una vez al día', 'DELAYED');

INSERT INTO medications (id, user_id, medication_name, description, dosage, allergies, frequency, status) VALUES
(9, 3, 'Levotiroxina 50mcg', 'Para hipotiroidismo', '1 comprimido', 'Ninguna', 'Una vez al día', 'TAKEN'),
(10, 3, 'Salbutamol inhalador', 'Para el asma', '2 puff', 'Ninguna', 'Cada 6 horas','PENDING'),
(11, 3, 'Vitamina D 1000UI', 'Suplemento vitamínico','1 cápsula', 'Pescado', 'Una vez al día', 'PENDING'),
(12, 3, 'Diazepam 5mg', 'Ansiolítico', '1 comprimido', 'Ninguna', 'Cuando sea necesario', 'TAKEN');

INSERT INTO intakes (id, medication_id, intake_time, status, taken_at) VALUES
(1, 1, '2025-09-17 08:00:00', 'PENDING', NULL),
(2, 1, '2025-09-17 16:00:00', 'PENDING', NULL),
(3, 2, '2025-09-17 12:00:00', 'TAKEN', '2025-09-17 12:05:00'),
(4, 3, '2025-09-17 09:00:00', 'PENDING', NULL),
(5, 4, '2025-09-17 07:30:00', 'TAKEN', '2025-09-17 07:35:00'),

(6, 5, '2025-09-17 09:00:00', 'PENDING', NULL),
(7, 6, '2025-09-17 08:00:00', 'TAKEN', '2025-09-17 08:10:00'),
(8, 7, '2025-09-17 22:00:00', 'PENDING', NULL),
(9, 8, '2025-09-17 08:00:00', 'DELAYED', NULL),

(10, 9, '2025-09-17 07:00:00', 'TAKEN', '2025-09-17 07:05:00'),
(11, 10, '2025-09-17 08:00:00', 'PENDING', NULL),
(12, 11, '2025-09-17 13:00:00', 'PENDING', NULL),
(13, 12, '2025-09-17 21:00:00', 'TAKEN', '2025-09-17 21:30:00');

ALTER TABLE users AUTO_INCREMENT = 4;
ALTER TABLE medications AUTO_INCREMENT = 13;
ALTER TABLE intakes AUTO_INCREMENT = 14;