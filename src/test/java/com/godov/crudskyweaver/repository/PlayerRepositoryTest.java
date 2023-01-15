package com.godov.crudskyweaver.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
@Transactional
@Sql(value = {"/sql/players/clear-db.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PlayerRepositoryTest {
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("Must return true")
    @Sql(value = {"/sql/players/set-up-db-before-exists.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void existsWithValidAddress() {
        //given
        String validAddress = "0xfc79a6fdea5da8c0734378914bb453fdbe4c995d";
        //when
        boolean doesExist = playerRepository.exists(validAddress);
        //then
        assertTrue(doesExist);
    }
    @Test
    @DisplayName("Must return false")
    @Sql(value = {"/sql/players/set-up-db-before-exists.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void existsWithInvalidAddress() {
        //given
        String invalidAddress = "0x66e3f0f89d1882ae8cd323cb42ed1825197324be";
        //when
        boolean doesExist = playerRepository.exists(invalidAddress);
        //then
        assertFalse(doesExist);
    }
}