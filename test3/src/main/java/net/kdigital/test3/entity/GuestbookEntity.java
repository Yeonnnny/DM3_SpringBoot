package net.kdigital.test3.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.test3.dto.GuestbookDTO;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "guestbook")
public class GuestbookEntity {

    @SequenceGenerator(name = "guest_seq", sequenceName = "guest_seq", initialValue = 1, allocationSize = 1)
    @Id
    @GeneratedValue(generator = "guest_seq")
    @Column(name = "guest_seq")
    private Long guestSeq;

    @Column(name = "guest_name", nullable = false)
    private String guestName;

    @Column(name = "guest_pwd", nullable = false)
    private String guestPwd;

    @Column(name = "guest_text")
    private String guestText;

    @Column(name = "regdate")
    private LocalDate regdate;

    public static GuestbookDTO toDTO(GuestbookEntity guestbookEntity) {
        return GuestbookDTO.builder()
                .guestSeq(guestbookEntity.getGuestSeq())
                .guestName(guestbookEntity.getGuestName())
                .guestPwd(guestbookEntity.getGuestPwd())
                .guestText(guestbookEntity.getGuestText())
                .regdate(guestbookEntity.getRegdate())
                .build();
    } 

}
/*
 * create table guestbook(
 * guest_seq number primary key,
 * guest_name varchar2(50) not null,
 * guest_pwd varchar2(20) not null,
 * guest_text varchar2(2000),
 * regdate date default sysdate
 * );
 */