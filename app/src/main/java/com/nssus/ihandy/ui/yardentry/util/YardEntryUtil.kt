package com.nssus.ihandy.ui.yardentry.util

import com.nssus.ihandy.model.ui.DropdownUIModel

object YardEntryUtil {

    fun getDataLs(): List<DropdownUIModel> = listOf(
        DropdownUIModel(
            value = "12",
            display = "Product Label",
            isSelected = true
        ),
        DropdownUIModel(
            value = "13",
            display = "Checking Label"
        )
    )

}