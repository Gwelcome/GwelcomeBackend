package backend.Gwelcome.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Gwelcome",
                version = "0.0",
                description = "청년들을 위한 복지 서비스 Gwelcome"
        )
)
@Configuration
public class SwaggerConfig {
}