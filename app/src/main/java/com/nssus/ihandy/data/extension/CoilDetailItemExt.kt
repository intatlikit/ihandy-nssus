package com.nssus.ihandy.data.extension

import com.nssus.ihandy.model.yardentry.CoilDetailItem

fun List<CoilDetailItem>.setMatchedItemFrom(searchCoilNo: String) {
    apply {
        getMatchedItem()?.let { it.isMatched = false }
        find { searchCoilNo == it.coilNo }?.let { it.isMatched = true }
    }
}

fun List<CoilDetailItem>.setSelectedRemoveFrom(selectedCoilDetl: CoilDetailItem): List<CoilDetailItem> =
    apply {
        find { it.coilNo == selectedCoilDetl.coilNo }
            ?.let { it.isSelectedRemove = it.isSelectedRemove.not() }
    }

fun List<CoilDetailItem>.getMatchedItem(): CoilDetailItem? = find { it.isMatched }

//fun List<CoilDetailItem>.getMatchedItemCoilNo(): String? = getSelectedItem()?.coilNo // ถ้าเป็นเรสปอนเอพีไอ ค่อย dot เข้าไปอีกชั้นเพื่อเอาค่า coil เป็นต้น

fun CoilDetailItem?.isNull(): Boolean = this == null

fun CoilDetailItem?.isNotNull(): Boolean = isNull().not()