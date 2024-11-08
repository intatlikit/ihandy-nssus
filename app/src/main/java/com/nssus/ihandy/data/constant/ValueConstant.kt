package com.nssus.ihandy.data.constant

object ValueConstant {

    // User Roles
    const val USER_ROLE_PACKING = "P" // แก้ตามค่าที่ส่งมาดัก
    const val USER_ROLE_SHIPPING = "S" // แก้ตามค่าที่ส่งมาดัก

    // Network Timeout
    const val TIME_OUT = 120L

    // Network Request Header Key
    const val REQ_HEADER_KEY_CONTENT_TYPE = "Content-Type"
    const val REQ_HEADER_LANGUAGE = "Language"
    const val REQ_HEADER_AUTHORIZATION = "Authorization"
    const val REQ_HEADER_USER_AGENT = "User-Agent"

    // Network Request Header Value
    const val REQ_HEADER_VALUE_CONTENT_TYPE = "application/json; charset=UTF-8"

    const val PREFIX_TOKEN = "Bearer " // or Bearer (no end space)

    // Network Response Status Code
    const val STATUS_CODE_SUCCESS = "S"
    const val STATUS_CODE_FAILED = "F"
    const val STATUS_CODE_200 = "200"
    const val STATUS_CODE_204 = "204"
    const val STATUS_CODE_TOKEN_EXPIRED = "STATUS_CODE_TOKEN_EXPIRED"
    const val STATUS_CODE_NO_INTERNET = "STATUS_CODE_NO_INTERNET"
    const val STATUS_CODE_SOCKET_TIME_OUT = "STATUS_CODE_SOCKET_TIME_OUT"
    const val STATUS_CODE_ERROR = "999"

    // Text Pattern
    const val PATTERN_NUMBER = "^[0-9]*$"

}