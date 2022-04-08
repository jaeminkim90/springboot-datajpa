package study.datajpa.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@EntityListeners(AuditingEntityListener.class) // Spring Data JPA 방식을 사용한다면 필요한 애노테이션이다
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity{

	@CreatedBy
	@Column(updatable = false) // 등록자는 수정되지 않도록 처리하는 것이 좋다
	private String createdBy;

	@LastModifiedBy
	private String lastModifiedBy;
}
