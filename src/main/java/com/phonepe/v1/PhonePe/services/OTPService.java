package com.phonepe.v1.PhonePe.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OTPService {

    @Value("${fast2sms.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public OTPService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendOtp(String phoneNumber, int otp) {
        // Fast2SMS API URL
        String url = "https://www.fast2sms.com/dev/bulkV2";

        // Prepare the request payload as application/x-www-form-urlencoded
        String requestBody = String.format(
                "variables_values=%d&route=otp&numbers=%s",
                otp, phoneNumber);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", apiKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create the HTTP request
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Send the request to Fast2SMS API
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return "OTP sent successfully: " + response.getBody();
            } else {
                return "Failed to send OTP. Response: " + response.getBody();
            }
        } catch (Exception e) {
            return "Error sending OTP: " + e.getMessage();
        }
    }
}
