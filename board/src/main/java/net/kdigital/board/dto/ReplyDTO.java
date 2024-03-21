package net.kdigital.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.board.entity.ReplyEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyDTO {
    private Long replyNum;
    private Long boardNum;
    private String replyWriter;
    private String replyText;
    private LocalDateTime createDate;
    private int favorite;

    // entity -> dto반환
    public static ReplyDTO toDTO(ReplyEntity replyEntity, Long boardNum){
        return ReplyDTO.builder()
                        .replyNum(replyEntity.getReplyNum())
                        .boardNum(boardNum)
                        .replyWriter(replyEntity.getReplyWriter())
                        .replyText(replyEntity.getReplyText())
                        .createDate(replyEntity.getCreateDate())
                        .favorite(replyEntity.getFavorite())
                        .build();
    }
}
