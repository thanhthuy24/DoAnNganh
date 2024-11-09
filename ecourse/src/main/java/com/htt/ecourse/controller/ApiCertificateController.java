package com.htt.ecourse.controller;

import com.htt.ecourse.components.PdfGeneratorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/certificate")
public class ApiCertificateController {
    private final PdfGeneratorService pdfGeneratorService;
    private static final Logger logger = LoggerFactory.getLogger(PdfGeneratorService.class);

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadCertificate(
            @RequestParam String courseName,
            @RequestParam String completionDate,
            @RequestParam String username) {
        byte[] pdfBytes = pdfGeneratorService.generateCertificate(courseName, completionDate, username);

        if (pdfBytes == null || pdfBytes.length == 0) {
            logger.error("Generated PDF is empty");
            throw new RuntimeException("Generated PDF is empty");
        }

        // Thiết lập response header để tải xuống PDF
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline ; filename=certificate.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
