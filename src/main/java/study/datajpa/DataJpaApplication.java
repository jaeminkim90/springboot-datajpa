package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing // Spring Data JPA를 이용하여 BaseEntity룰 사용할 경우 필요한 애노테이션
@SpringBootApplication
@EnableJpaRepositories(basePackages = "study.datajpa.repository") // 스프링 부트를 쓰면 없어도 된다
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}
}

