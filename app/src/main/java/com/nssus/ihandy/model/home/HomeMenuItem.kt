package com.nssus.ihandy.model.home

import androidx.annotation.StringRes
import com.nssus.ihandy.R
import com.nssus.ihandy.navigation.constant.GraphConstant
import java.io.Serializable

sealed class HomeMenuItem(
    @StringRes val menuNameId: Int,
    val route: String
) : Serializable {
    object YardEntryScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_yard_entry_title,
            route = GraphConstant.YARD_ENTRY
        )

    object InventoryTakingScreen :
        HomeMenuItem(
            menuNameId = R.string.menu_inventory_taking_title,
            route = GraphConstant.INVENTORY_TAKING
        )
}