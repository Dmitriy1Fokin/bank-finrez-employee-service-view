package ru.fdo.bank.finrez.employeeserviceview.search

data class SearchCriteriaNestedAttribute(val nestedObjectName: String,
                                         val key: String,
                                         val operation: Operations,
                                         val value: Any,
                                         val predicate: Boolean)