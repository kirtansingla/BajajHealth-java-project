package com.example.bfhl.controller;

import com.example.bfhl.dto.BfhlRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testExampleA() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", contains("1")))
                .andExpect(jsonPath("$.even_numbers", contains("334", "4")))
                .andExpect(jsonPath("$.alphabets", contains("A", "R")))
                .andExpect(jsonPath("$.special_characters", contains("$")))
                .andExpect(jsonPath("$.sum", is("340")))
                .andExpect(jsonPath("$.concat_string", is("rA")));
    }

    @Test
    public void testExampleB() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", contains("5")))
                .andExpect(jsonPath("$.even_numbers", contains("2", "4", "92")))
                .andExpect(jsonPath("$.alphabets", contains("A", "Y", "B")))
                .andExpect(jsonPath("$.special_characters", contains("&", "-", "*")))
                .andExpect(jsonPath("$.sum", is("104")))
                .andExpect(jsonPath("$.concat_string", is("bYa")));
    }

    @Test
    public void testExampleC() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", is(empty())))
                .andExpect(jsonPath("$.alphabets", contains("A", "ABCD", "DOE")))
                .andExpect(jsonPath("$.special_characters", is(empty())))
                .andExpect(jsonPath("$.sum", is("1")))
                .andExpect(jsonPath("$.concat_string", is("eOdDcBaA")));
    }

    @Test
    public void testEmptyArray() throws Exception {
        BfhlRequest request = new BfhlRequest(Collections.emptyList());

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", is(empty())))
                .andExpect(jsonPath("$.alphabets", is(empty())))
                .andExpect(jsonPath("$.special_characters", is(empty())))
                .andExpect(jsonPath("$.sum", is("1")))
                .andExpect(jsonPath("$.concat_string", is("")));
    }

    @Test
    public void testOnlyNumbers() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("2", "-4", "5", "-11"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", contains("5", "-11")))
                .andExpect(jsonPath("$.even_numbers", contains("2", "-4")))
                .andExpect(jsonPath("$.alphabets", is(empty())))
                .andExpect(jsonPath("$.special_characters", is(empty())))
                .andExpect(jsonPath("$.sum", is("-7")))
                .andExpect(jsonPath("$.concat_string", is("")));
    }

    @Test
    public void testOnlyAlphabets() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("x", "Y", "z"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", is(empty())))
                .andExpect(jsonPath("$.alphabets", contains("X", "Y", "Z")))
                .andExpect(jsonPath("$.special_characters", is(empty())))
                .andExpect(jsonPath("$.sum", is("1")))
                .andExpect(jsonPath("$.concat_string", is("zYx")));
    }

    @Test
    public void testOnlySpecialCharacters() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("@", "#", "$", "!"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", is(empty())))
                .andExpect(jsonPath("$.alphabets", is(empty())))
                .andExpect(jsonPath("$.special_characters", contains("@", "#", "$", "!")))
                .andExpect(jsonPath("$.sum", is("1")))
                .andExpect(jsonPath("$.concat_string", is("")));
    }

    @Test
    public void testMixedValues() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a1b", "2.5", "10", "c"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", contains("10")))
                .andExpect(jsonPath("$.alphabets", contains("C")))
                .andExpect(jsonPath("$.special_characters", contains("a1b", "2.5")))
                .andExpect(jsonPath("$.sum", is("11")))
                .andExpect(jsonPath("$.concat_string", is("c")));
    }

    @Test
    public void testNullRequest() throws Exception {
        BfhlRequest request = new BfhlRequest(null);

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.message", containsString("data list cannot be null")));
    }

    @Test
    public void testMalformedJson() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ invalid json }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.message", containsString("Invalid JSON request format")));
    }
}
