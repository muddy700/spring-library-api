package com.kalambo.libraryapi.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        // My contact information
        Contact myContact = new Contact();
        myContact.setName("Mohamed Mfaume");
        myContact.setEmail("mohamedmfaume700@gmail.com");
        myContact.setUrl("https://my-profile-website.com");

        // Servers information
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8000");
        localServer.setDescription("Server URL in local environment");

        Server productionServer = new Server();
        productionServer.setUrl("https://library-api.com");
        productionServer.setDescription("Server URL in production environment");

        // License information
        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        // Combine all iformation
        Info information = new Info()
                .title("Library API")
                .version("1.0")
                .contact(myContact)
                .description("This API exposes endpoints for library management information system.")
                .license(mitLicense)
                .termsOfService("https://my-awesome-api.com/terms");

        return new OpenAPI().info(information).servers(List.of(localServer, productionServer));
    }
}
