package br.com.pessimistic_locking.repository

import br.com.pessimistic_locking.entity.Conta
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ContaRepository : JpaRepository<Conta, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Conta c WHERE c.id = :id")
    fun findByIdForUpdate(@Param("id") id: Long): Conta
}