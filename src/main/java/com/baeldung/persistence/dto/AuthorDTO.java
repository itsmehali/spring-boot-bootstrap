package com.baeldung.persistence.dto;

import com.baeldung.persistence.model.enums.OriginCountry;
import java.time.LocalDate;
import java.util.List;

public class AuthorDTO {
    private Long id;
    private String name;
    private LocalDate birth_date;
    private OriginCountry origin_country;
    private long book_count;
    private List<BookDTO> books;

    public AuthorDTO() {
    }

    public AuthorDTO(Long id, String name, LocalDate birth_date, OriginCountry origin_country, Long book_count, List<BookDTO> books) {
        this.id = id;
        this.name = name;
        this.birth_date = birth_date;
        this.origin_country = origin_country;
        this.book_count = book_count;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public OriginCountry getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(OriginCountry origin_country) {
        this.origin_country = origin_country;
    }

    public long getBookCount() {
        return book_count;
    }

    public void setBookCount(long book_count) {
        this.book_count = book_count;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
