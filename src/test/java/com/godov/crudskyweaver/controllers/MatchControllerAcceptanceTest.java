package com.godov.crudskyweaver.controllers;

import com.godov.crudskyweaver.dto.match.request.SaveMatchDTORequest;
import com.godov.crudskyweaver.dto.match.request.UpdateMatchDTORequest;
import com.godov.crudskyweaver.dto.match.response.MatchDTOResponse;
import com.godov.crudskyweaver.utils.ResourseUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/sql/matches/clear-db.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class MatchControllerAcceptanceTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    protected ResourseUtils utils;

    @Test
    @DisplayName("Must return page of DTOs with size 20, page 0 and the number of elements 20")
    @Sql(value = {"/sql/matches/set-up-db-before-find-all.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllThenReturnDefaultPageOfMatchDTOResponse() throws Exception {
        //given
        final String expectedPage = utils.getJsonFromResources("json/match/default-match-page.json", MatchDTOResponse.class);
        //when then
        mockMvc.perform(get("/api/v1/matches"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPage));
    }

    @Test
    @DisplayName("Must return page of DTOs with size 10, offset 20, number of elements 4")
    @Sql(value = {"/sql/matches/set-up-db-before-find-all.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllWhenRequestParamsNotDefaultThenReturnPageOfMatchDTOResponse() throws Exception {
        //given
        final String expectedPage = utils.getJsonFromResources("json/match/custom-match-page.json", MatchDTOResponse.class);
        //when then
        mockMvc.perform(get("/api/v1/matches?size=20&page=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPage));
    }

    @Test
    @DisplayName("Must return page of DTOs with full info")
    @Sql(value = {"/sql/matches/set-up-db-before-find-all-with-full-info.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllWithFullInfoThenReturnPageOfFullInfoMatchDTORequest() throws Exception {
        //given
        final String expectedPage = utils.getJsonFromResources("json/match/find-all-with-full-info.json", MatchDTOResponse.class);
        //when then
        mockMvc.perform(get("/api/v1/matches/full-info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedPage));
    }

    @Test
    @DisplayName("Must return DTO of saved match")
    @Sql(value = {"/sql/matches/set-up-db-before-save.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void saveThenReturnSavedDTO() throws Exception {
        //given
        final String content = utils.getJsonFromResources("json/match/save-match.json", SaveMatchDTORequest.class);
        final String expectedMatch = utils.getJsonFromResources("json/match/saved-match.json", MatchDTOResponse.class);
        //when then
        mockMvc.perform(post("/api/v1/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedMatch))
                .andExpect(jsonPath("$.playedOn", equalTo(LocalDate.now().toString())));
    }

    @Test
    @DisplayName("Must return DTO of found entity")
    @Sql(value = {"/sql/matches/set-up-db-before-find-by-id.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByValidIdThenReturnMatchDTO() throws Exception {
        //given
        Long validId = 1L;
        final String expectedMatch = utils.getJsonFromResources("json/match/found-by-id.json", MatchDTOResponse.class);
        //when then
        mockMvc.perform(get("/api/v1/matches/{id}", validId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedMatch));
    }

    @Test
    @DisplayName("Must throw not found exception")
    @Sql(value = {"/sql/matches/set-up-db-before-find-by-id.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByInvalidIdThenThrow() throws Exception {
        //given
        Long invalidId = 0L;
        //when then
        mockMvc.perform(get("/api/v1/matches/{id}", invalidId))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo("Match not found for this id :: " + invalidId)));
    }

    @Test
    @DisplayName("Must delete record and return HTTP 204")
    @Sql(value = {"/sql/matches/set-up-db-before-delete.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteWhenIdIsValidThenReturnDeletedMatchDTO() throws Exception {
        //given
        Long validId = 1L;
        //when then
        mockMvc.perform(delete("/api/v1/matches/{id}", validId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Must throw not found exception")
    @Sql(value = {"/sql/matches/set-up-db-before-delete.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteWhenIdInvalidThenThrow() throws Exception {
        //given
        Long invalidId = 0L;
        //when then
        mockMvc.perform(delete("/api/v1/matches/{id}", invalidId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Match not found for this id :: " + invalidId));
    }

    @Test
    @DisplayName("Must update record and return updated DTO")
    @Sql(value = {"/sql/matches/set-up-db-before-update.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateExistingEntityThenReturnUpdatedMatchDTO() throws Exception {
        //given
        Long validId = 1L;
        final String content = utils.getJsonFromResources("json/match/update-match.json", UpdateMatchDTORequest.class);
        final String expectedMatch = utils.getJsonFromResources("json/match/updated-match.json", MatchDTOResponse.class);
        //when then
        mockMvc.perform(put("/api/v1/matches/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedMatch));
    }
}