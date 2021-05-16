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
    PERFORMANCE("績效考核"),
    HISTORY("")
}
