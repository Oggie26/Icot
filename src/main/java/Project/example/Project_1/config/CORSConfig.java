package Project.example.Project_1.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CORSConfig implements WebMvcConfigurer {
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/**")
    //             .allowedOrigins("*")
    //             .allowedHeaders("*")
    //         //    .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Methods","Access-Control-Allow-Headers")
    //             .allowedMethods("*")
    //             .maxAge(1440000);
    // }
    @Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("https://icot.vercel.app") // KHÔNG dùng "*"
            .allowedHeaders("*")
            .allowedMethods("*")
            .exposedHeaders("Authorization")
            .allowCredentials(true) // Bắt buộc khi gửi Authorization
            .maxAge(3600);
}
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Phục vụ các file trong src/main/resources/static
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        // Phục vụ file ảnh đã upload vào thư mục bên ngoài
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

}
