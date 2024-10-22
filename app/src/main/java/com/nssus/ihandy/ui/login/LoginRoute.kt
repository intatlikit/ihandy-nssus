package com.nssus.ihandy.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nssus.ihandy.R
import com.nssus.ihandy.model.login.LoginErrorType
import com.nssus.ihandy.model.login.LoginNavigateType
import com.nssus.ihandy.navigation.constant.GraphConstant
import com.nssus.ihandy.ui.basecomposable.CustomLoading
import com.nssus.ihandy.ui.basecomposable.FailWithDescriptionDialog
import com.nssus.ihandy.ui.login.viewmodel.LoginViewModel

@Composable
fun LoginRoute(
    navController: NavController,
    loginVm: LoginViewModel
) {
    val uiLoginSt by loginVm.loginUISt

    when {
        uiLoginSt.isLoading -> CustomLoading()
        uiLoginSt.isSuccess -> {
            when (uiLoginSt.navigateType) {
                LoginNavigateType.GO_TO_MAIN -> {
                    navController.navigate(GraphConstant.MAIN) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                    loginVm.initNavigateData()
                }
                else -> Unit
            }
        }
        uiLoginSt.isError -> {
            FailWithDescriptionDialog(
                description = (when (uiLoginSt.errorBody?.loginErrorType) {
                    LoginErrorType.ERROR_FROM_API -> uiLoginSt.errorBody?.errorMsg
                    else -> stringResource(id = uiLoginSt.errorBody?.loginErrorType?.errorMsgId ?: R.string.dash_string)
                }) ?: "-",
                onCloseDialog = loginVm::initNavigateData
            )
        }
    }

    LoginScreen(
        onAction = loginVm::action
    )
}