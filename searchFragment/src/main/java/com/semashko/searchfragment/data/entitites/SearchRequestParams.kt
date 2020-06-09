package com.semashko.searchfragment.data.entitites

import com.semashko.searchfragment.presentation.viewmodels.FIRST_PAGE_NUMBER

data class SearchRequestParams(
    val searchText: String = "",
    val pageNumber: Int = FIRST_PAGE_NUMBER
)