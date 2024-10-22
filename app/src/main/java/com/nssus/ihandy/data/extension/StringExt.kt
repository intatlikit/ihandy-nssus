package com.nssus.ihandy.data.extension

fun String.isErrorTextFieldWith(tfMaxLength: Int): Boolean = isNotEmpty() && tfMaxLength != length