package com.edu.ProjetoComprovante.dto;

import lombok.Getter;

@Getter
public class LinhaArquivo {

    private char tipo;         // Ex: '0', '1', '2', '8', '9'
    private String conteudo;   // Linha completa de 100 caracteres

    public LinhaArquivo(char tipo, String conteudo) {
        this.tipo = tipo;
        this.conteudo = conteudo;
    }

}
