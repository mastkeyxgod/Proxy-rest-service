package ru.mastkey.vkbackendtest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mastkey.vkbackendtest.entity.Audit;


public interface AuditRepository extends JpaRepository<Audit, Long> {
}
