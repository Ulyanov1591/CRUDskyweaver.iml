package com.godov.crudskyweaver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/sql/set-up-db.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/clear-db.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MatchControllerAcceptanceTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return page of DTOs with size 20, page 0 and the number of elements 20")
    void findAllThenReturnPageOfDTO() throws Exception {
        //when then
        mockMvc.perform(get("/api/v1/matches"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", equalTo(24)))
                .andExpect(jsonPath("$.content[1].id", equalTo(23)))
                .andExpect(jsonPath("$.content[19].id", equalTo(5)))
                .andExpect(jsonPath("$.size", equalTo(20)))
                .andExpect(jsonPath("$.number", equalTo(0)))
                .andExpect(jsonPath("$.totalPages", equalTo(2)))
                .andExpect(jsonPath("$.numberOfElements", equalTo(20)));
    }

    @Test
    @DisplayName("Should return page of DTOs with size 20, number 1 and the number of elements 4 and offset 20")
    void findAllWhenRequestParamsNotNullThenReturnPageOfDTO() throws Exception {
        //when then
        mockMvc.perform(get("/api/v1/matches?page=1&size=20&sortBy=playedOn&order=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[3].id", equalTo(14)))
                .andExpect(jsonPath("$.content[3].playedOn", equalTo("2022-10-15")))
                .andExpect(jsonPath("$.size", equalTo(20)))
                .andExpect(jsonPath("$.number", equalTo(1)))
                .andExpect(jsonPath("$.pageable.offset", equalTo(20)))
                .andExpect(jsonPath("$.numberOfElements", equalTo(4)));
    }

    @Test
    @DisplayName("Should return DTO of saved entity")
    void saveThenReturnSavedDTO() throws Exception {
        //given
        MatchDTO requestBody = MatchDTO.builder()
                .id(null)
                .myHero(Hero.ADA)
                .opponentHero(Hero.SAMYA)
                .result(Result.WIN)
                .playedOn(null)
                .build();
        //when then
        mockMvc.perform(post("/api/v1/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody)))
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(25)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ADA.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.SAMYA.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.WIN.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.now().toString())));
    }

    @Test
    @DisplayName("Should return DTO of found entity")
    void findByValidIdThenReturnMatchDTO() throws Exception {
        //when then
        mockMvc.perform(get("/api/v1/matches/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ADA.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.SAMYA.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.WIN.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.of(2022, 10, 26).toString())));
    }

    @Test
    @DisplayName("Should throw not found exception")
    void findByInvalidIdThenThrow() throws Exception {
        //given
        Long nonExistentId = 0L;
        //when then
        mockMvc.perform(get("/api/v1/matches/{id}", nonExistentId))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("Match not found for this id :: " + nonExistentId)));
    }

    @Test
    @DisplayName("Should delete entity and return deleted DTO")
    void deleteWhenIdIsValidThenReturnDeletedMatchDTO() throws Exception {
        //when then
        mockMvc.perform(delete("/api/v1/matches/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ADA.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.SAMYA.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.WIN.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.of(2022, 10, 26).toString())));
    }

    @Test
    @DisplayName("Should throw not found exception")
    void deleteWhenIdInvalidThenThrow() throws Exception {
        //given
        Long nonExistentId = 0L;
        //when then
        mockMvc.perform(delete("/api/v1/matches/{id}", nonExistentId))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("Match not found for this id :: " + nonExistentId)));
    }

    @Test
    @DisplayName("Should update entity and return updated DTO")
    void updateExistingEntityThenReturnUpdatedMatchDTO() throws Exception {
        //given
        MatchDTO requestBody = MatchDTO.builder()
                .id(null)
                .myHero(Hero.ARI)
                .opponentHero(Hero.BOURAN)
                .result(Result.LOSE)
                .playedOn(LocalDate.now())
                .build();
        //when then
        mockMvc.perform(put("/api/v1/matches/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody)))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ARI.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.BOURAN.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.LOSE.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.now().toString())));
    }
}