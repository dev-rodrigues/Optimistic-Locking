package br.com.pessimistic_locking.controller

import br.com.pessimistic_locking.entity.Conta
import br.com.pessimistic_locking.service.ContaService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/contas")
class ContaController(
    private val service: ContaService
) {
    @GetMapping("/{id}/extrato")
    fun get(@PathVariable id: Long): ResponseEntity<Conta> = ResponseEntity.ok(service.extrato(id))
}