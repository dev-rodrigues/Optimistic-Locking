package br.com.pessimistic_locking.service

import br.com.pessimistic_locking.entity.Conta
import br.com.pessimistic_locking.repository.ContaRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ContaService(
    @Autowired private val contaRepository: ContaRepository
) {

    fun extrato(id: Long): Conta {
        return contaRepository.findById(id).orElseThrow { IllegalArgumentException("Conta não encontrada") }
    }

    @Transactional
    fun transferir(origemId: Long, destinoId: Long, valor: BigDecimal) {
        try {
            val origem = contaRepository.findById(origemId)
                .orElseThrow { RuntimeException("Conta de origem não encontrada") }
            val destino = contaRepository.findById(destinoId)
                .orElseThrow { RuntimeException("Conta de destino não encontrada") }

            if (origem.saldo < valor) {
                throw IllegalArgumentException("Saldo insuficiente")
            }

            origem.saldo -= valor
            destino.saldo += valor

            contaRepository.save(origem)
            contaRepository.save(destino)
        } catch (e: ObjectOptimisticLockingFailureException) {
            throw RuntimeException("Conflito detectado: os dados foram modificados por outra transação.")
        }
    }

}