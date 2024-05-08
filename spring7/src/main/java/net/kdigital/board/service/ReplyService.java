package net.kdigital.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.ReplyDTO;
import net.kdigital.board.entity.BoardEntity;
import net.kdigital.board.entity.ReplyEntity;
import net.kdigital.board.repository.BoardRepository;
import net.kdigital.board.repository.ReplyRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyService {
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository; 
	
	public ReplyDTO replyInsert(ReplyDTO replyDTO) {
		// 댓글의 부모인 게시글이 존재하는지 확인!
		Optional<BoardEntity> boardEntity =  boardRepository.findById(replyDTO.getBoardNum());
	
		if(boardEntity.isPresent()) {
			BoardEntity entity = boardEntity.get();
			ReplyEntity replyEntity = ReplyEntity.toEntity(replyDTO, entity);
			
			ReplyEntity temp =replyRepository.save(replyEntity);
			
			return ReplyDTO.toDTO(temp, replyDTO.getBoardNum());
		} else {
			return null;
		}
	}
	
	/**
	 * 댓글 전체 목록 반환
	 * @param boardNum
	 * @return
	 */
	public List<ReplyDTO> replyAll(Long boardNum) {
		log.info("{}", boardNum);
		BoardEntity entity = boardRepository.findById(boardNum).get();
		List<ReplyEntity> replyEntityList 
			= replyRepository.findAllByBoardEntityOrderByReplyNumDesc(entity);
		
	
		/* EntityList --> DTOList */
		List<ReplyDTO> replyDTOList = new ArrayList<>();
		
		for(ReplyEntity temp : replyEntityList) {
			ReplyDTO dto = ReplyDTO.toDTO(temp, boardNum);
			replyDTOList.add(dto);
		}
		
		return replyDTOList;
	}

	/**
	 * 댓글  삭제
	 * @param boardNum
	 * @return
	 */
	public boolean replyDelete(Long replyNum) {
		replyRepository.deleteById(replyNum);
		return replyRepository.existsById(replyNum);
	}

}





