package com.widehouse.cafe.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cafe")
data class Cafe(
    @Id val url: String)

