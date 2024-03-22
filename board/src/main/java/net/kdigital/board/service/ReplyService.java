package net.kdigital.board.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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

    /**
     * 전달받은 boardNum에 해당하는 게시글의 댓글 목록 반환
     * @param boardNum
     * @return
     */
    public List<ReplyDTO> replyAll(Long boardNum) {
        BoardEntity boardEntity = boardRepository.findById(boardNum).get();
        
        List<ReplyEntity> replyEntityList = replyRepository.findAllByBoardEntityOrderByReplyNumDesc(boardEntity);
        /**
         * 새로운 함수 만드는 이름 규칙(규칙대로 만들어야 함!!) -> 메소드 이름에 의해 쿼리문이 실행되는 것임
         * findAllBy (/findBy) : select하는게 여러 개일 때
         * OrderBy변수Desc(Asc) : 가져올 때 정렬 순서 
         */


        // EntityList -> DTOList
        List<ReplyDTO> replyDTOList = new ArrayList<>();

        replyEntityList.forEach((entity)->{
            replyDTOList.add(ReplyDTO.toDTO(entity, boardNum));
        });

        return replyDTOList;
    }

    /**
     * 전달받은 댓글 번호에 해당하는 댓글 삭제하는 함수 (삭제 되면 true 반환)
     * @param replyNum
     * @return
     */
    public boolean delete(Long replyNum) {
        replyRepository.deleteById(replyNum);

        return !replyRepository.existsById(replyNum); 
    }

    /**
     * 전달받은 댓글 번호에 해당하는 좋아요 수 증가하는 
     * @param replyNum
     * @return
     */
    @Transactional
    public int incrementLike(Long replyNum, int count) {
        Optional<ReplyEntity> entity =  replyRepository.findById(replyNum);
        if (entity.isPresent()) {
            ReplyEntity replyEntity = entity.get();
            replyEntity.setFavorite(replyEntity.getFavorite()+count); // 좋아요 1 증가
            
            return replyEntity.getFavorite(); 
        }
        return 0;
    }
    

}
