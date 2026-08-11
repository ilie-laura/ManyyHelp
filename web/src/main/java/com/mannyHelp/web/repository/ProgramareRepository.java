package com.mannyHelp.web.repository;

import com.mannyHelp.web.models.BookingId;
import com.mannyHelp.web.models.Programare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramareRepository extends JpaRepository<Programare, BookingId> {

    List<Programare> findByUserUserid(Long userId);
    List<Programare> findByProgramareidProviderid(int providerId);

    List<Programare> findByProviderUserUserid(Long userId);
}