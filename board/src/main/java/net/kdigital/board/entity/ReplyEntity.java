package net.kdigital.board.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.board.dto.ReplyDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="reply")
public class ReplyEntity {
    @SequenceGenerator(
        name = "reply_seq",
        sequenceName  = "reply_seq",
        allocationSize = 1,
        initialValue = 1
    )
    @Id
    @Column(name = "reply_num")
    @GeneratedValue(generator = "reply_seq")
    private Long replyNum;

    @Column(name = "reply_writer")
    private String replyWriter;

    @Column(name = "reply_text")
    private String replyText;
    
    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createDate;

    private int favorite;
    
    /**
     * BOARD: REPLY = 1:N 관계임
     * REPLY이 일대다에서 다의 위치, 조인 컬럼은 BoardEntity 객체의 boardNum임
     * 부모가 객체로 선언이 되어 있어야 하고, 관계를 맺을 때 @ManyToOne로 설정해야 함
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_num") // 테이블에 저장된 컬럼명 (FK)
    private BoardEntity boardEntity;

    public static ReplyEntity toEntity (ReplyDTO replyDTO, BoardEntity boardEntity){
        return ReplyEntity.builder()
                        .replyNum(replyDTO.getReplyNum())
                        .replyWriter(replyDTO.getReplyWriter())
                        .replyText(replyDTO.getReplyText())
                        .createDate(replyDTO.getCreateDate())
                        .favorite(replyDTO.getFavorite())
                        .boardEntity(boardEntity)
                        .build();
    }

    
}
