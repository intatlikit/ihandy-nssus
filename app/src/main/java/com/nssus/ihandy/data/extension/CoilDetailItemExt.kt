package com.nssus.ihandy.data.extension

import com.nssus.ihandy.model.yardentry.CoilDetailItem

fun List<CoilDetailItem>.setSelectItemFrom(searchCoilNo: String) {
    apply {
        getSelectedItem()?.let { it.isMatched = false }
        find { searchCoilNo == it.coilNo }?.let { it.isMatched = true }
    }
}

fun List<CoilDetailItem>.getSelectedItem(): CoilDetailItem? = find { it.isMatched }

//fun List<CoilDetailItem>.getSelectedItemValue(): String? = getSelectedItem()?.coilNo // ถ้าเป็นเรสปอนเอพีไอ ค่อย dot เข้าไปอีกชั้นเพื่อเอาค่า coil เป็นต้น

fun CoilDetailItem?.isNull(): Boolean = this == null

fun CoilDetailItem?.isNotNull(): Boolean = isNull().not()