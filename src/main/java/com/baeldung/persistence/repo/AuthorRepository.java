package com.baeldung.persistence.repo;

import com.baeldung.persistence.dto.AuthorDTO;
import com.baeldung.persistence.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.baeldung.persistence.model.enums.OriginCountry;

import java.time.LocalDate;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>, AuthorRepositoryCustom {
}
