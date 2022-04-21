package study.datajpa.repository;

public class UsernameOnlyDto {

	private final String username;

	// 생성자를 이용하면 파라미터 데이터를 이용하여 매칭이 가능하다
	// 파라미터 이름으로 매칭된다
	public UsernameOnlyDto(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
