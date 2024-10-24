package com.nssus.ihandy.data.extension

fun String.isErrorTextFieldWith(tfMaxLength: Int): Boolean = isNotEmpty() && isNotEqualsMaxLength(tfMaxLength)


//fun String.isEqualsMaxLength(tfMaxLength: Int): Boolean = this.length == tfMaxLength
fun String.isEqualsMaxLength(tfMaxLength: Int): Boolean = length == tfMaxLength

//fun String.isEqualsMaxLength(tfMaxLength: Int): Boolean {
//    return length == tfMaxLength
//}

fun isEqualMaxLength(text: String, tfMaxLength: Int): Boolean { //
    return text.length == tfMaxLength
}




fun String.isNotEqualsMaxLength(tfMaxLength: Int): Boolean = isEqualsMaxLength(tfMaxLength).not() //  เหมือนกับ !isEqualsMaxLength()