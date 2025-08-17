package lifeful.redis

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
internal class RedisConfig(
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            connectionFactory = redisConnectionFactory
            keySerializer = keySerializer()
            valueSerializer = valueSerializer()
            hashKeySerializer = keySerializer()
            hashKeySerializer = valueSerializer()
        }
    }

    @Bean
    fun keySerializer(): StringRedisSerializer {
        return StringRedisSerializer()
    }

    @Bean
    fun valueSerializer(): GenericJackson2JsonRedisSerializer {
        val cacheObjectMapper = objectMapper.copy().apply {
            val ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Any::class.java)
                .build()
            activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
        }

        return GenericJackson2JsonRedisSerializer(cacheObjectMapper)
    }
}
