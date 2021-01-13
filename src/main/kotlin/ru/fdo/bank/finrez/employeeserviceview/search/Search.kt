package ru.fdo.bank.finrez.employeeserviceview.search

import org.springframework.data.jpa.domain.Specification
import ru.fdo.bank.finrez.employeeserviceview.search.impl.SpecificationBuilderImpl
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Search<T>(clazz: Class<T>) {
    private val clazz = clazz
    private val builder = SpecificationBuilderImpl<T>()
    private val SEARCH_PARAM_POSTFIX = "Option"

    @Throws(ReflectiveOperationException::class)
   fun withParam(searchParam: Map<String, String>){
        for (field in clazz.declaredFields){
            if (searchParam.containsKey(field.name) && !searchParam[field.name].isNullOrEmpty()){

                val key = field.name
                val isPredicate = false

                if(field.type == String::class.java || field.type.superclass == Number::class.java){
                    val value = searchParam[key] ?: ""
                    val operation = searchParam["${key}$SEARCH_PARAM_POSTFIX"]?.let { Operations.valueOf(it) } ?: Operations.EQUAL_IGNORE_CASE
                    val searchCriteria = SearchCriteria(key, operation, value, isPredicate)
                    builder.withCriteria(searchCriteria)

                }else if (field.type.superclass == Enum::class.java){
                    val enumClass = field.type
                    val methodValueOf = enumClass.getMethod("valueOf", String::class.java)
                    val value = methodValueOf.invoke(enumClass, searchParam[key])
                    val operation = Operations.EQUAL_IGNORE_CASE
                    val searchCriteria = SearchCriteria(key, operation, value, isPredicate)
                    builder.withCriteria(searchCriteria)

                }else if (field.type == LocalDate::class.java){
                    val dateFormatter = DateTimeFormatter.ISO_DATE
                    val value = LocalDate.parse(searchParam[key], dateFormatter)
                    val operation = searchParam["${key}$SEARCH_PARAM_POSTFIX"]?.let { Operations.valueOf(it) } ?: Operations.EQUAL_IGNORE_CASE
                    val searchCriteria = SearchCriteria(key, operation, value, isPredicate)
                    builder.withCriteria(searchCriteria)

                }
            }
        }
    }

    fun withNestedAttributeParam(searchParam: Map<String, String>, nestedAttribute: String){
        val nestedClass = clazz.getDeclaredField(nestedAttribute).type
        for (field in nestedClass.declaredFields){
            if (searchParam.containsKey(field.name) && !searchParam[field.name].isNullOrEmpty()){

                val key = field.name
                val isPredicate = false

                if(field.type == String::class.java || field.type.superclass == Number::class.java){
                    val value = searchParam[key] ?: ""
                    val operation = searchParam["${key}$SEARCH_PARAM_POSTFIX"]?.let { Operations.valueOf(it) } ?: Operations.EQUAL_IGNORE_CASE
                    val searchCriteria = SearchCriteriaNestedAttribute(nestedAttribute, key, operation, value, isPredicate)
                    builder.withNestedAttributeCriteria(searchCriteria)

                }else if (field.type.superclass == Enum::class.java){
                    val enumClass = field.type
                    val methodValueOf = enumClass.getMethod("valueOf", String::class.java)
                    val value = methodValueOf.invoke(enumClass, searchParam[key])
                    val operation = Operations.EQUAL_IGNORE_CASE
                    val searchCriteria = SearchCriteriaNestedAttribute(nestedAttribute, key, operation, value, isPredicate)
                    builder.withNestedAttributeCriteria(searchCriteria)

                }else if (field.type == LocalDate::class.java){
                    val dateFormatter = DateTimeFormatter.ISO_DATE
                    val value = LocalDate.parse(searchParam[key], dateFormatter)
                    val operation = searchParam["${key}$SEARCH_PARAM_POSTFIX"]?.let { Operations.valueOf(it) } ?: Operations.EQUAL_IGNORE_CASE
                    val searchCriteria = SearchCriteriaNestedAttribute(nestedAttribute, key, operation, value, isPredicate)
                    builder.withNestedAttributeCriteria(searchCriteria)

                }
            }
        }
    }

    fun getSpecification() : Specification<T>? = builder.buildSpecification()
}