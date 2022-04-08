package study.datajpa.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass // BaseEntity를 상속받으면 속성만 받아 쓸 수 있도록 하는 애노테이션. 진짜 상속은 따로 있다
public class JpaBaseEntity {

	// insertable = true가 default다
	@Column(updatable = false) // updatable을 false로 세팅하면 변경을 막을 수 있다 -> DB 값이 변경되지 않음
	private LocalDateTime createdDate;

	@Column
	private LocalDateTime updatedDate;

	@PrePersist // persist하기 전에 적용된다
	public void perPersist() { // 저장하기 전에 이벤트 발생. 최초 등록용으로 사용
		LocalDateTime now = LocalDateTime.now();
		createdDate = now;
		updatedDate = now;
	}

	@PreUpdate // update 전에 적용
	public void preUpdate() {
		updatedDate = LocalDateTime.now();
	}
}

