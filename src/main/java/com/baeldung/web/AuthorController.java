package com.baeldung.web;

import com.baeldung.persistence.dto.AuthorDTO;
import com.baeldung.persistence.model.Author;
import com.baeldung.web.services.AuthorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.baeldung.persistence.model.enums.OriginCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public Iterable<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author authorDetails) {
        Optional<Author> updatedAuthor = authorService.updateAuthor(id, authorDetails);
        return updatedAuthor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/filter")
    public List<AuthorDTO> filterAuthors(@RequestBody AuthorDTO filterRequest) {
        return authorService.filterAuthors(
                filterRequest.getName(),
                filterRequest.getBirth_date(),
                filterRequest.getOrigin_country(),
                filterRequest.getBookCount(),
                filterRequest.getBooks()
        );
    }
}
