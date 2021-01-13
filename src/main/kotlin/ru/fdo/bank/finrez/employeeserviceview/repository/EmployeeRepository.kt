package ru.fdo.bank.finrez.employeeserviceview.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import ru.fdo.bank.finrez.employeeserviceview.domain.Employee

interface EmployeeRepository : JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee>