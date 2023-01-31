package com.godov.crudskyweaver.controllers;

import com.godov.crudskyweaver.dto.user.request.SaveUserDTORequest;
import com.godov.crudskyweaver.dto.user.request.UpdateUserDTORequest;
import com.godov.crudskyweaver.dto.user.response.UserDTOResponse;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/sql/users/clear-db.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    protected ResourseUtils utils;

    @Test
    @DisplayName("Must return DTO of saved user")
    @Sql(value = {"/sql/users/set-up-db-before-save.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void saveThenReturnSavedDTO() throws Exception {
        //given
        final String content = utils.getJsonFromResources("json/user/save-user.json", SaveUserDTORequest.class);
        final String expectedUser = utils.getJsonFromResources("json/user/saved-user.json", UserDTOResponse.class);
        //when then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedUser));
    }

    @Test
    @DisplayName("Must return DTO of updated user")
    @Sql(value = {"/sql/users/set-up-db-before-update.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateThenReturnUpdatedDTO() throws Exception {
        //given
        UUID validId = UUID.fromString("6d55418a-80b4-495f-824c-36eb3639af13");
        final String content = utils.getJsonFromResources("json/user/update-user.json", UpdateUserDTORequest.class);
        final String expectedUser = utils.getJsonFromResources("json/user/updated-user.json", UserDTOResponse.class);
        //when then
        mockMvc.perform(put("/api/v1/users/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedUser));
    }
}