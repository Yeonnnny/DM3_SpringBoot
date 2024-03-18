package net.kdigital.test3.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.test3.entity.GuestbookEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GuestbookDTO {
    private Long guestSeq;

    @Size(min = 2, max = 10, message = "이름은 2~10자 이내로 입력해야 합니다.")
    private String guestName;

    @Size(min = 4, max = 12, message = "비밀번호는 4~12자 이내로 입력해야 합니다.")
    private String guestPwd;

    @Size(min = 1, message = "방명록 내용을 한 글자 이상 입력해야 합니다.")
    private String guestText;

    private LocalDate regdate;

    public static GuestbookEntity toEntity(GuestbookDTO guestbookDTO) {
        return GuestbookEntity.builder()
                .guestName(guestbookDTO.getGuestName())
                .guestPwd(guestbookDTO.getGuestPwd())
                .guestText(guestbookDTO.getGuestText())
                .regdate(guestbookDTO.getRegdate())
                .build();
    }

}
/*
 * create table guestbook(
 * geust_seq number primary key,
 * guest_name varchar2(50) not null,
 * guest_pwd varchar2(20) not null,
 * guest_text varchar2(2000),
 * regdate date default sysdate
 * );
 */
