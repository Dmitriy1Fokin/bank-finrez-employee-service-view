package ru.fdo.bank.finrez.employeeserviceview.service

import org.springframework.data.domain.Pageable
import ru.fdo.bank.finrez.employeeserviceview.domain.Employee
import java.util.*
import java.util.concurrent.CompletableFuture

interface EmployeeService {
    fun getEmployee(employeeId: String) : CompletableFuture<Optional<Employee>>
    fun getEmployeeByParams(searchParameters : Map<String, String>, pageable: Pageable) : CompletableFuture<List<Employee>>
    fun getAllEmployee(pageable: Pageable) : CompletableFuture<List<Employee>>
    fun getAllEmployeeByOffice(officeId: String, pageable: Pageable) : CompletableFuture<List<Employee>>
    fun getAllEmployeeByOffices(officeIds: List<String>, pageable: Pageable) : CompletableFuture<List<Employee>>
}