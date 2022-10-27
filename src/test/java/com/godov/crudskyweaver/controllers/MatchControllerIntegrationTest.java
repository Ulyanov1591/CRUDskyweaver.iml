package com.godov.crudskyweaver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import com.godov.crudskyweaver.exceptions.NoSuchMatchFoundException;
import com.godov.crudskyweaver.services.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class MatchControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MatchService matchService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void findAll() throws Exception {
        //given
        List<MatchDTO> listToReturn = new ArrayList<>();
        when(matchService.findAll()).thenReturn(listToReturn);
        //when then
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/matches"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findByValidIdThenReturnMatchDTO() throws Exception {
        //given
        MatchDTO responseBody = MatchDTO.builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.SAMYA)
                .result(Result.WIN)
                .playedOn(LocalDate.now())
                .build();
        when(matchService.findById(1L)).thenReturn(responseBody);
        //when then
        mockMvc.perform(get("/api/v1/matches/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ADA.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.SAMYA.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.WIN.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.now().toString())));;
    }

    @Test
    void findByIdWhenIdInvalidThenThrow() throws Exception {
        //given
        Long nonExistentId = 0L;
        when(matchService.findById(0L)).thenThrow(new NoSuchMatchFoundException("Match not found for this id :: " + nonExistentId));
        //when then
        mockMvc.perform(get("/api/v1/matches/{id}", nonExistentId))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("Match not found for this id :: " + nonExistentId)));
    }

    @Test
    void saveThenReturnSavedDTO() throws Exception {
        //given
        MatchDTO requestBody = MatchDTO.builder()
                .id(null)
                .myHero(Hero.ADA)
                .opponentHero(Hero.SAMYA)
                .result(Result.WIN)
                .playedOn(null)
                .build();
        MatchDTO responseBody = MatchDTO.builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.SAMYA)
                .result(Result.WIN)
                .playedOn(LocalDate.now())
                .build();
        when(matchService.save(requestBody)).thenReturn(responseBody);
        //when then
        mockMvc.perform(post("/api/v1/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody)))
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ADA.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.SAMYA.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.WIN.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.now().toString())));
    }

    @Test
    void deleteWhenIdIsValidThenReturnDeletedMatchDTO() throws Exception {
        //given
        MatchDTO responseBody = MatchDTO.builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.SAMYA)
                .result(Result.WIN)
                .playedOn(LocalDate.now())
                .build();
        when(matchService.delete(1L)).thenReturn(responseBody);
        //when then
        mockMvc.perform(delete("/api/v1/matches/{id}", 1L))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ADA.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.SAMYA.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.WIN.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.now().toString())));
    }

    @Test
    void deleteWhenIdInvalidThenThrow() throws Exception {
        //given
        Long nonExistentId = 0L;
        when(matchService.delete(0L)).thenThrow(new NoSuchMatchFoundException("Match not found for this id :: " + nonExistentId));
        //when then
        mockMvc.perform(delete("/api/v1/matches/{id}", nonExistentId))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("Match not found for this id :: " + nonExistentId)));
    }
    @Test
    void updateExistingEntityThenReturnUpdatedMatchDTO() throws Exception {
        //given
        MatchDTO requestBody = MatchDTO.builder()
                .id(null)
                .myHero(Hero.ADA)
                .opponentHero(Hero.SAMYA)
                .result(Result.WIN)
                .playedOn(null)
                .build();
        MatchDTO responseBody = MatchDTO.builder()
                .id(1L)
                .myHero(Hero.ADA)
                .opponentHero(Hero.SAMYA)
                .result(Result.WIN)
                .playedOn(LocalDate.now())
                .build();
        when(matchService.update(1L, requestBody)).thenReturn(responseBody);
        //when then
        mockMvc.perform(put("/api/v1/matches/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody)))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ADA.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.SAMYA.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.WIN.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.now().toString())));
    }
}