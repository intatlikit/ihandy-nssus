package com.nssus.ihandy.ui.main.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nssus.ihandy.data.usecase.AuthenUseCase
import com.nssus.ihandy.model.main.MainErrorModel
import com.nssus.ihandy.model.main.MainErrorType
import com.nssus.ihandy.model.main.MainNavigateType
import com.nssus.ihandy.model.main.MainUIStateModel

class MainViewModel(
//    private val authenUc: AuthenUseCase
) : ViewModel() {
    private val _mainUISt = mutableStateOf(MainUIStateModel())
    val mainUISt: State<MainUIStateModel> = _mainUISt

    fun getUserRole() {
//        USER_ROLE = USER_ROLE_PACKING // USER_ROLE_SHIPPING
        // call api userinfop
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