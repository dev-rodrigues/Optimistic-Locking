package br.com.pessimistic_locking.repository

import br.com.pessimistic_locking.entity.Conta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ContaRepository : JpaRepository<Conta, Long> {
    fun findByIdForUpdate(@Param("id") id: Long): Conta
}