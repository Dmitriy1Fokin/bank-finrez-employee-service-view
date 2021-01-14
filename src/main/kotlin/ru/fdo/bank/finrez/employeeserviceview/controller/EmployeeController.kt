package ru.fdo.bank.finrez.employeeserviceview.controller

import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.fdo.bank.finrez.employeeserviceview.domain.Employee
import ru.fdo.bank.finrez.employeeserviceview.exception.EmployeeNotFoundException
import ru.fdo.bank.finrez.employeeserviceview.service.EmployeeService
import java.util.concurrent.CompletableFuture
@RestController
@RequestMapping("/emp/view")
class EmployeeController(private val employeeService: EmployeeService) {

    @GetMapping("/{employeeId}")
    fun getEmployee(@PathVariable("employeeId") employeeId: String) : Employee {
        return employeeService.getEmployee(employeeId).get()
                .orElseThrow{EmployeeNotFoundException("Employee with id=$employeeId not found")}
    }

    @GetMapping("/search")
    fun getEmployeeByParams(@RequestParam searchParams: Map<String, String>, pageable: Pageable) : CompletableFuture<List<Employee>> =
            employeeService.getEmployeeByParams(searchParams, pageable)

    @GetMapping("/all")
    fun getAllEmployee(pageable: Pageable) : CompletableFuture<List<Employee>> =
            employeeService.getAllEmployee(pageable)

    @GetMapping("/office")
    fun getAllEmployeeByOffice(@RequestParam("officeId") officeId: String, pageable: Pageable) : CompletableFuture<List<Employee>> =
            employeeService.getAllEmployeeByOffice(officeId, pageable)

    @GetMapping("/offices")
    fun getAllEmployeeByOffices(@RequestParam("officeIds") officeIds: List<String>, pageable: Pageable) : CompletableFuture<List<Employee>> =
            employeeService.getAllEmployeeByOffices(officeIds, pageable)
}

