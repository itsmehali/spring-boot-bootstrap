package com.baeldung.persistence.repo;
import com.baeldung.persistence.dto.AuthorDTO;
import com.baeldung.persistence.dto.BookDTO;
import com.baeldung.persistence.model.enums.OriginCountry;

import java.time.LocalDate;
import java.util.List;
public interface AuthorRepositoryCustom {
    List<AuthorDTO> findAuthorsWithBookCount(String name, LocalDate birth_date, OriginCountry origin_country, Long book_count, List<BookDTO> books);
}
