package org.iesalixar.pps.radiografia.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NoticeRequest(
        @NotBlank
        @Size(max = 100)
        String title,

        @NotBlank
        @Pattern(regexp = "LOW|MEDIUM|HIGH|CRITICAL")
        String severity,

        @NotBlank
        @Size(max = 500)
        String description
) {
}
