package ru.fdo.bank.finrez.employeeserviceview.search.impl

import org.springframework.data.jpa.domain.Specification
import ru.fdo.bank.finrez.employeeserviceview.search.Operations
import ru.fdo.bank.finrez.employeeserviceview.search.SearchCriteriaNestedAttribute
import java.time.LocalDate
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Join
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class SpecificationNestedAttributeImpl<T, N>(private val criteria: SearchCriteriaNestedAttribute) : Specification<T> {

    override fun toPredicate(root: Root<T>, criteriaQuery: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? {
        val join: Join<T, N> = root.join(criteria.nestedObjectName)
        if(criteria.operation == Operations.GREATER){
            return when(criteria.value::class){
                String::class -> criteriaBuilder.greaterThan(join.get(criteria.key), criteria.value.toString())
                LocalDate::class -> criteriaBuilder.greaterThan(join.get(criteria.key), criteria.value as LocalDate)
                else -> null
            }
        }
        if(criteria.operation == Operations.GREATER_EQUAL){
            return when(criteria.value::class){
                String::class -> criteriaBuilder.greaterThanOrEqualTo(join.get(criteria.key), criteria.value.toString())
                LocalDate::class -> criteriaBuilder.greaterThanOrEqualTo(join.get(criteria.key), criteria.value as LocalDate)
                else -> null
            }
        }
        if(criteria.operation == Operations.LESS){
            return when(criteria.value::class){
                String::class -> criteriaBuilder.lessThan(join.get(criteria.key), criteria.value.toString())
                LocalDate::class -> criteriaBuilder.lessThan(join.get(criteria.key), criteria.value as LocalDate)
                else -> null
            }
        }
        if(criteria.operation == Operations.LESS_EQUAL){
            return when(criteria.value::class){
                String::class -> criteriaBuilder.lessThanOrEqualTo(join.get(criteria.key), criteria.value.toString())
                LocalDate::class -> criteriaBuilder.lessThanOrEqualTo(join.get(criteria.key), criteria.value as LocalDate)
                else -> null
            }
        }
        if(criteria.operation == Operations.EQUAL_IGNORE_CASE){
            return when(criteria.value::class){
                String::class -> criteriaBuilder.like(criteriaBuilder.lower(join.get(criteria.key)), "%${criteria.value.toString().toLowerCase()}%")
                else -> criteriaBuilder.equal(join.get<Any>(criteria.key), criteria.value)
            }
        }
        return null
    }
}