package net.kdigital.spring6.entity;

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
import net.kdigital.spring6.dto.FriendDTO;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "friend")
public class FriendEntity {

    @SequenceGenerator(name = "friend_seq", sequenceName = "friend_seq", initialValue = 1, allocationSize = 1)
    @Id
    @Column(name = "friend_seq")
    @GeneratedValue(generator = "friend_seq")
    private Long friendSeq;

    @Column(name = "fname", nullable = false)
    private String fname;

    @Column(name = "age")
    private int age;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "active")
    private boolean active;

    // Entity -> DTO로 반환하는 함수
    public static FriendDTO toDTO(FriendEntity friendEntity) {
        return FriendDTO.builder()
                .friendSeq(friendEntity.getFriendSeq())
                .fname(friendEntity.getFname())
                .age(friendEntity.getAge())
                .phone(friendEntity.getPhone())
                .birthday(friendEntity.getBirthday())
                .active(friendEntity.isActive())
                .build();
    }

}
