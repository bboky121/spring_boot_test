package com.example.gadget

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoNoteApplication

fun main(args: Array<String>) {
	runApplication<DemoNoteApplication>(*args)
}
