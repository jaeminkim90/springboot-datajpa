package study.datajpa.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 스펙은 기본 생성자를 필요로 한다
@ToString(of = {"id", "usename", "age"}) // 객체를 찍을 때 정보를 바로 출력한다
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id") // DB 테이블에 매핑하는 이름을 지정할 수 있다
	private Long Id;
	private String username;
	private int age;

	@ManyToOne(fetch = LAZY) // ToOne 관계는 기본 fetch 타입이 EAGER이므로, 반드시 LAZY로 변경. 즉시로딩은 성능 최적화가 어렵다.
	@JoinColumn(name = "team_id") // forign key 이름을 이용해 연관관계 매
	private Team team;

	// JPA는 기본 생성자가 필요하다
	// JPA 표준 스펙은 default 생성자가 하나 필요하다. private AccessLevel은 사용 불가하다.
	// 간단하게 NoArgsConstruct를 사용해도 된다
//	protected Member() {
//	}

	// 생성자 방식의 주입을 사용하면 setter를 대체할 수 있다
	public Member(String username) {
		this.username = username;
	}

	public Member(String username, int age2) {
		this.username = username;
		this.age = age2;
	}

	public Member(String username, int age2, Team team) {
		this.username = username;
		this.age = age2;
		if (team != null) {
			changeTeam(team); // 연관관계 세팅 메서드를 이용해 생성된 member와 team을 연결
		}
	}

	// 서로 연관관계를 세팅해주는 메서드가 필요하다
	// Team을 변경할 때 사용
	public void changeTeam(Team team) {
		this.team = team; // 파라미터로 들어온 team을 넣는다
		team.getMembers().add(this); // 변경된 팀에도 List(Members)에 현재의 Member 자기 자신을 추가한다


	}

	// 나중에 이름 변경이 필요하면 changeUsername() 같은 메서드를 만들어 사용한다
	public void changeUsername(String username) {
		this.username = username;
	}
}

