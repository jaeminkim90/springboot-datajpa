package study.datajpa.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Item;

@SpringBootTest
class ItemRepositoryTest {

	@Autowired
	ItemRepository itemRepository;


	@Test
	public void save() {
		Item item = new Item("A");
		itemRepository.save(item);
		// Item의 Id를 따로 넣지 않아도 JPA persist가 완료되면 그 안에서 들어간다.
		// 만약 Id를 임의로 넣어주면 DB에 있는 데이터라고 생각해서 바로 select 쿼리가 나간다.
		// 그때 데이터가 없을 경우 다시 insert 쿼리가 나간다.

	}
}
