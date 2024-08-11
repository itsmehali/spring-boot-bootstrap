package com.baeldung.persistence.repo_impl;

import com.baeldung.persistence.dto.AuthorDTO;
import com.baeldung.persistence.dto.BookDTO;
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
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuthorDTO> findAuthorsWithBookCount(String name, LocalDate birth_date, OriginCountry origin_country, Long book_count, List<BookDTO> books) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
        Root<Author> author = query.from(Author.class);
        Join<Author, Book> booksJoin = author.join("books", JoinType.LEFT);

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

        query.multiselect(
                author.get("id"),
                author.get("name"),
                author.get("birth_date"),
                author.get("origin_country"),
                cb.count(booksJoin),
                booksJoin.get("id"),
                booksJoin.get("title"),
                booksJoin.get("author")
        );

        query.groupBy(
                author.get("id"),
                author.get("name"),
                author.get("birth_date"),
                author.get("origin_country"),
                booksJoin.get("id"),
                booksJoin.get("title"),
                booksJoin.get("author")
        );

        query.having(cb.ge(cb.count(booksJoin), book_count));

        List<Tuple> tuples = entityManager.createQuery(query).getResultList();

        List<AuthorDTO> authorDTOs = new ArrayList<>();
        for (Tuple tuple : tuples) {
            Long id = tuple.get(0, Long.class);
            String nameResult = tuple.get(1, String.class);
            LocalDate birthDate = tuple.get(2, LocalDate.class);
            OriginCountry originCountry = tuple.get(3, OriginCountry.class);
            Long bookCount = tuple.get(4, Long.class);

            List<BookDTO> booksList = new ArrayList<>();
            for (int i = 5; i < tuple.getElements().size(); i += 3) {
                Long bookId = tuple.get(i, Long.class);
                String title = tuple.get(i + 1, String.class);
                String authorName = tuple.get(i + 2, String.class);
                BookDTO book = new BookDTO(bookId, title, authorName);
                booksList.add(book);
            }

            AuthorDTO authorDTO = new AuthorDTO(id, nameResult, birthDate, originCountry,bookCount, booksList);
            authorDTOs.add(authorDTO);
        }

        return authorDTOs;
    }
}
