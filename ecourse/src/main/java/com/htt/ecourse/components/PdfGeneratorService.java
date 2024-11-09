package com.htt.ecourse.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfGeneratorService {
    @Value("${pdfgeneratorapi.api_key}")
    private String apiKey;

    @Value("${pdfgeneratorapi.base_url}")
    private String baseUrl;

    @Value("${pdfgeneratorapi.template_id}")
    private String templateId;

    @Value("${pdfgeneratorapi.workspace}")
    private String workspace;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(PdfGeneratorService.class);

    public byte[] generateCertificate(String courseName, String completionDate, String username) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_PDF));
//            headers.set("X-Api-Key", apiKey);
            headers.set("X-Workspace", workspace);
            headers.set("Authorization", "Bearer " +
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIyZWQwMGU2YWVmZTkyYmNkMjU0ZDc1ODZiZDVhYjNhNTJlOGQyZWQxNzY4ODdlODg0YWVhZTU4Mzc4OWFmZTBhIiwic3ViIjoiaG90aGFuaHRodXkxMS4yMDAzQGdtYWlsLmNvbSIsImV4cCI6MTcyOTk5ODA2Nn0.8hHf1Iz_JJjxe0XGeJnx6Fc-xNtQlQAw0AMsk-2WqKY");

            Map<String, Object> bodyData = new HashMap<>();
            bodyData.put("Name", courseName);
            bodyData.put("issuanceDate", completionDate);
            bodyData.put("Username", username);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(bodyData, headers);

            String url = baseUrl + "/templates/" + templateId + "/output?format=pdf";

        try {
            byte[] pdfData = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class).getBody();
            return pdfData;
        } catch (HttpClientErrorException e) {
            // Ghi log lỗi để xem lý do
            logger.error("HTTP Status: " + e.getStatusCode() + ", Message: " + e.getResponseBodyAsString());
            throw new RuntimeException("Error generating PDF: Unauthorized (401)", e);
        }
    }
}
