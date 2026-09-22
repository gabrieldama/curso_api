package com.curso_api.mapper;

import com.curso_api.dto.CursoRequestDTO;
import com.curso_api.dto.CursoResponseDTO;
import com.curso_api.entity.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    // Ele mapeia automaticamente campos com o mesmo
    //Em alguns casos que os campos possuem nomes diferentes precisa explicar como será feito
    @Mapping(source = "instrutor.nome", target = "InstrutorNome")
    CursoResponseDTO toResponse(Curso curso);

    // Não mapeia o id porque estamos criando um novo curso
    // O InstrutorId não pode ser convertido direto para uma classe Instrutor
    // Isso ainda é preciso ser feito pelo service
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instrutor", ignore = true)
    Curso toEntity(CursoRequestDTO cursoRequestDTO);


    // O iD NUNCA deve ser alterado uma vez que já foi salvo
    // O instrutor ID não pode ser direto para uma classe instrutor
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instrutor", ignore = true)
    void update(CursoRequestDTO dto, @MappingTarget Curso curso);
}
