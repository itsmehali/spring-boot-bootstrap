package com.baeldung.persistence.repo_impl;

import com.baeldung.persistence.dto.AuthorDTO;
import com.baeldung.persistence.model.Author;
import com.baeldung.persistence.model.Book;
import com.baeldung.persistence.model.enums.OriginCountry;
import com.baeldung.persistence.repo.AuthorRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Repository
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<AuthorDTO> findAuthorsWithBookCount(String name, LocalDate birth_date, OriginCountry origin_country, Long book_count) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuthorDTO> query = cb.createQuery(AuthorDTO.class);
        Root<Author> author = query.from(Author.class);


        Join<Author, Book> booksJoin = author.join("books");

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.equal(author.get("name"), name));
        }
        if (birth_date != null) {
            predicates.add(cb.equal(author.get("birth_date"), birth_date));
        }
        if (origin_country != null) {
            predicates.add(cb.equal(author.get("origin_country"), origin_country));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // Selecting required fields
        query.multiselect(
                author.get("id"),
                author.get("name"),
                author.get("birth_date"),
                author.get("origin_country"),
                cb.count(booksJoin).alias("book_count")
        );

        query.groupBy(
                author.get("id"),
                author.get("name"),
                author.get("birth_date"),
                author.get("origin_country")
        );

        query.having(cb.ge(cb.count(booksJoin), book_count));

        return entityManager.createQuery(query).getResultList();
    }
}
