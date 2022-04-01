package study.datajpa.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String name;

	@OneToMany(mappedBy = "team") // 1:多 관계에서 mapped by는 FK가 없는 쪽에 걸어주는 것이 좋다
	private List<Member> members = new ArrayList<>();

	public Team(String name) {
		this.name = name;
	}
}
