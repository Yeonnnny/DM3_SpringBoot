package net.kdigital.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kdigital.board.entity.BoardEntity;
import net.kdigital.board.entity.ReplyEntity; 

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

	List<ReplyEntity> findAllByBoardEntityOrderByReplyNumDesc(BoardEntity entity);

}
