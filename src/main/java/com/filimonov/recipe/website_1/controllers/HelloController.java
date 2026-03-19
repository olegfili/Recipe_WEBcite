package com.filimonov.recipe.website_1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String info() {
        return """
            <h1>Информация о проекте</h1>
            <ul>
                <li><strong>Имя ученика:</strong> Олег Филимонов </li>
                <li><strong>Название проекта:</strong> Recipe Website </li>
                <li><strong>Дата создания:</strong> 11.11.25 </li>
                <li><strong>Описание:</strong> Веб-приложение для рецептов </li>
                <li><strong>Технологии:</strong> Java, Spring Boot, Maven, HTML/CSS</li>
                <li><strong>Язык программирования:</strong> Java</li>
            </ul>
            """.formatted(LocalDate.now());
    }
}