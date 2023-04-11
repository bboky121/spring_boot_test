package com.example.gadget.controller

import com.example.gadget.model.Gadget
import com.example.gadget.repository.GadgetRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.coyote.Request
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class GadgetControllerTest @Autowired constructor (
    val gadgetRepository: GadgetRepository,
    val mockMvc: MockMvc,
) {
    @Test
    fun addNewGadget() {
        val request = Gadget(
            gadgetId = 2,
            gadgetName = "캬캬캬",
            gadgetCategory = null,
            gadgetAvailability = false,
            gadgetPrice = 25300.0
        )
        val content = jacksonObjectMapper().writeValueAsString(request)

        mockMvc.perform(
            post("/api/gadgets")
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andDo(print())

    }

    @Test
    fun fetchGadgets() {
        val gadget : Gadget = gadgetRepository.save(Gadget(
            gadgetId = 1,
            gadgetName = "test1515",
            gadgetCategory = null,
            gadgetAvailability = true,
            gadgetPrice = 1500.0
        ))

        mockMvc.perform(
            get("/api/gadgets/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$.gadgetName").value(gadget.gadgetName))
                .andDo(print())

    }

    @Test
    fun fetchGadgetById() {
    }

    @Test
    fun updateGadgetById() {
    }

    @Test
    fun removeGadgetById() {
    }

    @Test
    fun removeGadgets() {
    }
}