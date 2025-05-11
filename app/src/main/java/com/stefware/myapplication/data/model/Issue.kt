package com.stefware.myapplication.data.model

data class Issue(
    val id: Int = 0,
    val title: String = "",
    val sprintAssociate: String = "",
    val description: String = "",
    val status: String = "",
    val priority: String = "",
    val assignedTo: String = "",
    val madeBy: String = "",
    val resolutionDate: String? = null,
    val createdIn: String = "",
    val history: List<History> = emptyList()
)