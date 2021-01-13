package ru.fdo.bank.finrez.employeeserviceview.search.impl

import org.springframework.data.jpa.domain.Specification
import ru.fdo.bank.finrez.employeeserviceview.search.SearchCriteria
import ru.fdo.bank.finrez.employeeserviceview.search.SearchCriteriaNestedAttribute
import ru.fdo.bank.finrez.employeeserviceview.search.SpecificationBuilder

class SpecificationBuilderImpl<T> : SpecificationBuilder<T> {

    private val searchCriteriaList = mutableListOf<SearchCriteria>()
    private val searchCriteriaNestedAttributeList = mutableListOf<SearchCriteriaNestedAttribute>()


    override fun withCriteria(searchCriteria: SearchCriteria) {
        searchCriteriaList.add(searchCriteria)
    }

    override fun withNestedAttributeCriteria(searchCriteriaNestedAttribute: SearchCriteriaNestedAttribute) {
        searchCriteriaNestedAttributeList.add(searchCriteriaNestedAttribute)
    }

    override fun buildSpecification(): Specification<T>? {
        if(searchCriteriaList.isEmpty() && searchCriteriaNestedAttributeList.isEmpty()){
            return null
        }

        val specs: List<Specification<T>> = searchCriteriaList.map { SpecificationImpl<T>(it) }.toList()
        val specsNested: List<Specification<T>> = searchCriteriaNestedAttributeList
                .map { SpecificationNestedAttributeImpl<T, Any>(it) }.toList()

        var result: Specification<T> = specs[0]

        for (i in 1 until searchCriteriaList.size){
            result = if (searchCriteriaList[i].predicate)
                Specification.where(result).or(specs[i])
            else
                Specification.where(result).and(specs[i])
        }

        for (i in searchCriteriaNestedAttributeList.indices) {
            result = if (searchCriteriaNestedAttributeList[i].predicate)
                Specification.where(result).or(specsNested[i])
            else
                Specification.where(result).and(specsNested[i])
        }

        return result
    }
}