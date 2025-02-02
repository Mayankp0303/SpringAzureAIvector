package com.example.springvector.springvector1.config;

import java.util.Collections;
import java.util.List;

import org.springframework.ai.azure.openai.AzureOpenAiEmbeddingModel;
import org.springframework.ai.azure.openai.AzureOpenAiEmbeddingOptions;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.azure.AzureVectorStore;
import org.springframework.ai.vectorstore.azure.AzureVectorStore.MetadataField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.search.documents.indexes.SearchIndexClient;
import com.azure.search.documents.indexes.SearchIndexClientBuilder;

@Configuration
public class BeanConfig {
    
    @Value("${spring.ai.vectorstore.azure.url}")
    private String azureSearcEndPoint;

    @Value("${spring.ai.vectorstore.azure.api-key}")
    private String azureApiKey;
    
    @Bean
    public SearchIndexClient searchIndexClient() {
        return new SearchIndexClientBuilder().endpoint(azureSearcEndPoint)
        .credential(new AzureKeyCredential(azureApiKey))
        .buildClient();
    }


    @Bean
    public VectorStore vectorStore(SearchIndexClient searchIndexClient,EmbeddingModel embeddingModel){

        return AzureVectorStore.builder(searchIndexClient, embeddingModel).initializeSchema(true).filterMetadataFields(List.of(MetadataField.text("country"), MetadataField.int64("year"),
            MetadataField.date("activationDate"))).defaultTopK(3).defaultSimilarityThreshold(0.3).indexName("springaiindexdocumen").build();
    }
     
}
