package study.datajpa.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id") // DB 테이블에 매핑하는 이름을 지정할 수 있다
	private Long Id;
	private String username;
	private int age;

	@ManyToOne
	@JoinColumn(name = "team_id") // forign key 이름을 이용해 연관관계 매
	private Team team;

	// JPA는 기본 생성자가 필요하다
	// JPA 표준 스펙은 default 생성자가 하나 필요하다
	protected Member() {
	}

	// 생성자 방식의 주입은 setter를 없앨 수 있다
	public Member(String username) {
		this.username = username;
	}

	// 나중에 이름 변경이 필요하면 changeUsername() 같은 메서드를 만들어 사용한다
	public void changeUsername(String username) {
		this.username = username;
	}
}
