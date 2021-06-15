package com.rn1.puffren.util

import com.rn1.puffren.R
import com.rn1.puffren.util.Util.getString

enum class CurrentFragmentType(val value: String) {
    HOME(""),
    PRODUCT("商品列表"),
    DETAIL(""),
    CART("購物車"),
    LOCATION("攤商地圖"),
    LOGIN("登入"),
    REGISTRY("註冊"),
    PROFILE("會員頁面"),
    PERFORMANCE("歷史考核"),
    HISTORY(""),
    EDITMEMBERSHIP("會員資料修改"),
    EDITPASSWORD("修改密碼"),
    ACHIEVEMENT("會員成就"),
    ACTIVITY("會員專屬活動"),
    FOODCART(""),
    REPORT("營業狀況回報")
}
