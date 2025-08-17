package lifeful.shared.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@EnableCaching
@Configuration
internal class CacheConfig(
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun redisCacheManager(redisConnectionFactory: RedisConnectionFactory): CacheManager {
        return RedisCacheManager
            .RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(redisCacheConfiguration())
            .withInitialCacheConfigurations(mapOf<String, RedisCacheConfiguration>())
            .build()
    }

    private fun redisCacheConfiguration(): RedisCacheConfiguration {
        val cacheObjectMapper = objectMapper.copy().apply {
            val ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Any::class.java)
                .build()
            activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
        }

        val jacksonSerializer = GenericJackson2JsonRedisSerializer(cacheObjectMapper)
        val keySerializerPair = fromSerializer(StringRedisSerializer())
        val valueSerializerPair = fromSerializer(jacksonSerializer)

        return RedisCacheConfiguration
            .defaultCacheConfig()
            .disableCachingNullValues()
            .serializeKeysWith(keySerializerPair)
            .serializeValuesWith(valueSerializerPair)
    }
}
