package in.experience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	    info = @Info(
	        title = "Product API Documentation",
	        version = "1.0.0",
	        description = "Created by [Anubhav Srivastava]",
	        contact = @Contact(name = "Anubhav", email = "anubhav.aa2@gmail.com", url = "http://fakedevelopers.online")
	    )
	)
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
