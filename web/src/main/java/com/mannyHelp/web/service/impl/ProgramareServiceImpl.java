package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.dto.ProgramareDto;
import com.mannyHelp.web.models.*;
import com.mannyHelp.web.repository.OferitorServiciiRepository;
import com.mannyHelp.web.repository.ProgramareRepository;
import com.mannyHelp.web.repository.ServiceRepository;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.service.ProgramareService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public void createProgramare(Long userId, int serviceId, Long providerId, LocalDateTime dataProgramare, String detaliiSpecifice) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        com.mannyHelp.web.models.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviciul nu a fost găsit"));
        OferitorServicii provider = oferitorRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Furnizorul nu a fost găsit"));

        BookingId bookingId = new BookingId(userId, serviceId, providerId);

        Programare programare = Programare.builder()
                .programareid(bookingId)
                .user(user)
                .service(service)
                .provider(provider)
                .status("PENDING")
                .dataProgramare(dataProgramare)
                .detaliiSpecifice(detaliiSpecifice != null ? detaliiSpecifice : "")
                .reviewTrimis(false)
                .build();

        programareRepository.save(programare);
    }

    @Override
    public void createProgramare(Long userId, int serviceId, Long providerId, LocalDateTime dataProgramare) {
        this.createProgramare(userId, serviceId, providerId, dataProgramare, null);
    }

    @Override
    public List<ProgramareDto> getProgramariByUserId(Long userId) {
        List<Programare> programari = programareRepository.findByUserUserid(userId);
        return programari.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgramareDto> getProgramariByProviderUserId(Long providerUserId) {
        List<Programare> programari = programareRepository.findByProviderUserUserid(providerUserId);
        return programari.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(Long userId, Long serviceId, Long providerId, String newStatus) {
        if (serviceId == null || userId == null || providerId == null) {
            throw new IllegalArgumentException("ID-urile necesare pentru identificarea programării nu pot fi null!");
        }

        BookingId bookingId = new BookingId(userId, Math.toIntExact(serviceId), providerId);
        Programare programare = programareRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Programarea nu a fost găsită!"));

        programare.setStatus(newStatus);
        programareRepository.save(programare);
    }

    @Override
    public void markReviewAsSubmitted(Long userId, Long serviceId, Long providerId) {
        BookingId bookingId = new BookingId(userId, Math.toIntExact(serviceId), providerId);
        Programare programare = programareRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Programarea nu a fost găsită!"));

        programare.setReviewTrimis(true);
        programare.setStatus("COMPLETED");
        programareRepository.save(programare);
    }

    public ProgramareDto mapToDto(Programare programare) {
        return ProgramareDto.builder()
                .userId(programare.getUser() != null ? programare.getUser().getUserid() : (programare.getProgramareid() != null ? programare.getProgramareid().getUserId() : null))
                .serviceId(programare.getService() != null ? (long) programare.getService().getServiceid() : (programare.getProgramareid() != null ? (long) programare.getProgramareid().getServiceId() : null))
                .providerId(programare.getProvider() != null ? programare.getProvider().getProviderid() : (programare.getProgramareid() != null ? programare.getProgramareid().getProviderId() : null))
                .serviceNume(programare.getService() != null ? programare.getService().getNumeServiciu() : "")
                .userNume(programare.getUser() != null ? (programare.getUser().getNume() + " " + (programare.getUser().getPrenume() != null ? programare.getUser().getPrenume() : "")) : "")
                .providerNume(programare.getProvider() != null ? programare.getProvider().getNumeCompanie() : "")
                .dataProgramare(programare.getDataProgramare())
                .status(programare.getStatus())
                .detaliiSpecifice(programare.getDetaliiSpecifice())
                .reviewTrimis(programare.isReviewTrimis())
                .build();
    }

}