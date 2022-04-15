package study.datajpa.dto;

import lombok.Data;
import study.datajpa.entity.Member;

@Data
public class MemberDto {

	private Long id;
	private String username;
	private String teamname;

	public MemberDto(Long id, String username, String teamname) {
		this.id = id;
		this.username = username;
		this.teamname = teamname;
	}

	// Member를 인자로 바로 받아서 필드를 채워줄 수도 있다
	public MemberDto(Member member) {
		this.id = member.getId();
		this.username = member.getUsername();
	}
}
