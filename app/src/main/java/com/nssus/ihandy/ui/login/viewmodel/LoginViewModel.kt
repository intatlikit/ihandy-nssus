package com.nssus.ihandy.ui.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nssus.ihandy.data.constant.AppConstant.APP_TOKEN
import com.nssus.ihandy.data.constant.AppConstant.USER_ROLE
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_PACKING
import com.nssus.ihandy.data.constant.ValueConstant.USER_ROLE_SHIPPING
import com.nssus.ihandy.data.usecase.AuthenUseCase
import com.nssus.ihandy.data.usecase.HomeUseCase
import com.nssus.ihandy.model.login.LoginAction
import com.nssus.ihandy.model.login.LoginErrorModel
import com.nssus.ihandy.model.login.LoginErrorType
import com.nssus.ihandy.model.login.LoginNavigateType
import com.nssus.ihandy.model.login.LoginUIStateModel
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.launch

class LoginViewModel(
//    private val sharedPref: SharedPreferences,
    private val homeUc: HomeUseCase, // un comment this
//    private val authenUc: AuthenUseCase // comment this if not use
) : ViewModel() {
    private val _loginUISt = mutableStateOf(LoginUIStateModel())
    val loginUISt: State<LoginUIStateModel> = _loginUISt

    fun action(viewAction: LoginAction) {
        when (viewAction) {
            is LoginAction.ClickLoginButton -> login(viewAction.username, viewAction.password)
            is LoginAction.TypingPasswordTextField -> {

            }
            is LoginAction.TypingUsernameTextField -> {

            }
        }
    }

    private fun login(username:String, password: String) {
//        if (username.isEmpty()) {
//            _loginUISt.value = onLoginUIStateError(
//                loginErrorType = LoginErrorType.EMPTY_FILL_USERNAME,
//            )
//            return
//        }
        if (password.isEmpty()) {
            _loginUISt.value = onLoginUIStateError(
                loginErrorType = LoginErrorType.EMPTY_FILL_PASSWORD,
            )
            return
        }
        viewModelScope.launch {
            homeUc.getUserInfo().collect {
                when (it) {
//                    is NetworkResult.Success200, is NetworkResult.Success204 -> {
                    is NetworkResult.Success200 -> {
                        println("Status Code: 200")

                        USER_ROLE = if (username.isEmpty()) USER_ROLE_PACKING else USER_ROLE_SHIPPING // เกทจากค่าในเอพีไอ หรือจากซักที่ เพื่อเซต แต่ทำใน homeวิวโมเดล ไ่ม่ก้ของเมน

                        _loginUISt.value = onLoginUIStateSuccess(
                            navigateType = LoginNavigateType.GO_TO_MAIN
                        )
                    }
                    is NetworkResult.Success -> {
                        println("Status Code: ${it.statusCode}")
                        USER_ROLE = if (username.isEmpty()) USER_ROLE_PACKING else USER_ROLE_SHIPPING // เกทจากค่าในเอพีไอ หรือจากซักที่ เพื่อเซต แต่ทำใน homeวิวโมเดล ไ่ม่ก้ของเมน

                        _loginUISt.value = onLoginUIStateSuccess(
                            navigateType = LoginNavigateType.GO_TO_MAIN,
                            successMsg = "$username $password" //
                        )
                    }
                    is NetworkResult.Loading -> onLoginUIStateLoading()
                    is NetworkResult.Error -> {
                        _loginUISt.value = onLoginUIStateError(
                            errorMsg = it.errorMessage // .getDisplay(getCurrentLanguage())
                        )
                    }
                    else -> initNavigateData() // Status Code = 204
                }
            }
        }
    }

    fun initNavigateData() {
        _loginUISt.value = onLoginUIStateSuccess()
    }

    private fun onLoginUIStateSuccess(
        navigateType: LoginNavigateType = LoginNavigateType.NONE,
        successMsg: String? = null
    ): LoginUIStateModel = _loginUISt.value.copy(
        isLoading = false,
        isSuccess = true,
        navigateType = navigateType,
        successMsg = successMsg,
        isError = false,
        errorBody = null
    )

    private fun onLoginUIStateError(
        loginErrorType: LoginErrorType = LoginErrorType.ERROR_FROM_API,
        errorMsg: String? = null
    ): LoginUIStateModel = _loginUISt.value.copy(
        isLoading = false,
        isSuccess = false,
        navigateType = LoginNavigateType.NONE,
        successMsg = null,
        isError = true,
        errorBody = LoginErrorModel(
            loginErrorType = loginErrorType,
            errorMsg = errorMsg
        )
    )

    private fun onLoginUIStateLoading(isLoading: Boolean = true) {
        _loginUISt.value = _loginUISt.value.copy(isLoading = isLoading)
    }

}