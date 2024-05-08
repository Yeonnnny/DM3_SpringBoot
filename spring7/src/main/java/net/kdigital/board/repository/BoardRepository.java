package net.kdigital.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import net.kdigital.board.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
	
	// 검색기능이 있는 메소드 선언
	Page<BoardEntity> findByBoardTitleContaining  (String searchWord, PageRequest pageRequest);
	Page<BoardEntity> findByBoardWriterContaining (String searchWord, PageRequest pageRequest);
	Page<BoardEntity> findByBoardContentContaining(String searchWord, PageRequest pageRequest);
}
