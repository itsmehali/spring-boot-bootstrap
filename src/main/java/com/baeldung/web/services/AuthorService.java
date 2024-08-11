package com.baeldung.web.services;

import com.baeldung.persistence.dto.AuthorDTO;
import com.baeldung.persistence.dto.BookDTO;
import com.baeldung.persistence.model.Author;
import com.baeldung.persistence.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baeldung.persistence.model.enums.OriginCountry;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> updateAuthor(Long id, Author authorDetails) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            if (authorDetails.getName() != null) {
                author.setName(authorDetails.getName());
            }
            if (authorDetails.getBirth_date() != null) {
                author.setBirth_date(authorDetails.getBirth_date());
            }
            if (authorDetails.getOrigin_country() != null) {
                author.setOrigin_country(authorDetails.getOrigin_country());
            }
            return Optional.of(authorRepository.save(author));
        }
        return Optional.empty();
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public List<AuthorDTO> filterAuthors(String name, LocalDate birthDate, OriginCountry origin_country, Long book_count, List<BookDTO> books) {
        List<AuthorDTO> authorsWithBookCount = authorRepository.findAuthorsWithBookCount(name, birthDate, origin_country, book_count, books );

        return authorsWithBookCount.stream()
                .map(author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getBirth_date(),
                        author.getOrigin_country(),
                        author.getBookCount(),
                        author.getBooks()
                ))
                .collect(Collectors.toList());
    }
}
