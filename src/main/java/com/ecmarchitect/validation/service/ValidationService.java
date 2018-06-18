package com.ecmarchitect.validation.service;
import com.ecmarchitect.validation.exceptions.ValidationServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class ValidationService {
    private static final Logger logger = LogManager.getLogger(ValidationService.class);

    private ObjectMapper mapper = new ObjectMapper();

    public void validate(Path schemaFile, Path jsonFile) {

        // read schema
        String schema;
        try {
            schema = new String(Files.readAllBytes(schemaFile));
            logger.debug("schema: " + schema);
        } catch (IOException ioe) {
            throw new ValidationServiceException("Problem reading schema: " + ioe.getMessage());
        }

        // read json
        String json;
        try {
            json = new String(Files.readAllBytes(jsonFile));
            logger.debug("json: " + json);
        } catch (IOException ioe) {
            throw new ValidationServiceException("Problem reading json: " + ioe.getMessage());
        }

        ProcessingReport report;
        try {
            report = validate(schema, json);
        } catch (ProcessingException | IOException e) {
            throw new ValidationServiceException(e.getMessage());
        }

        if (report == null) {
            throw new ValidationServiceException("Content validation report came back null");
        } else if (!report.isSuccess()) {
            Iterator<ProcessingMessage> iter = report.iterator();
            StringBuffer sb = new StringBuffer();
            while (iter.hasNext()) {
                ProcessingMessage pm = iter.next();
                if (pm.getLogLevel().equals(LogLevel.ERROR)) {
                    sb.append(pm.getMessage());
                    sb.append(";");
                }
            }
            throw new ValidationServiceException(sb.toString());
        }
    }

    private ProcessingReport validate(String schemaJson, String json) throws ProcessingException, IOException {
        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonNode jsonSchemaObject = mapper.readTree(schemaJson);
        JsonSchema schema = factory.getJsonSchema(jsonSchemaObject);
        JsonNode jsonObject = mapper.readTree(json);
        return schema.validate(jsonObject);
    }
}
