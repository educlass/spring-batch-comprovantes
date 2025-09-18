package com.edu.ProjetoComprovante.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;

import com.edu.ProjetoComprovante.dto.ComprovanteMontado;

public class ComprovanteReader implements ItemReader<ComprovanteMontado>{
	
	private BufferedReader reader;
    private String nomeArquivo;

    private int loteAtual;
    private LocalDate dataLote;

    private boolean fimDoArquivo = false;
	
	public ComprovanteReader(Resource resource) {
		
		try {
            this.reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            this.nomeArquivo = resource.getFilename();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao abrir o arquivo: " + resource.getFilename(), e);
        }
		
	}
	
	@Override
    public ComprovanteMontado read() {
        if (fimDoArquivo) return null;

        try {
            String linha;
            while ((linha = reader.readLine()) != null) {
                char tipo = linha.charAt(0);

                switch (tipo) {
                    case '0': // Header — ignorar
                        continue;

                    case '1': // Início de lote
                        loteAtual = Integer.parseInt(linha.substring(9, 19));
                        dataLote = parseData(linha.substring(1, 9));
                        continue;

                    case '2': // Comprovante
                        
                    	ComprovanteMontado comp = montarComprovante(linha);
                    	StringBuilder sb  = new StringBuilder();
                    	sb.append(comp.getImagemBase64());
                    	
                    	while(comp.getTotalBytes() > sb.length()) {
                    		
                    		String linhaDetalhe = reader.readLine();
                    		ComprovanteMontado compInterno = montarComprovante(linhaDetalhe);
                    		
                    		sb.append(compInterno.getImagemBase64());
                    		
                    	}
                    	
                    	comp.setImagemBase64(sb.toString());
                    	return comp;

                    case '8': // Fim de lote
                        loteAtual = 0;
                        dataLote = null;
                        continue;

                    case '9': // Trailer
                        fimDoArquivo = true;
                        return null;

                    default:
                        continue;
                }
            }

            fimDoArquivo = true;
            return null;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo: " + nomeArquivo, e);
        }
    }

    private LocalDate parseData(String ddMMyyyy) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        return LocalDate.parse(ddMMyyyy, formatter);
    }

    private ComprovanteMontado montarComprovante(String linha) {
        String dadosLogicos = linha.substring(0, 49);
        String imagemBase64 = linha.substring(49).trim();

        String cpfCnpj = dadosLogicos.substring(1, 15);
        BigDecimal valor = new BigDecimal(dadosLogicos.substring(15, 25)).movePointLeft(2);
        String operacao = dadosLogicos.substring(33, 41).trim();
        String status = dadosLogicos.substring(41).trim();
        int totalBytes = Integer.valueOf(dadosLogicos.substring(25, 33));

        return new ComprovanteMontado(
            cpfCnpj,
            valor,
            operacao,
            status,
            totalBytes,
            imagemBase64,
            loteAtual,
            dataLote,
            nomeArquivo
        );
    }

}
