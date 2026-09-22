package com.curso_api.service;

import com.curso_api.dto.CursoRequestDTO;
import com.curso_api.dto.CursoResponseDTO;
import com.curso_api.entity.Curso;
import com.curso_api.entity.Instrutor;
import com.curso_api.mapper.CursoMapper;
import com.curso_api.repository.CursoRepository;
import com.curso_api.repository.InstrutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final InstrutorRepository instrutorRepository;
    private final CursoMapper cursoMapper;

    public CursoService(CursoRepository cursoRepository, InstrutorRepository instrutorRepository, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.instrutorRepository = instrutorRepository;
        this.cursoMapper = cursoMapper;
    }

    public List<CursoResponseDTO> listarTodos(){
        return cursoRepository.findAll().stream().map(c -> cursoMapper.toResponse(c)).toList();
    }

    public CursoResponseDTO buscarPorId(Long id){
        return cursoMapper.toResponse(cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com id: " + id)));
    }

    public void deletar(Long id){
        buscarPorId(id);

        cursoRepository.deleteById(id);
    }

    public CursoResponseDTO criar (CursoRequestDTO cursoRequestDTO){
        Curso curso = cursoMapper.toEntity(cursoRequestDTO);

        Instrutor instrutor = instrutorRepository.findById(cursoRequestDTO.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Instrutor não encontrado com id: " + cursoRequestDTO.instrutorId()));

        curso.setInstrutor(instrutor);

        return cursoMapper.toResponse(cursoRepository.save(curso));
    }

    public CursoResponseDTO atualizar (Long cursoId, CursoRequestDTO cursoRequestDTO){
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com id: " + cursoId));

        Instrutor instrutor = instrutorRepository.findById(cursoRequestDTO.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Instrutor não encontrado com id: " + cursoRequestDTO.instrutorId()));

        cursoMapper.update(cursoRequestDTO, curso);
        curso.setInstrutor(instrutor);

        return cursoMapper.toResponse(cursoRepository.save(curso));
    }
}
