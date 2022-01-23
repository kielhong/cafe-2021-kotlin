package com.widehouse.cafe.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date

@Configuration
class MongoConfig {
    @Bean
    fun customConversions(): MongoCustomConversions? {
        val converters: MutableList<Converter<*, *>?> = ArrayList()
        converters.add(ZonedDateTimeReadConverter())
        converters.add(ZonedDateTimeWriteConverter())
        return MongoCustomConversions(converters)
    }

    @WritingConverter
    class ZonedDateTimeWriteConverter : Converter<ZonedDateTime, Date> {
        override fun convert(source: ZonedDateTime): Date =
            Date.from(source.toInstant())
    }

    @ReadingConverter
    class ZonedDateTimeReadConverter : Converter<Date, ZonedDateTime> {
        override fun convert(source: Date): ZonedDateTime =
            source.toInstant().atZone(ZoneOffset.UTC)
    }
}
