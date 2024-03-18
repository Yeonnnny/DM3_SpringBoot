package net.kdigital.test3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kdigital.test3.entity.GuestbookEntity;

public interface GuestbookRepository extends JpaRepository<GuestbookEntity, Long> {

}
 