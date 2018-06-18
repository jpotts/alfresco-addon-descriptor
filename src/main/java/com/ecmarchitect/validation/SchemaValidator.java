package com.ecmarchitect.validation;

import com.ecmarchitect.validation.service.ValidationService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by jpotts, Metaversant on 6/18/18.
 */
public class SchemaValidator {
    public static void main(String[] args) {
        if (args.length != 2) {
            doUsage();
            return;
        }

        ValidationService validationService = new ValidationService();

        String schemaPath = args[0];
        Path schemaFile = Paths.get(schemaPath);
        if (!Files.exists(schemaFile)) {
            System.out.println("Schema file does not exist: " + schemaPath);
        }

        String jsonPath = args[1];
        Path jsonFile = Paths.get(jsonPath);
        if (!Files.exists(jsonFile)) {
            System.out.println("JSON file does not exist: " + jsonPath);
        }

        validationService.validate(schemaFile, jsonFile);
    }

    private static void doUsage() {
        System.out.println("USAGE: java SchemaValidator [schema file path] [json file path]");
    }
}
