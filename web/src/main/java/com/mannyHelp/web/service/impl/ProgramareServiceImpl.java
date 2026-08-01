package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.dto.ProgramareDto;
import com.mannyHelp.web.models.*;
import com.mannyHelp.web.repository.OferitorServiciiRepository;
import com.mannyHelp.web.repository.ProgramareRepository;
import com.mannyHelp.web.repository.ServiceRepository;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.service.ProgramareService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProgramareServiceImpl implements ProgramareService {

    private final ProgramareRepository programareRepository;
    private final UsersRepository usersRepository;
    private final ServiceRepository serviceRepository;
    private final OferitorServiciiRepository oferitorRepository;

    public ProgramareServiceImpl(ProgramareRepository programareRepository,
                                 UsersRepository usersRepository,
                                 ServiceRepository serviceRepository,
                                 OferitorServiciiRepository oferitorRepository) {
        this.programareRepository = programareRepository;
        this.usersRepository = usersRepository;
        this.serviceRepository = serviceRepository;
        this.oferitorRepository = oferitorRepository;
    }

    @Override
    public ProgramareDto createProgramare(Long userId, int serviceId, int providerId, LocalDateTime dataProgramare) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        com.mannyHelp.web.models.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviciul nu a fost găsit"));
        OferitorServicii provider = oferitorRepository.findById((long) providerId)
                .orElseThrow(() -> new RuntimeException("Furnizorul nu a fost găsit"));

        // Construim cheia compusă BookingId
        BookingId bookingId = new BookingId(Math.toIntExact(userId), serviceId, providerId);

        Programare programare = Programare.builder()
                .programareid(bookingId)
                .user(user)
                .service(service)
                .provider(provider)
                .status("PENDING")
                .dataProgramare(dataProgramare)
                .build();

        Programare saved = programareRepository.save(programare);

        return ProgramareDto.builder()
                .userNume(user.getNume())
                .serviceNume(service.getNumeServiciu())
                .providerNume(provider.getNumeCompanie())
                .status(saved.getStatus())
                .dataProgramare(saved.getDataProgramare())
                .build();
    }
}