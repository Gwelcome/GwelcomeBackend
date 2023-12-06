package backend.Gwelcome.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Gwelcome",
                version = "0.0",
                description = "청년들을 위한 복지 서비스 Gwelcome"
        ),
        servers = {@Server(url = "/", description = "any description of Server URL")}
)
@Configuration
public class SwaggerConfig {
}