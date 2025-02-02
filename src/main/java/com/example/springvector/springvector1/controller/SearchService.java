package com.example.springvector.springvector1.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
public class SearchService {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private EmbeddingModel embeddingModel;

    // Similarity search endpoint
    @GetMapping("/getSearch")
    public List<Document> getMethodName() {
        return vectorStore.similaritySearch("Which is best language");
    }

    // Test endpoint
    @GetMapping("/test")
    public String requestMethodName() {
        float arr[] =embeddingModel.embed("tell me a joke");

        StringBuilder embeddingString = new StringBuilder("Embedding: ");
            for (float value : arr) {
                embeddingString.append(value).append(" ");
            }

            // Return the embedding values as a response
            return embeddingString.toString();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String question) {
        try {
            //String question= "Which is the most used ar";
            // Step 1: Embed the question
            float[] questionEmbedding = embeddingModel.embed(question);

            // Step 2: Perform similarity search to find the most relevant document
            List<Document> results = vectorStore.similaritySearch(question);  // Retrieve top 3 most similar documents

            // Step 3: Return the content of the most relevant document
            if (!results.isEmpty()) {
                return "Answer: " + results.get(0).getContent();  // Returning the most relevant document's content
            } else {
                return "Sorry, no relevant documents found.";
            }

        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }
}
