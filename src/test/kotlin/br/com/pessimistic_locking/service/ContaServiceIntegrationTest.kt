package br.com.pessimistic_locking.service

import br.com.pessimistic_locking.entity.Conta
import br.com.pessimistic_locking.repository.ContaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.ObjectOptimisticLockingFailureException
import java.math.BigDecimal

@SpringBootTest
class ContaServiceIntegrationTest {

    @Autowired
    lateinit var contaRepository: ContaRepository

    lateinit var contaInicial: Conta

    @BeforeEach
    fun setup() {
        contaRepository.deleteAll() // Garante base limpa

        contaInicial = contaRepository.saveAndFlush(
            Conta(id = 1L, saldo = BigDecimal("100.00"))
        )
    }

    @Test
    fun `deve lançar exceção de conflito de versão com Optimistic Locking`() {
        val tx1 = contaRepository.findById(1L).get()
        val tx2 = contaRepository.findById(1L).get()

        tx1.saldo += BigDecimal("50.00")
        contaRepository.saveAndFlush(tx1) // version 0 → 1

        tx2.saldo -= BigDecimal("20.00")

        val exception = assertThrows<ObjectOptimisticLockingFailureException> {
            contaRepository.saveAndFlush(tx2)
        }

        println("Exceção esperada: ${exception.message}")
    }
}