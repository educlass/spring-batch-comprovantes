CREATE TABLE arquivo_processado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_arquivo VARCHAR(255) NOT NULL,
    data_processamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    quantidade_lotes INT NOT NULL,
    quantidade_comprovantes INT NOT NULL
);


CREATE TABLE lote_processado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    arquivo_id BIGINT NOT NULL,
    identificador_lote INT NOT NULL,
    data_lote DATE NOT NULL,
    quantidade_comprovantes INT NOT NULL,
    FOREIGN KEY (arquivo_id) REFERENCES arquivo_processado(id)
);

CREATE TABLE comprovante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lote_id BIGINT NOT NULL,
    cpf_cnpj VARCHAR(14) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data DATE NOT NULL,
    operacao VARCHAR(20),
    status VARCHAR(20),
    total_bytes INT NOT NULL,
    linhas INT NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (lote_id) REFERENCES lote_processado(id)
);


CREATE TABLE imagem_comprovante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comprovante_id BIGINT NOT NULL,
    sequencia INT NOT NULL,
    base64_fragment TEXT NOT NULL,
    FOREIGN KEY (comprovante_id) REFERENCES comprovante(id)
);

