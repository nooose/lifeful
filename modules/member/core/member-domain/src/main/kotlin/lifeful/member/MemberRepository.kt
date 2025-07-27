package lifeful.member

import org.springframework.data.repository.Repository

/**
 * 사용자 저장소
 */
interface MemberRepository : Repository<Member, Long> {
    fun save(member: Member): Member

    fun findById(id: Long): Member?

    fun findByEmail(email: Email): Member?

    fun findAll(): List<Member>
}
