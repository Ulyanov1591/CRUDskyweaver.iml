package com.godov.crudskyweaver.mappers;

import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.models.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface MatchMapper {

    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);
    MatchDTO mapToDTO(Match matchEntity);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(target = "playedOn", expression = "java(matchDTO.getPlayedOn() == null || matchDTO.getPlayedOn().isAfter(java.time.LocalDate.now()) ? java.time.LocalDate.now() : matchDTO.getPlayedOn())")
    Match mapToEntity(MatchDTO matchDTO);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(target = "playedOn", expression = "java(matchDTO.getPlayedOn() == null || matchDTO.getPlayedOn().isAfter(java.time.LocalDate.now()) ? matchEntity.getPlayedOn() : matchDTO.getPlayedOn())")
    @Mapping(source = "myHero", target = "myHero", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "opponentHero", target = "opponentHero", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "result", target = "result", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Match updateEntity(MatchDTO matchDTO, @MappingTarget Match matchEntity);

    default List<MatchDTO> mapAllToDTO(final List<Match> entityList) {
        return entityList.stream()
                .map(INSTANCE::mapToDTO)
                .collect(Collectors.toList());
    }
}

