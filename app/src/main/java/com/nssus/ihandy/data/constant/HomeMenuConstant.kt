package com.nssus.ihandy.data.constant

import com.nssus.ihandy.R
import com.nssus.ihandy.model.home.DisplayHomeModel
import com.nssus.ihandy.model.home.HomeMenuItem

object HomeMenuConstant {
    val PACKING_OP_MENU = DisplayHomeModel(
        titleId = R.string.home_user_role_packing_title,
        menuLs = listOf(
            HomeMenuItem.InventoryTakingScreen,
            HomeMenuItem.YardEntryScreen
        )
    )

    val SHIPPING_OP_MENU = DisplayHomeModel(
        titleId = R.string.home_user_role_shipping_title,
        menuLs = listOf(
            HomeMenuItem.YardEntryScreen,
            HomeMenuItem.InventoryTakingScreen
        )
    )
}