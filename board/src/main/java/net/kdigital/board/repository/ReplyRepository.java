package net.kdigital.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kdigital.board.entity.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

}
