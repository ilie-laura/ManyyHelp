package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.dto.OferitorServiciiDto;
import com.mannyHelp.web.models.OferitorServicii;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.OferitorServiciiRepository;
import com.mannyHelp.web.repository.ServiceRepository;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.service.OferitorServiciiService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OferitorServiciiServiceImpl implements OferitorServiciiService {

    private final OferitorServiciiRepository osrepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServiceRepository serviceRepository;

    public OferitorServiciiServiceImpl(OferitorServiciiRepository osrepository,
                                       UsersRepository usersRepository,
                                       PasswordEncoder passwordEncoder,
                                       ServiceRepository serviceRepository) {
        this.osrepository = osrepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<OferitorServiciiDto> findAllOferitori() {
        List<OferitorServicii> os = osrepository.findAll();

        return os.stream()
                .map(this::mapToOferitorServiciiDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveOferitorServicii(OferitorServiciiDto dto) {

        Users user = Users.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nume(dto.getNume())
                .prenume(dto.getPrenume())
                .photourl(dto.getPhotoUrl()) // Corectat: photourl în loc de photoUrl dacă modelul are câmpul photourl
                .userOrProvider(true)
                .build();

        Users savedUser = usersRepository.save(user);

        OferitorServicii provider = OferitorServicii.builder()
                .numeCompanie(dto.getNumeCompanie())
                .cui(dto.getCui())
                .descriereServicii(dto.getDescriereServicii())
                .telefonContact(dto.getTelefonContact())
                .user(savedUser)
                .build();

        osrepository.save(provider);
    }

    @Override
    public OferitorServiciiDto findByServiceId(int serviceId) {

        com.mannyHelp.web.models.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviciul cu ID-ul " + serviceId + " nu a fost găsit"));

        Users providerUser = service.getProvider();

        if (providerUser == null) {
            throw new RuntimeException("Serviciul nu are niciun utilizator/furnizor asignat");
        }

        OferitorServicii provider = osrepository.findByUserUserid(providerUser.getUserid())
                .orElseThrow(() -> new RuntimeException("Nu s-a găsit profilul de OferitorServicii pentru utilizatorul " + providerUser.getUsername()));

        return mapToOferitorServiciiDto(provider);
    }

    private OferitorServiciiDto mapToOferitorServiciiDto(OferitorServicii os) {
        Users user = os.getUser();

        return OferitorServiciiDto.builder()
                .providerid((long) os.getProviderid())
                .numeCompanie(os.getNumeCompanie())
                .cui(os.getCui())
                .descriereServicii(os.getDescriereServicii())
                .telefonContact(os.getTelefonContact())
                .reviews(os.getReviews())
                .username(user != null ? user.getUsername() : null)
                .nume(user != null ? user.getNume() : null)
                .prenume(user != null ? user.getPrenume() : null)

                .photoUrl(user != null ? user.getPhotourl() : null)
                .build();
    }
}