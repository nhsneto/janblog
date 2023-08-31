package com.janblog.controller.v1;

import com.janblog.repository.ArticleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleRepository articleRepo;

    public ArticleController(ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(articleRepo.findAll());
    }
}
