package br.com.pessimistic_locking.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Version
import java.math.BigDecimal

@Entity
data class Conta(
    @Id val id: Long,
    var saldo: BigDecimal,
    @Version
    var version: Long? = null
)