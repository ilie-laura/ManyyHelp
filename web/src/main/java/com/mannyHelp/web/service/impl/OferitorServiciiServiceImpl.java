package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.dto.OferitorServiciiDto;
import com.mannyHelp.web.models.OferitorServicii;
import com.mannyHelp.web.models.Users;
import com.mannyHelp.web.repository.OferitorServiciiRepository;
import com.mannyHelp.web.repository.UsersRepository;
import com.mannyHelp.web.service.OferitorServiciiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OferitorServiciiServiceImpl implements OferitorServiciiService {

    private final OferitorServiciiRepository osrepository;
    private final UsersRepository usersRepository;

    public OferitorServiciiServiceImpl(OferitorServiciiRepository osrepository, UsersRepository usersRepository) {
        this.osrepository = osrepository;
        this.usersRepository = usersRepository;
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
                .password(dto.getPassword())
                .nume(dto.getNume())
                .prenume(dto.getPrenume())
                .photourl(dto.getPhotoUrl())
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

    private OferitorServiciiDto mapToOferitorServiciiDto(OferitorServicii os) {
        Users user = os.getUser();

        return OferitorServiciiDto.builder()
                .numeCompanie(os.getNumeCompanie())
                .cui(os.getCui())
                .descriereServicii(os.getDescriereServicii())
                .telefonContact(os.getTelefonContact())
                .reviews(os.getReviews())
                .username(user != null ? user.getUsername() : null)
                .nume(user != null ? user.getNume() : null)
                .prenume(user != null ? user.getPrenume() : null)
                .photourl(user != null ? user.getPhotoUrl() : null)
                .build();
    }
}