package ru.fdo.bank.finrez.employeeserviceview.projector;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.query.FindAllEmployeesByOfficeQuery;
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.query.FindAllEmployeesByOfficesQuery;
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.query.FindAllEmployeesQuery;
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.query.FindEmployeeByIdQuery;
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.query.FindEmployeeByParamsQuery;
import ru.fdo.bank.finrez.employeeserviceview.domain.Employee;
import ru.fdo.bank.finrez.employeeserviceview.repository.EmployeeRepository;
import ru.fdo.bank.finrez.employeeserviceview.search.Search;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class EmployeeQuery {

    private final EmployeeRepository employeeRepository;

    public EmployeeQuery(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @QueryHandler
    public Optional<Employee> handle(FindEmployeeByIdQuery query){
        log.debug("triggered FindEmployeeByIdQuery: {}", query);
        return employeeRepository.findById(query.getEmployeeId());
    }

    @QueryHandler
    public List<Employee> handle(FindEmployeeByParamsQuery query) throws ReflectiveOperationException {
        log.debug("triggered FindEmployeeByParamsQuery: {}", query);

        final Map<String, String> searchParam = query.getSearchParameters();

        Search<Employee> employeeSearch = new Search<>(Employee.class);

        employeeSearch.withParam(searchParam);

        Specification<Employee> specification = employeeSearch.getSpecification();

        return employeeRepository.findAll(specification, query.getPageable()).getContent();
    }

    @QueryHandler
    public List<Employee> handle(FindAllEmployeesQuery query){
        log.debug("triggered FindAllEmployeesQuery: {}", query);
        return employeeRepository.findAll(query.getPageable()).getContent();
    }

    @QueryHandler
    public List<Employee> handle(FindAllEmployeesByOfficeQuery query){
        log.debug("triggered FindAllEmployeesByOfficeQuery: {}", query);
        return employeeRepository.findAllByOfficeId(query.getOfficeId(), query.getPageable()).getContent();
    }

    @QueryHandler
    public List<Employee> handle(FindAllEmployeesByOfficesQuery query){
        log.debug("triggered FindAllEmployeesByOfficesQuery: {}", query);
        return employeeRepository.findAllByOfficeIdIn(query.getOfficeIds(), query.getPageable()).getContent();
    }
}
