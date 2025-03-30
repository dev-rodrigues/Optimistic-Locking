package br.com.pessimistic_locking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PessimisticLockingApplication

fun main(args: Array<String>) {
    runApplication<PessimisticLockingApplication>(*args)
}
