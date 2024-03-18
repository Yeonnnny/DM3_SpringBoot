package net.kdigital.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.kdigital.board.entity.BoardEntity;
import net.kdigital.board.repository.BoardRepository;

@SpringBootTest
class BoardApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired 	//bin을 주입받기 위한 하나의 어노테이션
	private BoardRepository repository;

	@Test
	void testInsertBoard(){
		for (int i = 0; i < 11; i++) {
			BoardEntity b = new BoardEntity();

			b.setBoardWriter("있지");
			b.setBoardTitle("달라달라"+(i+1));
			b.setBoardContent("밥 좀 달라");

			repository.save(b);
		}
	}


}
