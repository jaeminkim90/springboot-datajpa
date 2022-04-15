package study.datajpa.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class) // @createdDate 사용을 위해 필요하다
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {

	@Id
	private String id;

	@CreatedDate // JPA 이벤트로 persist 후에 호출된다. isNew를 판단하는데 사용할 수 있다.
	private LocalDateTime createdDate;

	public Item(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean isNew() { // createdDate를 기준으로 새로운 엔티티 여부를 확인 할 수 있다.
		return createdDate == null;
	}
}
