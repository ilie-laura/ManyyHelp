package com.mannyHelp.web.service.impl;

import com.mannyHelp.web.dto.OferitorServiciiDto;
import com.mannyHelp.web.models.OferitorServicii;
import com.mannyHelp.web.repository.OferitorServiciiRepository;
import com.mannyHelp.web.service.OferitorServiciiService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OferitorServiciiServiceImpl implements OferitorServiciiService {

    private final OferitorServiciiRepository osrepository;

    // În Spring modern (4.3+), dacă ai un singur constructor, @Autowired nu mai este obligatoriu.
    public OferitorServiciiServiceImpl(OferitorServiciiRepository osrepository) {
        this.osrepository = osrepository;
    }



    @Override
    public List<OferitorServiciiDto> findAllOferitori() {

        List<OferitorServicii> os = osrepository.findAll();

        return os.stream()
                .map(item -> mapToOferitorServiciiDto(item))
                .collect(Collectors.toList());
    }

    private OferitorServiciiDto mapToOferitorServiciiDto(OferitorServicii os) {
        return OferitorServiciiDto.builder()
                .reviews(os.getReviews())
                .build();
    }
}