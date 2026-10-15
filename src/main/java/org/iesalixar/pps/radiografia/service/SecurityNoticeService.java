package org.iesalixar.pps.radiografia.service;

import org.iesalixar.pps.radiografia.model.NoticeRequest;
import org.iesalixar.pps.radiografia.model.SecurityNotice;
import org.iesalixar.pps.radiografia.repository.SecurityNoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityNoticeService {

    private final SecurityNoticeRepository repository;

    public SecurityNoticeService(SecurityNoticeRepository repository) {
        this.repository = repository;
    }

    public List<SecurityNotice> findAll() {
        return repository.findAll();
    }

    public SecurityNotice findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aviso no encontrado: " + id));
    }

    public SecurityNotice create(NoticeRequest request) {
        SecurityNotice notice = new SecurityNotice(
                request.title(),
                request.severity(),
                request.description()
        );
        return repository.save(notice);
    }
}
