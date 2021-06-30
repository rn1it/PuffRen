package com.rn1.puffren.util


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
    EVENT("會員專屬活動"),
    FOODCART(""),
    REPORT("營業狀況回報"),
    FOODCARTORDER("餐車服務預約"),
    QRCODE("會員消費累積")
}
