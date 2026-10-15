package org.iesalixar.pps.radiografia.controller;

import jakarta.validation.Valid;
import org.iesalixar.pps.radiografia.model.NoticeRequest;
import org.iesalixar.pps.radiografia.model.SecurityNotice;
import org.iesalixar.pps.radiografia.service.SecurityNoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class SecurityNoticeController {

    private final SecurityNoticeService service;

    public SecurityNoticeController(SecurityNoticeService service) {
        this.service = service;
    }

    @GetMapping
    public List<SecurityNotice> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SecurityNotice findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SecurityNotice create(@Valid @RequestBody NoticeRequest request) {
        return service.create(request);
    }
}
