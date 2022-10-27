package com.godov.crudskyweaver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godov.crudskyweaver.dto.MatchDTO;
import com.godov.crudskyweaver.enums.Hero;
import com.godov.crudskyweaver.enums.Result;
import com.godov.crudskyweaver.exceptions.NoSuchMatchFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MatchControllerAcceptanceTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return list of DTOs with the size 3")
    @Sql(value = {"/sql/set-up-db.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllThenReturnListOfDTO() throws Exception {
        //when then
        mockMvc.perform(get("/api/v1/matches"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[2].id", equalTo(3)));
    }

    @Test
    @DisplayName("Should return DTO of saved entity")
    @Sql(value = {"/sql/set-up-db.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
                .andExpect(jsonPath("$.id", equalTo(4)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ADA.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.SAMYA.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.WIN.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.now().toString())));
    }

    @Test
    @DisplayName("Should return DTO of found entity")
    @Sql(value = {"/sql/set-up-db.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByValidIdThenReturnMatchDTO() throws Exception {
        //when then
        mockMvc.perform(get("/api/v1/matches/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ADA.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.SAMYA.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.WIN.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.of(2022,10,20).toString())));
    }

    @Test
    @DisplayName("Should throw not found exception")
    @Sql(value = {"/sql/set-up-db.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByInvalidIdThenThrow() throws Exception {
        //given
        Long nonExistentId = 4L;
        //when then
        mockMvc.perform(get("/api/v1/matches/{id}", nonExistentId))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("Match not found for this id :: " + nonExistentId)));
    }

    @Test
    @DisplayName("Should delete entity and return deleted DTO")
    @Sql(value = {"/sql/set-up-db.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteWhenIdIsValidThenReturnDeletedMatchDTO() throws Exception {
        //when then
        mockMvc.perform(delete("/api/v1/matches/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.myHero", equalTo(Hero.ADA.toString())))
                .andExpect(jsonPath("$.opponentHero", equalTo(Hero.SAMYA.toString())))
                .andExpect(jsonPath("$.result", equalTo(Result.WIN.toString())))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.of(2022,10,20).toString())));
    }

    @Test
    @DisplayName("Should throw not found exception")
    @Sql(value = {"/sql/set-up-db.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteWhenIdInvalidThenThrow() throws Exception {
        //given
        Long nonExistentId = 4L;
        //when then
        mockMvc.perform(delete("/api/v1/matches/{id}", nonExistentId))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("Match not found for this id :: " + nonExistentId)));
    }

    @Test
    @DisplayName("Should update entity and return updated DTO")
    @Sql(value = {"/sql/set-up-db.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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