package org.iesalixar.pps.radiografia.controller;

import org.junit.jupiter.api.Test;
import org.iesalixar.pps.radiografia.model.SecurityNotice;
import org.iesalixar.pps.radiografia.service.SecurityNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecurityNoticeController.class)
class SecurityNoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SecurityNoticeService service;

    @Test
    void shouldReturnNotices() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                new SecurityNotice("Prueba", "LOW", "Aviso de prueba")
        ));

        mockMvc.perform(get("/api/notices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Prueba"))
                .andExpect(jsonPath("$[0].severity").value("LOW"));
    }
}
