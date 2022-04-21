package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

	// Projection: 조회할 엔티티의 필드를 Getter 형식으로 지정하면 해당필드만 선택해서 조회한다
	// @Value를 사용하면 EL 문법을 그대로 사용할 수 있다 -> 내용을 채워서 문자열로 만들어준다
	// 하지만 이랗게 SpEL 문법을 사용하면 DB에서 엔티티 필드를 다 조회해온 다음에 계산한다. 따라서 JPQL SELECT 절 최적화가 안된다.
	@Value("#{target.username + ' ' + target.age}")
	String getUsername();

}
