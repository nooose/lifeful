package lifeful.shared.config

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
    private val stringSerializer: StringRedisSerializer,
    private val valueSerializer: GenericJackson2JsonRedisSerializer,
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
        val keySerializerPair = fromSerializer(stringSerializer)
        val valueSerializerPair = fromSerializer(valueSerializer)

        return RedisCacheConfiguration
            .defaultCacheConfig()
            .disableCachingNullValues()
            .serializeKeysWith(keySerializerPair)
            .serializeValuesWith(valueSerializerPair)
    }
}
