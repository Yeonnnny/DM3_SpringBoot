package net.kdigital.board.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.BoardDTO;
import net.kdigital.board.entity.BoardEntity;
import net.kdigital.board.repository.BoardRepository;
import net.kdigital.board.util.FileService;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 업로드된 파일이 저장될 디렉토리 경로를 읽어옴
    @Value("${spring.servlet.multipart.location}")
    String uploadPath;

    // 페이지 당 글의 개수
    @Value("${user.board.pageLimit}")
    int pageLimit;

    /**
     * DB의 모든 데이터를 DTO 리스트로 반환
     * @param pageable 
     * @param searchWord 
     * @param searchItem 
     * 
     * @return dtoList
     */
    public Page<BoardDTO> selectAll(Pageable pageable, String searchItem, String searchWord) {
        int page = pageable.getPageNumber()-1; // 사용자가 요청한 페이지 번호
        // -1을 한 이유 : page 위치의 값은 0부터 시작함
        // 사용자가 1페이지를 요청하면 DB에서는 0페이지를 가져와야 함
        
        // JAVA Reflection 기능을 이용할 수도 있음
        // List<BoardEntity> entityList= new ArrayList<>();
        Page<BoardEntity> entityList= null;

        switch (searchItem) {
            case "boardTitle":
                entityList = boardRepository.findByBoardTitleContaining(searchWord, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "boardNum")));
                break;
            case "boardWriter":
                entityList = boardRepository.findByBoardWriterContaining(searchWord, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "boardNum")));
                break;
            case "boardContent":
                entityList = boardRepository.findByBoardContentContaining(searchWord, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "boardNum")));
                break;
        }
        /*
        System.out.println("글 내용 - getContent() : "+entityList.getContent());                
        System.out.println("글 개수 - getTotalElements() : "+entityList.getTotalElements());      
        System.out.println("요청한 페이지 번호 - getNumber() : "+entityList.getNumber());                    
        System.out.println("총 페이지 수 - getTotalPages() : "+entityList.getTotalPages());
        System.out.println("한 페이지에 보여주는 글 개수 - (getSize()==pageLimit) : "+entityList.getSize());
        System.out.println("이전 페이지 - hasPrevious() : "+entityList.hasPrevious());
        System.out.println("다음 페이지 - hasNext() : "+entityList.hasNext());
        System.out.println("첫번째 페이지 - isFirst() : "+entityList.isFirst());
        System.out.println("마지막 페이지 - isLast() : "+entityList.isLast());
        */
        
        // List<BoardEntity> entityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardNum"));
        Page<BoardDTO> dtoList = null; // DTO 생성자 추가

        // entity를 dto로 변환하여 List에 담는 작업
        // entityList.forEach((entity) -> dtoList.add(BoardDTO.toDTO(entity)));

        // boardList에서 사용하는 변수만으로 생성함
        dtoList = entityList.map(board -> new BoardDTO(board.getBoardNum(),
                                                        board.getBoardWriter(),
                                                        board.getBoardTitle(),
                                                        board.getHitCount(),
                                                        board.getCreateDate(),
                                                        board.getOriginalFileName()));                          

        return dtoList;
    }

    /**
     * DB에 게시글 저장
     * @param boardDTO
     */
    public void insertBoard(BoardDTO boardDTO) {
        boardDTO.setBoardWriter("김도연");
        log.info("====== 저장 경로 : {}",uploadPath);

        String originalFileName = null;
        String savedFileName=null;

        // 첨부파일이 있으면 파일명 세팅 실시
        if (!boardDTO.getUploadFile().isEmpty()) {
            originalFileName = boardDTO.getUploadFile().getOriginalFilename();
            savedFileName = FileService.saveFile(boardDTO.getUploadFile(), uploadPath);

            boardDTO.setOriginalFileName(originalFileName); 
            boardDTO.setSavedFileName(savedFileName); //entity로 변환 전 dto의 savedFileName 변경해주기
        }

        BoardEntity boardEntity = BoardEntity.toEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    /**
     * DB에서 boardNum에 해당하는 게시글 조회
     * 
     * @param boardNum
     * @return
     */
    public BoardDTO selectOne(Long boardNum) {

        Optional<BoardEntity> entity = boardRepository.findById(boardNum);

        if (entity.isPresent()) {
            BoardEntity boardEntity = entity.get();
            return BoardDTO.toDTO(boardEntity);
        }

        return null;
    }

    /**
     * DB에서 boardNum에 해당하는 게시글 삭제
     * 
     * @param boardNum
     */
    @Transactional
    public void deleteOne(Long boardNum) {
        // 글번호에 해당하는 글을 읽어옴 --> savedFileName을 알아내기 위해
        Optional<BoardEntity> entity= boardRepository.findById(boardNum);

        if (entity.isPresent()) {
            BoardEntity boardEntity = entity.get();

            String savedFileName = boardEntity.getSavedFileName();
            
            // 첨부파일이 있는 경우
            if(savedFileName!=null){
                String fullPath = uploadPath+"/"+savedFileName;
                FileService.deleteFile(fullPath);
            }    
            boardRepository.deleteById(boardNum);
        }

    }


    /**
     * DB에 데이터 수정 작업 처리
     * @param boardDTO
     */
    @Transactional
    public void updateOne(BoardDTO boardDTO) {
        MultipartFile uploadFile = boardDTO.getUploadFile(); // 첨부파일

        String originalFileName = null;     // 새로운 첨부파일이 있을 때 (원래 이름)
        String savedFileName = null;        // 새로운 첨부파일이 있을 때 (DB에 저장된 이름)
        String oldSavedFileName = null;     // 기존 업로드된 파일이 있을 때 (DB)


        // 수정 작업에서 새롭게 업로드한 파일이 있는 경우
        // 파일을 저장장치에 저장하고, 이름을 추출함
        if (!uploadFile.isEmpty()) {
            originalFileName = uploadFile.getOriginalFilename();
            savedFileName   = FileService.saveFile(uploadFile, uploadPath);
        }

        // 수정된 내용과 비교를 위해 DB에서 데이터를 가져옴
        Optional<BoardEntity> entity = boardRepository.findById(boardDTO.getBoardNum());
        if (entity.isPresent()) {
            BoardEntity boardEntity = entity.get();     // 
            oldSavedFileName = boardEntity.getSavedFileName();
            
            // 기존 파일이 있고, 업로드한 파일도 있다면, 원래 저장된 파일은 삭제 & 새로운 파일은 저장
            if (oldSavedFileName!=null && !uploadFile.isEmpty()) {
                // 예전 파일은 저장장치에서 삭제
                String fullPath = uploadPath+"/"+oldSavedFileName;  // 예전 파일 경로
                FileService.deleteFile(fullPath);
                // 새로운 파일 세팅 및 저장
                boardEntity.setOriginalFileName(originalFileName);
                boardEntity.setSavedFileName(savedFileName);
            }
            
            // 기존 파일은 없고, 새롭게 업로드한 파일이 있을 경우
            else if (oldSavedFileName==null && !uploadFile.isEmpty()) {
                // 새로운 파일 세팅 및 저장
                boardEntity.setOriginalFileName(originalFileName);
                boardEntity.setSavedFileName(savedFileName);
            }
            
            // 새로운 첨부파일이 없는 경우 : 파일명이 모두 널인 상태로 넘어오기 때문에 파일 처리는 하면 안됨 ==> 즉 기존 DB는 건들이면 안됨
            boardEntity.setBoardTitle(boardDTO.getBoardTitle());
            boardEntity.setBoardContent(boardDTO.getBoardContent());            
            boardEntity.setUpdateDate(LocalDateTime.now());            
        }
    }

    /**
     * 조회수 증가
     * @param boardNum
     */
    @Transactional
    public void incrementHitCount(Long boardNum){
        Optional<BoardEntity> entity = boardRepository.findById(boardNum);
        if (entity.isPresent()) {
            BoardEntity boardEntity = entity.get();
            boardEntity.setHitCount(boardEntity.getHitCount()+1);
        }
    }

    /**
     * 좋아요 증가
     * @param boardNum
     * @return 
     */
    @Transactional
    public int incrementFavoriteCount(Long boardNum, int count){
        Optional<BoardEntity> entity = boardRepository.findById(boardNum);
        if (entity.isPresent()) {
            BoardEntity boardEntity = entity.get();
            boardEntity.setFavoriteCount(boardEntity.getFavoriteCount()+count);
            return boardEntity.getFavoriteCount();
        }
        return 0;
    }

}
