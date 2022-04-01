package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	// 이렇게만 만들어 놓으면 Spring Data JPA가 구현체를 만들어서 Injection한다. 개발자가 직접 구현체를 만들 필요가 없다.

}
