package net.kdigital.board.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.board.entity.BoardEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BoardDTO {
	private Long boardNum;
	private String boardWriter;
	private String boardTitle;
	private String boardContent;
	private int hitCount;
	private int favoriteCount;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	
	// 파일이 첨부되었을 때 추가작업
	private MultipartFile uploadFile;
	
	private String originalFileName;		// 원본 파일의 파일명
	private String savedFileName; 			// 하드디스크에 저장될 파일명
	
	// 생성자 (페이징을 위한 생성자, boardList에서 사용할 내용으로 추림)
	public BoardDTO(Long boardNum, String boardWriter, String boardTitle, int hitCount, LocalDateTime createDate,
			String originalFileName) {
		super();
		this.boardNum = boardNum;
		this.boardWriter = boardWriter;
		this.boardTitle = boardTitle;
		this.hitCount = hitCount;
		this.createDate = createDate;
		this.originalFileName = originalFileName;
	}	 
	
	// Entity 받아서  --> DTO 반환 
	public static BoardDTO toDTO(BoardEntity boardEntity) {
		return BoardDTO.builder()
				.boardNum(boardEntity.getBoardNum())
				.boardWriter(boardEntity.getBoardWriter())
				.boardTitle(boardEntity.getBoardTitle())
				.boardContent(boardEntity.getBoardContent())
				.hitCount(boardEntity.getHitCount())
				.favoriteCount(boardEntity.getFavoriteCount())
				.createDate(boardEntity.getCreateDate())
				.updateDate(boardEntity.getUpdateDate())
				.originalFileName(boardEntity.getOriginalFileName())  // 2개 추가
				.savedFileName(boardEntity.getSavedFileName())
				.build();
	}


}





