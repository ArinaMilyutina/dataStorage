CREATE TABLE documents (
                           id SERIAL PRIMARY KEY,
                           client_id BIGINT UNIQUE NOT NULL,
                           document_type VARCHAR(50) NOT NULL,
                           passport_file BYTEA,
                           application_form BYTEA,
                           comments TEXT,
                           CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);