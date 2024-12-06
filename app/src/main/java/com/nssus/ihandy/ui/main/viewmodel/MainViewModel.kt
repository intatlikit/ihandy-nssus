package com.nssus.ihandy.ui.main.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nssus.ihandy.data.constant.AppConstant.MAIN_MENU
import com.nssus.ihandy.data.constant.AppConstant.USERNAME
import com.nssus.ihandy.data.constant.AppConstant.USER_ROLE
import com.nssus.ihandy.data.constant.HomeMenuConstant.PACKING_OP_MENU
import com.nssus.ihandy.data.constant.HomeMenuConstant.SHIPPING_OP_MENU
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_PACKING
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_SHIPPING
import com.nssus.ihandy.data.usecase.HomeUseCase
import com.nssus.ihandy.model.home.DisplayHomeModel
import com.nssus.ihandy.model.main.MainErrorModel
import com.nssus.ihandy.model.main.MainErrorType
import com.nssus.ihandy.model.main.MainNavigateType
import com.nssus.ihandy.model.main.MainUIStateModel
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.launch

class MainViewModel(
    private val homeUc: HomeUseCase
) : ViewModel() {
    private val _mainUISt = mutableStateOf(MainUIStateModel())
    val mainUISt: State<MainUIStateModel> = _mainUISt

    fun getUserRole() {
        viewModelScope.launch {
            homeUc.getUserInfo().collect {
                when (it) {
                    is NetworkResult.Success200 -> {
                        USER_ROLE = if (USERNAME.isEmpty()) USER_ROLE_PACKING else USER_ROLE_SHIPPING

                        MAIN_MENU = when (USER_ROLE) {
                            USER_ROLE_PACKING -> PACKING_OP_MENU
                            USER_ROLE_SHIPPING -> SHIPPING_OP_MENU
                            else -> {
//                                if (USER_ROLE.contains("PRODUCTION")) PRODUCTION_OP_MENU
//                                else DisplayHomeModel()
                                DisplayHomeModel()
                            }
                        }

                        _mainUISt.value = onMainUIStateSuccess()
                    }
                    is NetworkResult.Loading -> onMainUIStateLoading()
                    is NetworkResult.Error -> {
                        _mainUISt.value = onMainUIStateError(
                            errorMsg = it.errorMessage
                        )
                    }
                    else -> initNavigateData() // Status Code = 204 or other
                }
            }
        }
    }

    fun logout() {
        // Call Logout Api Before

        _mainUISt.value = onMainUIStateSuccess(
            navigateType = MainNavigateType.GO_TO_RESTART_APP
        )
    }

    fun initNavigateData() {
        _mainUISt.value = onMainUIStateSuccess()
    }

    private fun onMainUIStateSuccess(
        navigateType: MainNavigateType = MainNavigateType.NONE,
        successMsg: String? = null
    ): MainUIStateModel = _mainUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onMainUIStateError(
        mainErrorType: MainErrorType = MainErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): MainUIStateModel = _mainUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = MainNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = MainErrorModel(
            mainErrorType = mainErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onMainUIStateLoading(isLoading: Boolean = true) {
        _mainUISt.value = _mainUISt.value.copy(isLoading = isLoading)
    }
}