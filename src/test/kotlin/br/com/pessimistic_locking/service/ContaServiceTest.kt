package br.com.pessimistic_locking.service

import br.com.pessimistic_locking.entity.Conta
import br.com.pessimistic_locking.repository.ContaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.math.BigDecimal
import java.util.Optional
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.*


class ContaServiceTest {
    private val contaRepository: ContaRepository = mockk()
    private val service = ContaService(contaRepository)

    @Test
    fun `deve retornar extrato quando conta existe`() {
        val conta = Conta(id = 1L, saldo = BigDecimal("100.00"))
        every { contaRepository.findById(1L) } returns Optional.of(conta)

        val resultado = service.extrato(1L)

        assertEquals(conta, resultado)
        verify { contaRepository.findById(1L) }
    }

    @Test
    fun `deve lançar exceção quando conta não encontrada no extrato`() {
        every { contaRepository.findById(1L) } returns Optional.empty()

        val exception = assertThrows(IllegalArgumentException::class.java) {
            service.extrato(1L)
        }

        assertEquals("Conta não encontrada", exception.message)
    }

    @Test
    fun `deve transferir valor entre contas com sucesso`() {
        val origem = Conta(1L, BigDecimal("100.00"))
        val destino = Conta(2L, BigDecimal("50.00"))

        every { contaRepository.findById(1L) } returns Optional.of(origem)
        every { contaRepository.findById(2L) } returns Optional.of(destino)
        every { contaRepository.save(any()) } returnsMany listOf(origem, destino)

        service.transferir(1L, 2L, BigDecimal("30.00"))

        assertEquals(BigDecimal("70.00"), origem.saldo)
        assertEquals(BigDecimal("80.00"), destino.saldo)
        verify { contaRepository.save(origem); contaRepository.save(destino) }
    }

    @Test
    fun `deve lançar exceção ao detectar conflito de versão`() {
        val origem = Conta(1L, BigDecimal("100.00"))
        val destino = Conta(2L, BigDecimal("50.00"))

        every { contaRepository.findById(1L) } returns Optional.of(origem)
        every { contaRepository.findById(2L) } returns Optional.of(destino)
        every { contaRepository.save(origem) } throws org.springframework.orm.ObjectOptimisticLockingFailureException(
            Conta::class.java, 1L
        )

        val exception = assertThrows(RuntimeException::class.java) {
            service.transferir(1L, 2L, BigDecimal("30.00"))
        }

        assertTrue(exception.message!!.contains("Conflito detectado"))
    }
}
