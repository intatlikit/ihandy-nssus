package com.nssus.ihandy.model.login

sealed interface LoginAction {
    data class TypingUsernameTextField(val text: String) : LoginAction
    data class TypingPasswordTextField(val text: String) : LoginAction
//    object SendEmptyUsername : LoginAction
//    object SendEmptyPassword : LoginAction
    data class ClickLoginButton(val username: String, val password: String) : LoginAction
}