package lifeful.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.Date
import lifeful.member.MemberPublicModel
import lifeful.member.MemberTokenGenerator
import org.springframework.stereotype.Component

/**
 * JWT 생성기
 * - TODO: 갱신을 위한 리프레시 토큰 생성 필요
 */
@Component
internal class JwtGenerator(
    jwtProperties: JwtProperties,
) : MemberTokenGenerator {
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())
    private val expirationDuration = jwtProperties.expirationTime.toMillis()

    // TODO: 토큰 쌍 반환
    override fun generate(
        member: MemberPublicModel,
        issuedAt: Date,
    ): String {
        val claims = Jwts.claims().subject(member.id.value.toString()).build()
        val expiration = Date(issuedAt.time + expirationDuration)
        val jwtBuilder = Jwts.builder()
            .header().type("JWT").and()
            .claims(claims)
            .issuedAt(issuedAt)
            .expiration(expiration)
        return jwtBuilder.signWith(secretKey).compact()
    }
}
