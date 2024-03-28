package net.kdigital.board.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.board.dto.BoardDTO;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Entity
@Table(name = "board")
public class BoardEntity {

    @Id
    @SequenceGenerator(name = "board_seq", sequenceName = "board_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "board_seq")
    @Column(name = "board_num")
    private Long boardNum;

    @Column(name = "board_writer", nullable = false)
    private String boardWriter;

    @Column(name = "board_title")
    private String boardTitle;

    @Column(name = "board_content")
    private String boardContent;

    @Column(name = "hit_count")
    private int hitCount;

    @Column(name = "favorite_count")
    private int favoriteCount;

    @Column(name = "create_date")
    @CreationTimestamp //게시글이 처음 생성될 때 자동으로 날짜 세팅
    private LocalDateTime createDate;

    @Column(name = "update_date")
    @LastModifiedDate // 게시글이 수정될 때 날짜 세팅 
    private LocalDateTime updateDate;

    // 첨부 파일이 있을 때
    @Column(name="original_file_name")
    private String originalFileName; // 원본 파일의 파일명

    @Column(name="saved_file_name")
    private String savedFileName; // 하드디스크에 저장될 파일

    // 댓글 수
    @Transient
    private int replyCount;

    

    /**
     * REPLY와의 관계 설정
     * mappedBy : one에 해당하는 테이블 엔티티 
     * CascadeType.REMOVE 이 값으로 on delete cascade 설정
     * fetch : LAZY는 지연 호출, EAGER: 즉시 호출
     */
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("reply_num desc")
    private List<ReplyEntity> replyEntity = new ArrayList<>(); // 생성자에 넣어주지않아도 됨. JQuery에서 알아서 자식을 끌어 옴


    //-------------------------------- Getter --------------------------------

    public Long getBoardNum() {
        return boardNum;
    }
    public String getBoardWriter() {
        return boardWriter;
    }
    public String getBoardTitle() {
        return boardTitle;
    }
    public String getBoardContent() {
        return boardContent;
    }
    public int getHitCount() {
        return hitCount;
    }
    public int getFavoriteCount() {
        return favoriteCount;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }
    public String getOriginalFileName() {
        return originalFileName;
    }
    public String getSavedFileName() {
        return savedFileName;
    }
    public List<ReplyEntity> getReplyEntity() {
        return replyEntity;
    }

    // 댓글 수 Get함수
    public int getReplyCount() {
        return replyEntity != null ? replyEntity.size() : 0;
    }

    //---------------------------------------------------------------------------------


    // DTO를 받아서 Entity로 반환
    public static BoardEntity toEntity(BoardDTO boardDTO) {
        return BoardEntity.builder()
                .boardNum(boardDTO.getBoardNum())
                .boardWriter(boardDTO.getBoardWriter())
                .boardTitle(boardDTO.getBoardTitle())
                .boardContent(boardDTO.getBoardContent())
                .hitCount(boardDTO.getHitCount())
                .favoriteCount(boardDTO.getFavoriteCount())
                .originalFileName(boardDTO.getOriginalFileName())
                .savedFileName(boardDTO.getSavedFileName())
                .replyCount(boardDTO.getReplyCount())
                .build();
    }

}
