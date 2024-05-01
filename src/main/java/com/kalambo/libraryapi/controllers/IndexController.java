package com.kalambo.libraryapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalambo.libraryapi.responses.IAuthor;
import com.kalambo.libraryapi.responses.IHome;
import com.kalambo.libraryapi.responses.IProject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping
@Tag(name = "Index", description = "General api details.")

public class IndexController {
    @GetMapping
    @Operation(summary = "Retrieve general api info.", description = "Some description.")
    public IHome getAPIDetails() {
        log.info("GET - / :: Index page");

        IAuthor authorInfo = new IAuthor("Mohamed Mfaume Mohamed", "mohamedmfaume700@gmail.com");
        IProject projectInfo = new IProject("Library API",
                "This is a RESTful MVP API for library management systems, built on top of Spring Boot.");

        return new IHome(projectInfo, authorInfo, "To see documentation, add /swagger in the url.");
    }
}
