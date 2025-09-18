package com.edu.ProjetoComprovante.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ComprovanteMontado {

    private String cpfCnpj;
    private BigDecimal valor;
    private String operacao;
    private String status;

    private int totalBytes;

    private String imagemBase64; // Imagem reconstruída a partir dos fragmentos

    private int identificadorLote;
    private LocalDate dataLote;
    private String nomeArquivo;

    public ComprovanteMontado(String cpfCnpj, BigDecimal valor, 
                              String operacao, String status, int totalBytes,
                              String imagemBase64, int identificadorLote,
                              LocalDate dataLote, String nomeArquivo) {
        this.cpfCnpj = cpfCnpj;
        this.valor = valor;
        this.operacao = operacao;
        this.status = status;
        this.totalBytes = totalBytes;
        this.imagemBase64 = imagemBase64;
        this.identificadorLote = identificadorLote;
        this.dataLote = dataLote;
        this.nomeArquivo = nomeArquivo;
    }

    // Getters e setters omitidos para brevidade
}
