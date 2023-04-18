package com.example.gadget.controller

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class NoteController {
    @GetMapping("/notes")
    fun fetchNotes(): ResponseEntity<List<String>> {
        val test = listOf<String>("B", "B", "O", "K", "Y")
        return ResponseEntity<List<String>>(test, HttpStatus.OK)
    }
}