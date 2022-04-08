package study.datajpa;

import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//(modifyOnCreate = false)을 추가로 설정하면 업데이트는 null이 들어간다
@EnableJpaAuditing // Spring Data JPA를 이용하여 BaseEntity룰 사용할 경우 필요한 애노테이션
@SpringBootApplication
@EnableJpaRepositories(basePackages = "study.datajpa.repository") // 스프링 부트를 쓰면 없어도 된다
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());

//		return new AuditorAware<String>() {
//
//			// interface에서 메서드가 하나이면 람다로 변경 가능
//			@Override
//			public Optional<String> getCurrentAuditor() {
//				return Optional.of(UUID.randomUUID().toString());
//			}
//		};
	}
}

