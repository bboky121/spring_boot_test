package com.example.gadget.model

import jakarta.persistence.*
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@Table(name = "GADGET")
data class Gadget (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val gadgetId : Long,
    val gadgetName : String,
    val gadgetCategory : String?,
    val gadgetAvailability : Boolean = true,
    val gadgetPrice : Double
)