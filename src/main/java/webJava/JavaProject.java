package webJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "webJava")
public class JavaProject {

	public static void main(String[] args) {
		SpringApplication.run(JavaProject.class, args);
	}

}
