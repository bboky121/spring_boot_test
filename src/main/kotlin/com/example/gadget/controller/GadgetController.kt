package com.example.gadget.controller

import com.example.gadget.repository.GadgetRepository
import com.example.gadget.model.Gadget
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.util.logging.Logger

@RestController
@RequestMapping("/api")
class GadgetController(private val gadgetRepository: GadgetRepository) {

    @GetMapping("/")
    fun display(): String = "Spring Boot CRUD operation with Kotlin and H2...!"

    @GetMapping("/gadgets")
    fun fetchGadgets(): ResponseEntity<List<Gadget>> {
        val gadgets = gadgetRepository.findAll()
        if (gadgets.isEmpty()) {
            return ResponseEntity<List<Gadget>>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Gadget>>(gadgets, HttpStatus.OK)
    }

    @PostMapping("/gadgets")
    fun addNewGadget(@RequestBody gadget: Gadget, uri: UriComponentsBuilder): ResponseEntity<Gadget> {
        Logger.getLogger("여기다!!!!!!!!!")
        if (gadgetRepository.existsById(gadget.gadgetId)) {
            return ResponseEntity<Gadget>(HttpStatus.BAD_REQUEST)
        }

        val persistedGadget = gadgetRepository.save(gadget)

        if (ObjectUtils.isEmpty(persistedGadget)) {
            return ResponseEntity<Gadget>(HttpStatus.BAD_REQUEST)
        }

        val headers = HttpHeaders()
        headers.location = uri.path("/gadget/{gadgetId}").buildAndExpand(gadget.gadgetId).toUri()
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    @GetMapping("/gadgets/{id}")
    fun fetchGadgetById(@PathVariable("id") gadgetId: Long): ResponseEntity<Gadget> {
        val gadget = gadgetRepository.findById(gadgetId)
        if (gadget.isPresent) {
            return ResponseEntity<Gadget>(gadget.get(), HttpStatus.OK)
        }
        return ResponseEntity<Gadget>(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/gadgets/{id}")
    fun updateGadgetById(@PathVariable("id") gadgetId: Long, @RequestBody gadget: Gadget): ResponseEntity<Gadget> {

        return gadgetRepository.findById(gadgetId).map { gadgetDetails ->
            val updateGadget: Gadget = gadgetDetails.copy(
                gadgetCategory = gadget.gadgetCategory,
                gadgetAvailability = gadget.gadgetAvailability,
                gadgetName = gadget.gadgetName,
                gadgetPrice = gadget.gadgetPrice
            )
            ResponseEntity(gadgetRepository.save(updateGadget), HttpStatus.OK)
        }.orElse(ResponseEntity<Gadget>(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @DeleteMapping("/gadgets/{id}")
    fun removeGadgetById(@PathVariable("id") gadgetId: Long): ResponseEntity<Void> {
        if(gadgetRepository.existsById(gadgetId)) {
            gadgetRepository.deleteById(gadgetId)
            return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @DeleteMapping("/gadgets")
    fun removeGadgets(): ResponseEntity<Void> {
        val gadgets = gadgetRepository.findAll()
        if (gadgets.isEmpty()) {
            return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }
        gadgetRepository.deleteAll()
        return ResponseEntity<Void>(HttpStatus.OK)
    }
}