package br.com.dv.antifraud.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record CustomErrorMessage(
        Integer statusCode,
        LocalDateTime timestamp,
        String description,
        Map<String, String> fieldErrors
) {
}