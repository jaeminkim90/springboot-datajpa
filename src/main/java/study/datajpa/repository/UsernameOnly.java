package study.datajpa.repository;

public interface UsernameOnly {

	// Projection: 조회할 엔티티의 필드를 Getter 형식으로 지정하면 해당필드만 선택해서 조회한다
	String getUsername();

}
