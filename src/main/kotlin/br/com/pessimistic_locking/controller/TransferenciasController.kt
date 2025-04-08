package br.com.pessimistic_locking.controller

import br.com.pessimistic_locking.service.ContaService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.math.BigDecimal

@Controller
@RequestMapping("/transferencias")
class TransferenciasController(
    private val service: ContaService
) {
    @PostMapping
    fun post(@RequestBody body: RequestTransferencia): ResponseEntity<Void> {
        service.transferir(
            origemId = body.origemId,
            destinoId = body.destinoId,
            valor = body.valor
        )
        return ResponseEntity.ok().build()
    }
}

data class RequestTransferencia(
    val origemId: Long,
    val destinoId: Long,
    val valor: BigDecimal

)