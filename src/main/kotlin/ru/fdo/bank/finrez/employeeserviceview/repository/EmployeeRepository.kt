package ru.fdo.bank.finrez.employeeserviceview.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import ru.fdo.bank.finrez.employeeserviceview.domain.Employee

interface EmployeeRepository : JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee>{
    fun findAllByOfficeId(officeId: String, pageable: Pageable) : Page<Employee>
    fun findAllByOfficeIdIn(officeIds: List<String>, pageable: Pageable) : Page<Employee>
}