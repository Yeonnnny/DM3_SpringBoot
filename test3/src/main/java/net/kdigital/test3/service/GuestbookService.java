package net.kdigital.test3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import net.kdigital.test3.dto.GuestbookDTO;
import net.kdigital.test3.entity.GuestbookEntity;
import net.kdigital.test3.repository.GuestbookRepository;

@Service
public class GuestbookService {

    private GuestbookRepository repository;

    public GuestbookService(GuestbookRepository repository) {
        this.repository = repository;
    }

    public void insert(GuestbookDTO guestbookDTO) {
        GuestbookEntity entity = GuestbookDTO.toEntity(guestbookDTO);
        repository.save(entity);
    }

    public List<GuestbookDTO> selectAll() {
        List<GuestbookEntity> guest_entities = repository.findAll(Sort.by(Sort.Direction.ASC, "guestName"));
        List<GuestbookDTO> guestbookDTOList = new ArrayList<>();

        guest_entities.forEach((entity) -> guestbookDTOList.add(GuestbookEntity.toDTO(entity)));

        return guestbookDTOList;
    }

    public GuestbookDTO selectOne(Long guestSeq) {
        Optional<GuestbookEntity> entity = repository.findById(guestSeq);

        if (entity.isPresent()) {
            GuestbookEntity guestEntity = entity.get();
            GuestbookDTO guestDTO = GuestbookEntity.toDTO(guestEntity);
            return guestDTO;
        }
        return null;
    }

    public void deleteOne(Long guestSeq) {
        repository.deleteById(guestSeq);
    }

    /**
     * 방명록 작성자의 이름과 내용을 수정하는 함수
     * @param guestbookDTO
     */
    @Transactional
    public void updateOnde(@Valid GuestbookDTO guestbookDTO) {

        Optional<GuestbookEntity> entity = repository.findById(guestbookDTO.getGuestSeq());

        if (entity.isPresent()) {
            GuestbookEntity guestEntity = entity.get();
            guestEntity.setGuestName(guestbookDTO.getGuestName());
            guestEntity.setGuestText(guestbookDTO.getGuestText());
        }

    }

}
