package br.com.pessimistic_locking.controller

import br.com.pessimistic_locking.entity.Conta
import br.com.pessimistic_locking.service.ContaService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.math.BigDecimal

@Controller
@RequestMapping("/contas")
class ContaController(
    private val service: ContaService
) {

    @GetMapping("/{id}/extrato")
    fun get(@PathVariable id: Long): ResponseEntity<Conta> = ResponseEntity.ok(service.extrato(id))

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