package ru.fdo.bank.finrez.employeeserviceview.search

import org.springframework.data.jpa.domain.Specification

interface SpecificationBuilder<T> {
    fun withCriteria(searchCriteria: SearchCriteria)
    fun withNestedAttributeCriteria(searchCriteriaNestedAttribute: SearchCriteriaNestedAttribute)
    fun buildSpecification() : Specification<T>?
}