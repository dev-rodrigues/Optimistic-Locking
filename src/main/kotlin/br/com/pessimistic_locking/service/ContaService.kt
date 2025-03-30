package br.com.pessimistic_locking.service

import br.com.pessimistic_locking.repository.ContaRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ContaService(
    @Autowired private val contaRepository: ContaRepository
) {

    @Transactional
    fun transferir(origemId: Long, destinoId: Long, valor: BigDecimal) {
        val origem = contaRepository.findByIdForUpdate(origemId)
        val destino = contaRepository.findByIdForUpdate(destinoId)

        if (origem.saldo < valor) throw IllegalArgumentException("Saldo insuficiente")

        origem.saldo -= valor
        destino.saldo += valor

        contaRepository.save(origem)
        contaRepository.save(destino)
    }
}