package ru.fdo.bank.finrez.employeeserviceview.domain

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "employee")
data class Employee(@Id @Column(name = "employee_id") val employeeId : String,
                    @Column(name = "last_name") val lastName : String,
                    @Column(name = "first_name") val firstName : String,
                    @Column(name = "middle_name") val middleName : String,
                    @Column(name = "position") val position : String,
                    @Column(name = "date_of_hiring") val dateOfHiring : LocalDate,
                    @Column(name = "date_of_dismissal") val dateOfDismissal : LocalDate,
                    @Column(name = "office_id") val officeId : String)