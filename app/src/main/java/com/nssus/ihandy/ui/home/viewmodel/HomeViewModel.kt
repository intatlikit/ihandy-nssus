package com.nssus.ihandy.ui.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nssus.ihandy.data.constant.AppConstant.USER_ROLE
import com.nssus.ihandy.data.constant.HomeMenuConstant
import com.nssus.ihandy.data.constant.HomeMenuConstant.PACKING_OP_MENU
import com.nssus.ihandy.data.constant.HomeMenuConstant.PRODUCTION_OP_MENU
import com.nssus.ihandy.data.constant.HomeMenuConstant.SHIPPING_OP_MENU
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_PACKING
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_SHIPPING
import com.nssus.ihandy.model.home.DisplayHomeModel
import com.nssus.ihandy.model.home.HomeErrorModel
import com.nssus.ihandy.model.home.HomeErrorType
import com.nssus.ihandy.model.home.HomeMenuItem
import com.nssus.ihandy.model.home.HomeNavigateType
import com.nssus.ihandy.model.home.HomeUIStateModel

class HomeViewModel(

) : ViewModel() {
    private val _homeUISt = mutableStateOf(HomeUIStateModel())
    val homeUISt: State<HomeUIStateModel> = _homeUISt

    fun getMenuData() {
        _homeUISt.value = HomeUIStateModel( // init state model
            menuData = when (USER_ROLE) {
                USER_ROLE_PACKING -> PACKING_OP_MENU
                USER_ROLE_SHIPPING -> SHIPPING_OP_MENU
                else -> {
                    if (USER_ROLE.contains("PRODUCTION")) PRODUCTION_OP_MENU
                    else DisplayHomeModel()
                }
            }
        )
    }

    fun selectMenu(selectedMenu: HomeMenuItem) {
        _homeUISt.value = onHomeUIStateSuccess(
            navigateType = HomeNavigateType.GO_TO_SELECTED_MENU
        ).copy(
            selectedRoute = selectedMenu.route
        )
    }

    fun initNavigateData() {
        _homeUISt.value = onHomeUIStateSuccess()
    }

    private fun onHomeUIStateSuccess(
        navigateType: HomeNavigateType = HomeNavigateType.NONE,
        successMsg: String? = null
    ): HomeUIStateModel = _homeUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onHomeUIStateError(
        homeErrorType: HomeErrorType = HomeErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): HomeUIStateModel = _homeUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = HomeNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = HomeErrorModel(
            homeErrorType = homeErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onHomeUIStateLoading(isLoading: Boolean = true) {
        _homeUISt.value = _homeUISt.value.copy(isLoading = isLoading)
    }
}