package com.filimonov.recipe.website_1.services;

public interface FileService {

    boolean saveToFile(String json, String fileName);

    String readFromFile(String fileName);
}
