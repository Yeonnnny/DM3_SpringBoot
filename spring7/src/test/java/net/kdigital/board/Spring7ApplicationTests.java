package net.kdigital.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.kdigital.board.entity.BoardEntity;
import net.kdigital.board.repository.BoardRepository;

@SpringBootTest
class Spring7ApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private BoardRepository repository;
	
	@Test
	void testInsertBoard() {
		for(int i=0; i<126; ++i) {
			BoardEntity b = new BoardEntity();
			
			b.setBoardWriter("디마 3기 학생");
			b.setBoardTitle("페이징을 연습해 보아요"+(i+1));
			b.setBoardContent("글을 넣고 있습니다.");
			
			repository.save(b);
		}
	}

}
