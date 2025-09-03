CREATE TABLE comprovante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lote_id INT NOT NULL,
    cpf_cnpj VARCHAR(14) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data DATE NOT NULL,
    operacao VARCHAR(20),
    status VARCHAR(20),
    total_bytes INT NOT NULL,
    linhas INT NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE imagem_comprovante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comprovante_id BIGINT NOT NULL,
    sequencia INT NOT NULL,
    base64_fragment TEXT NOT NULL,
    FOREIGN KEY (comprovante_id) REFERENCES comprovante(id)
);
