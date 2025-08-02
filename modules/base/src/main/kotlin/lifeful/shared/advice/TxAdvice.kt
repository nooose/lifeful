package lifeful.shared.advice

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TxAdvice {
    @Transactional
    fun <T> run(function: () -> T): T {
        return function()
    }
}

@Component
class Tx(
    _txAdvice: TxAdvice,
) {
    init {
        txAdvice = _txAdvice
    }

    companion object {
        private lateinit var txAdvice: TxAdvice

        fun <T> run(function: () -> T): T {
            return txAdvice.run(function)
        }
    }
}
