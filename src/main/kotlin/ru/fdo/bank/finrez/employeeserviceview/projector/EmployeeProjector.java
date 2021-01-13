package ru.fdo.bank.finrez.employeeserviceview.projector;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.event.EmployeeCreatedEvent;
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.event.EmployeeFiredEvent;
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.event.EmployeeUpdatedEvent;
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.event.OfficeChangedEvent;
import ru.fdo.bank.finrez.employeeservicecommon.coreapi.event.PositionChangedEvent;
import ru.fdo.bank.finrez.employeeserviceview.domain.Employee;
import ru.fdo.bank.finrez.employeeserviceview.repository.EmployeeRepository;

import java.util.Optional;

@Slf4j
@Component
public class EmployeeProjector {

    private final EmployeeRepository employeeRepository;

    public EmployeeProjector(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @EventHandler
    public void on(EmployeeCreatedEvent event){
        log.debug("triggered EmployeeCreatedEvent: {}", event);
        final Employee employee = new Employee(event.getEmployeeId(),
                event.getLastName(),
                event.getFirstName(),
                event.getMiddleName(),
                event.getPosition(),
                event.getDateOfHiring(),
                null,
                event.getOfficeId());
        employeeRepository.save(employee);
    }

    @Transactional
    @EventHandler
    public void on(EmployeeUpdatedEvent event){
        log.debug("triggered EmployeeUpdatedEvent: {}", event);
        Optional<Employee> employee = employeeRepository.findById(event.getEmployeeId());
        employee.ifPresent(emp -> {
          emp.setLastName(event.getLastName());
          emp.setFirstName(event.getFirstName());
          emp.setMiddleName(event.getMiddleName());
          employeeRepository.save(emp);
        });
    }

    @Transactional
    @EventHandler
    public void on(EmployeeFiredEvent event){
        log.debug("triggered EmployeeFiredEvent: {}", event);
        Optional<Employee> employee = employeeRepository.findById(event.getEmployeeId());
        employee.ifPresent(emp -> {
            emp.setDateOfDismissal(event.getDateOfDismissal());
            employeeRepository.save(emp);
        });
    }

    @Transactional
    @EventHandler
    public void on(OfficeChangedEvent event){
        log.debug("triggered OfficeChangedEvent: {}", event);
        Optional<Employee> employee = employeeRepository.findById(event.getEmployeeId());
        employee.ifPresent(emp -> {
            emp.setOfficeId(event.getOfficeId());
            employeeRepository.save(emp);
        });
    }

    @Transactional
    @EventHandler
    public void on(PositionChangedEvent event){
        log.debug("triggered PositionChangedEvent: {}", event);
        Optional<Employee> employee = employeeRepository.findById(event.getEmployeeId());
        employee.ifPresent(emp -> {
            emp.setPosition(event.getPosition());
            employeeRepository.save(emp);
        });
    }

}
