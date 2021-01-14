package ru.fdo.bank.finrez.employeeserviceview.service.impl

import lombok.extern.slf4j.Slf4j
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.query.FindAllEmployeesByOfficeQuery
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.query.FindAllEmployeesByOfficesQuery
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.query.FindAllEmployeesQuery
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.query.FindEmployeeByIdQuery
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.query.FindEmployeeByParamsQuery
import ru.fdo.bank.finrez.employeeserviceview.domain.Employee
import ru.fdo.bank.finrez.employeeserviceview.service.EmployeeService
import java.util.*
import java.util.concurrent.CompletableFuture

@Service
class EmployeeServiceImpl(private val queryGateway: QueryGateway) : EmployeeService {

    private val logger : Logger = LoggerFactory.getLogger(EmployeeServiceImpl::class.java)

    override fun getEmployee(employeeId: String): CompletableFuture<Optional<Employee>> {
        logger.debug("triggered getEmployee: employeeId=$employeeId")
        return queryGateway.query(FindEmployeeByIdQuery(employeeId), ResponseTypes.optionalInstanceOf(Employee::class.java))
    }

    override fun getEmployeeByParams(searchParameters: Map<String, String>, pageable: Pageable): CompletableFuture<List<Employee>> {
        logger.debug("triggered getEmployeeByParams: searchParameters=$searchParameters, pageable=$pageable")
        return queryGateway.query(FindEmployeeByParamsQuery(searchParameters, pageable), ResponseTypes.multipleInstancesOf(Employee::class.java))
    }

    override fun getAllEmployee(pageable: Pageable): CompletableFuture<List<Employee>> {
        logger.debug("triggered getAllEmployee: pageable=$pageable")
        return queryGateway.query(FindAllEmployeesQuery(pageable), ResponseTypes.multipleInstancesOf(Employee::class.java))
    }

    override fun getAllEmployeeByOffice(officeId: String, pageable: Pageable): CompletableFuture<List<Employee>> {
        logger.debug("triggered getAllEmployeeByOffice: officeId=$officeId, pageable=$pageable")
        return queryGateway.query(FindAllEmployeesByOfficeQuery(officeId, pageable), ResponseTypes.multipleInstancesOf(Employee::class.java))
    }

    override fun getAllEmployeeByOffices(officeIds: List<String>, pageable: Pageable): CompletableFuture<List<Employee>> {
        logger.debug("triggered getAllEmployeeByOffices: officeIds=$officeIds, pageable=$pageable")
        return queryGateway.query(FindAllEmployeesByOfficesQuery(officeIds, pageable), ResponseTypes.multipleInstancesOf(Employee::class.java))
    }
}