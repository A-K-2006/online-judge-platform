package com.onlinejudge.problem.service;

import com.onlinejudge.common.exception.ResourceNotFoundException;
import com.onlinejudge.problem.dto.*;
import com.onlinejudge.problem.entity.Problem;
import com.onlinejudge.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository repository;

    @Override
    public ProblemDto create(ProblemRequest req) {
        Problem p = Problem.builder()
                .title(req.getTitle()).slug(req.getSlug()).description(req.getDescription())
                .difficulty(req.getDifficulty()).examples(req.getExamples())
                .constraints(req.getConstraints()).tags(req.getTags()).build();
        return toDto(repository.save(p));
    }

    @Override
    public ProblemDto getById(Long id) {
        return toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Problem not found")));
    }

    @Override
    public ProblemDto getBySlug(String slug) {
        return toDto(repository.findBySlug(slug).orElseThrow(() -> new ResourceNotFoundException("Problem not found")));
    }

    @Override
    public Page<ProblemDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDto);
    }

    @Override
    public Page<ProblemDto> getByDifficulty(String difficulty, Pageable pageable) {
        return repository.findByDifficulty(difficulty, pageable).map(this::toDto);
    }

    @Override
    public ProblemDto update(Long id, ProblemRequest req) {
        Problem p = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Problem not found"));
        p.setTitle(req.getTitle()); p.setSlug(req.getSlug()); p.setDescription(req.getDescription());
        p.setDifficulty(req.getDifficulty()); p.setExamples(req.getExamples());
        p.setConstraints(req.getConstraints()); p.setTags(req.getTags());
        return toDto(repository.save(p));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ProblemDto toDto(Problem p) {
        return ProblemDto.builder().id(p.getId()).title(p.getTitle()).slug(p.getSlug())
                .description(p.getDescription()).difficulty(p.getDifficulty())
                .examples(p.getExamples()).constraints(p.getConstraints()).tags(p.getTags())
                .acceptedCount(p.getAcceptedCount()).submissionCount(p.getSubmissionCount()).build();
    }
}