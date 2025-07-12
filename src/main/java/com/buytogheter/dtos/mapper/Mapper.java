package com.buytogheter.dtos.mapper;

import com.buytogheter.dtos.PageableDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final ModelMapper mapper;

    public <S, D> D map(S source, Class<D> destinationClass) {
        System.out.println("Source: " + source);
        System.out.println("Classe: " + destinationClass.getName());
        return mapper.map(source, destinationClass);
    }

    public <S, D> List<D> map(List<S> source, Class<D> destinationClass) {
        return source.stream()
                .map(element -> map(element, destinationClass))
                .toList();
    }

    public PageableDto map(Page page) {
        return new ModelMapper().map(page, PageableDto.class);
    }

}



