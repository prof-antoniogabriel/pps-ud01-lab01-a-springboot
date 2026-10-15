package org.iesalixar.pps.radiografia.repository;

import org.iesalixar.pps.radiografia.model.SecurityNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityNoticeRepository extends JpaRepository<SecurityNotice, Long> {
}
