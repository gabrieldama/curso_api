package com.curso_api.dto;

import jakarta.validation.constraints.NotBlank;

public record CursoRequestDTO(
        @NotBlank(message = "Nome do curso é obrigatório")
        String nome,
        String descricao,
        Integer cargaHoraria,
        Long instrutorId
) {
}
