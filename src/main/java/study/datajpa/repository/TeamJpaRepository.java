package study.datajpa.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Team;

@Repository // Component Scan을 위해 넣는다
public class TeamJpaRepository {

	@PersistenceContext // JPA의 EntityManager를 injection 해주는 Annotation
	private EntityManager em;

	public Team save(Team team) {
		em.persist(team); // 저장
		return team;
	}

	public void delete(Team team) {
		em.remove(team);
	}

	public List<Team> findAll() {
		List<Team> teams = em.createQuery("select t from Team t", Team.class).getResultList();
		return teams;
	}

	public Optional<Team> findById(Long id) {
		Team team = em.find(Team.class, id);
		return Optional.ofNullable(team);
	}

	public long count() {
		Long countTeam = em.createQuery("select count(t) from Team t", Long.class).getSingleResult();
		return countTeam;
	}
}
