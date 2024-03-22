package net.kdigital.board.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.kdigital.board.dto.ReplyDTO;
import net.kdigital.board.entity.BoardEntity;
import net.kdigital.board.entity.ReplyEntity;
import net.kdigital.board.repository.BoardRepository;
import net.kdigital.board.repository.ReplyRepository;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public ReplyDTO replyInsert(ReplyDTO replyDTO) {
        Optional<BoardEntity> boardEntity =boardRepository.findById(replyDTO.getBoardNum());
        
        if (boardEntity.isPresent()) {
            BoardEntity entity = boardEntity.get();
            ReplyEntity replyEntity = ReplyEntity.toEntity(replyDTO, entity);
            
            ReplyEntity temp = replyRepository.save(replyEntity);
            return ReplyDTO.toDTO(temp, replyDTO.getBoardNum());
            
        }else{
            return null;
        }
    }
    
    

}
