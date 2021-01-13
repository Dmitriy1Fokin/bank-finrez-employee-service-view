package ru.fdo.bank.finrez.employeeserviceview.search.impl

import org.springframework.data.jpa.domain.Specification
import ru.fdo.bank.finrez.employeeserviceview.search.Operations
import ru.fdo.bank.finrez.employeeserviceview.search.SearchCriteria
import java.time.LocalDate
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class SpecificationImpl<T>(private val criteria: SearchCriteria) : Specification<T> {

    override fun toPredicate(root: Root<T>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? {
        if(criteria.operation == Operations.GREATER){
            return when(criteria.value::class){
                String::class -> criteriaBuilder.greaterThan(root.get(criteria.key), criteria.value.toString())
                LocalDate::class -> criteriaBuilder.greaterThan(root.get(criteria.key), criteria.value as LocalDate)
                else -> null
            }
        }
        if(criteria.operation == Operations.GREATER_EQUAL){
            return when(criteria.value::class){
                String::class -> criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.key), criteria.value.toString())
                LocalDate::class -> criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.key), criteria.value as LocalDate)
                else -> null
            }
        }
        if(criteria.operation == Operations.LESS){
            return when(criteria.value::class){
                String::class -> criteriaBuilder.lessThan(root.get(criteria.key), criteria.value.toString())
                LocalDate::class -> criteriaBuilder.lessThan(root.get(criteria.key), criteria.value as LocalDate)
                else -> null
            }
        }
        if(criteria.operation == Operations.LESS_EQUAL){
            return when(criteria.value::class){
                String::class -> criteriaBuilder.lessThanOrEqualTo(root.get(criteria.key), criteria.value.toString())
                LocalDate::class -> criteriaBuilder.lessThanOrEqualTo(root.get(criteria.key), criteria.value as LocalDate)
                else -> null
            }
        }
        if(criteria.operation == Operations.EQUAL_IGNORE_CASE){
            return when(criteria.value::class){
                String::class -> criteriaBuilder.like(criteriaBuilder.lower(root.get(criteria.key)), "%${criteria.value.toString().toLowerCase()}%")
                else -> criteriaBuilder.equal(root.get<Any>(criteria.key), criteria.value)
            }
        }
        return null
    }
}