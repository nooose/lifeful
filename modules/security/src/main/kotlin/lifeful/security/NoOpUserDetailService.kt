package lifeful.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class NoOpUserDetailService : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        return null
    }
}
