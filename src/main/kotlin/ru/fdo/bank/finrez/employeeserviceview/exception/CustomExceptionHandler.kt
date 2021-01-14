package ru.fdo.bank.finrez.employeeserviceview.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime


@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    private val TIMESTAMP = "timestamp"
    private val STATUS = "status"
    private val MESSAGE = "message"

    @ExceptionHandler(EmployeeNotFoundException::class)
    fun handleEmployeeNotFoundException(ex: Exception): ResponseEntity<Any> {

        val body = mapOf(TIMESTAMP to LocalDateTime.now(),
                STATUS to HttpStatus.NOT_FOUND.value(),
                MESSAGE to ex.message)
        return ResponseEntity(body, HttpHeaders(), HttpStatus.NOT_FOUND)
    }


}