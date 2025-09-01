package com.ffucks.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieAssistantController {

    private final ChatClient chatClient;

    public MovieAssistantController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/informations")
    public String getMovieInformations(@RequestParam(value = "message", defaultValue = "What is the best movies of last year?") String message) {

        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/informations-chat-response")
    public ChatResponse getMovieInformationsChatResponse(@RequestParam(value = "message", defaultValue = "What is the best movies of last year?") String message) {

        return chatClient
                .prompt()
                .user(message)
                .call()
                .chatResponse();
    }

    @GetMapping("/review")
    public String getMovieReview(@RequestParam(value = "message", defaultValue = "Club Fight") String movie) {
        PromptTemplate tmpl = new PromptTemplate(
                "Please provide a detailed review of the movie {movie} and the director");
        tmpl.add("movie", movie);

        Prompt prompt = tmpl.create();
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

}
