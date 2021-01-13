package ru.fdo.bank.finrez.employeeserviceview.search

data class SearchCriteria(val key: String,
                          val operation: Operations,
                          val value: Any,
                          val predicate: Boolean)