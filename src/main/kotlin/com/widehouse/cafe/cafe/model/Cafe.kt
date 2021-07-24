package com.widehouse.cafe.cafe.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Cafe(
    @Id val id: String,
    val name: String,
    val description: String,
    @Indexed
    val theme: String
)
