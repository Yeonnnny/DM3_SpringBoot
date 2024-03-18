package net.kdigital.spring6.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring6.dto.FriendDTO;
import net.kdigital.spring6.entity.FriendEntity;
import net.kdigital.spring6.repository.FriendRepository;

@Service
@Slf4j
public class FriendService {

    private FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    /**
     * DB에 저장하는 함수 (friendDto를 입력받아 entity로 변환하여 저장함)
     * 
     * @param friendDTO
     */
    public void insertFriend(FriendDTO friendDTO) {

        log.info("service에 도착");

        // 1) DTO -> entity로 변환
        FriendEntity entity = FriendDTO.toEntity(friendDTO);

        // 2) Repository로 넘겨서 저장
        friendRepository.save(entity);
        log.info("저장");
    }

    /**
     * 전체 데이터 조회하는 함수
     * 
     * @return
     */
    public List<FriendDTO> selectAll() {
        // 정렬되지 않음
        // List<FriendEntity> list = friendRepository.findAll();

        // 이름 오름차순으로 정렬
        List<FriendEntity> list = friendRepository.findAll(Sort.by(Sort.Direction.ASC, "fname"));

        List<FriendDTO> friendDTOList = new ArrayList<>();

        list.forEach((entity) -> friendDTOList.add(FriendEntity.toDTO(entity)));

        return friendDTOList;
    }

    /**
     * friendSeq(PK)를 받아 객체 한 개 삭제하는 함수
     * 
     * @param friendSeq
     */
    public void deleteOne(Long friendSeq) {
        friendRepository.deleteById(friendSeq);

    }

    @SuppressWarnings("static-access")
    public FriendDTO selectOne(Long friendSeq) {
        Optional<FriendEntity> entity = friendRepository.findById(friendSeq); // OPtional<> : null pointerexception관리하기
                                                                              // 위한 반환타입
        if (entity.isPresent()) {
            FriendEntity friendEntity = entity.get();
            return FriendEntity.toDTO(friendEntity);
        }
        return null;
    }

    @Transactional
    public void updateProc(FriendDTO friendDTO) {

        // find한 데이터의 값을 set으로 바꾸는 동작 ==> update
        // FriendEntity entity = FriendDTO.toEntity(friendDTO); // set으로 변경하기 위해 DB에서
        // find 해옴
        Optional<FriendEntity> entity = friendRepository.findById(friendDTO.getFriendSeq());

        if (entity.isPresent()) {
            FriendEntity f = entity.get();
            f.setFname(friendDTO.getFname());
            f.setAge(friendDTO.getAge());
            f.setPhone(friendDTO.getPhone());
            f.setBirthday(friendDTO.getBirthday());
            f.setActive(friendDTO.isActive());
        }

        // 오후에 수정할 것
        // friendRepository.updateFriend(entity); // jpql안됨

    }

}
