package porori.backend.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CommunityApplication {
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true"); //true 안하면 예외 메세지 발생
	}

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
