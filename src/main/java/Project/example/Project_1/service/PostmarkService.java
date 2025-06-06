package Project.example.Project_1.service;

import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@Service
public class PostmarkService {
    private static final Logger log = (Logger) LoggerFactory.getLogger(PostmarkService.class);
    @Value("${postmark.api.key}")
    private String apiKey;

    @Value("${postmark.from.address}")
    private String fromAddress;


    private final RestTemplate restTemplate;

    public PostmarkService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void sendForgotPassword(String email, String username,String otp) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("otpCode", otp);
        templateModel.put("userName", username);
        String templateId = "39218161";

        sendEmailWithOTP(email, templateId, templateModel);

    }

    public void sendVerificationEmailWithOTP(String email, String username, String otp) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("userName", username);
        templateModel.put("otpCode", otp);
        String templateId = "39217777";
        sendEmailWithOTP(email, templateId, templateModel);
    }


    public void sendEmailWithOTP(String toAddress, String templateId, Map<String, Object> templateModel) {
        String url = "https://api.postmarkapp.com/email/withTemplate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("X-Postmark-Server-Token", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> emailPayload = new HashMap<>();
        emailPayload.put("From", fromAddress);
        emailPayload.put("To", toAddress);
        emailPayload.put("TemplateId", Integer.parseInt(templateId));
        emailPayload.put("MessageStream", "outbound");
        emailPayload.put("TemplateModel", templateModel);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(emailPayload, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
            } else {
                throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }
}
