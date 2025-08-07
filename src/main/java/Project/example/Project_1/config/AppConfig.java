package Project.example.Project_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Enable logging request/response (nếu muốn)
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            System.out.println("Request URI: " + request.getURI());
            System.out.println("Request method: " + request.getMethod());
            System.out.println("Request headers: " + request.getHeaders());
            System.out.println("Request body: " + new String(body, StandardCharsets.UTF_8));

            ClientHttpResponse response = execution.execute(request, body);

            System.out.println("Response status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());

            return response;
        };

        restTemplate.getInterceptors().add(interceptor);

        return restTemplate;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
