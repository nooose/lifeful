package readful.core.application.club.command

import org.springframework.stereotype.Service
import readful.core.domain.book.Book
import readful.core.domain.club.Club
import readful.core.domain.club.ClubRepository
import readful.core.domain.club.reading.ReadingStep
import java.time.LocalDateTime

@Service
class ClubCommandService(
    private val clubRepository: ClubRepository
) {

  fun createClub(
      title: String,
      description: String,
      memberCount: Int,
      startAt: LocalDateTime,
      book: Book,
      steps: List<ReadingStep>
  ): Club {
      val club = Club.create(title, description, memberCount, startAt, book);

      steps.forEach{ step -> club.addReadingStep(step) }

      return clubRepository.save(club)
  }
}