package com.widehouse.cafe.cafe.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cafe")
data class Cafe(
    @Id val id: String
)
