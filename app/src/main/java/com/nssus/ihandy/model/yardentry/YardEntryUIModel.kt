package com.nssus.ihandy.model.yardentry

data class CoilDetailItem(
    var isMatched: Boolean = false,
    var isSelectedRemove: Boolean = false,
    // ใช้โมเดลเอพีไอนั้น
    var coilNo: String = "",
    var status: String = "",
    var dock: String = ""
)