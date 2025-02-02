package com.example.springvector.springvector1;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


import java.util.List;

@SpringBootApplication
public class Springvector1Application {

    @Autowired
    public VectorStore vectorStore;  // No static here, let Spring manage it

    public static void main(String[] args) {
        // Run the Spring Boot application
        SpringApplication.run(Springvector1Application.class, args);
    }

    @jakarta.annotation.PostConstruct
    public void init() {

        // This method will run after the application context is fully initialized
        
        // Create a document list
        List<Document> doc = List.of(
            new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!"),
			new Document(" the best and the widely used language is java"),
			new Document("Paris is the capital of france")
        );
        
        // Add documents to the VectorStore
        vectorStore.add(doc);
        
        // Perform a similarity search
    }
}
