package net.kdigital.spring6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import net.kdigital.spring6.entity.FriendEntity;

public interface FriendRepository extends JpaRepository<FriendEntity, Long> {

    // @Modifying // 입력 시 바로 수정되는게 아니라 완충 메모리(캐시 메모리)에 저장했다가 커밋시 반영됨을 의미
    // @Transactional // 오류가 났을 때 rollback해야 하기 때문에 꼭 써줘야 함
    // @Query(value = """
    // update friend
    // set
    // fname = :#{#entity.fname},
    // age = :#{#entity.age},
    // phone = :#{#entity.phone},
    // birthday = :#{#entity.birthday},
    // active = :#{#entity.active}
    // where friend_seq = :#{#entity.friendSeq}
    // """, nativeQuery = true) // sql developer에서 사용가능한 native query
    // public void updateFriend(@Param("entity") FriendEntity entity); // Entity 클래스
    // 이름, id의 타입

}
