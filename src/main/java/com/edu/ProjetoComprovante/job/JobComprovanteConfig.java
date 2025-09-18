package com.edu.ProjetoComprovante.job;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.edu.ProjetoComprovante.dto.ComprovanteMontado;
import com.edu.ProjetoComprovante.dto.LinhaArquivo;
import com.edu.ProjetoComprovante.reader.ComprovanteReader;

@Configuration
public class JobComprovanteConfig {
	
	@Bean
    Job comprovanteJob(JobRepository jobRepository, Step leituraArquivoStep) {
        return new JobBuilder("comprovanteJob", jobRepository)
            .start(leituraArquivoStep)
            .incrementer(new RunIdIncrementer())
            .build();
    }
	
	@Bean
    Step leituraComprovanteStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ItemReader<ComprovanteMontado> comprovanteReader) {
		
        return new StepBuilder("leituraArquivoStep", jobRepository)
            .<ComprovanteMontado, ComprovanteMontado>chunk(1, transactionManager)
            .reader(comprovanteReader)
            .processor(processor())
            .writer(writer())
            .build();
    }

	private ItemWriter<? super ComprovanteMontado> writer() {
		
		return item ->{
			System.out.println(item);
		};
	}

	private ItemProcessor<? super ComprovanteMontado, ? extends ComprovanteMontado> processor() {
		
		return item ->{
			
			System.out.println(item);
			
			// Simulação de montagem do comprovante
//	        ComprovanteMontado comprovante = new ComprovanteMontado(
//	            "12345678901234",                      // CPF/CNPJ
//	            new BigDecimal("100.00"),              // Valor
//	            LocalDate.of(2025, 9, 2),              // Data
//	            "TRANSFER",                            // Operação
//	            "OK",                                  // Status
//	            60,                                    // Total de bytes
//	            1,                                     // Linhas
//	            "U29tZSBpbWFnZSBkYXRhIGVuY29kZWQgaW4gYmFzZTY0", // Imagem base64
//	            1,                                     // ID do lote
//	            LocalDate.of(2025, 9, 2),              // Data do lote
//	            "lancamento_bancario_001.txt"          // Nome do arquivo
//	        );

	        return item;
		};
	}

	@Bean
	ItemReader<ComprovanteMontado> comprovanteReader(){
		FileSystemResource fileSystemResource = new FileSystemResource("/home/edu/Documentos/Desenvolvimento/Java/workspaces/spring-batch-comprovantes/files/COMP_02_02092025.txt");
		return new ComprovanteReader(fileSystemResource);
	}

}
