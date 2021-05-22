package com.rn1.puffren.data

enum class Day(val value: String) {
    TODAY("today"),
    TOMORROW("tomorrow")
}

enum class CouponType(val value: String){
    ALL("all"), //"所有優惠券" 包含通用、餐車、宅配
    VENDOR("vendor"), //"餐車可使用的優惠券" 包含通用、餐車，用於產出 QR Code 前取得用戶所有優惠券
    DELIVERY("delivery") //"宅配可使用的優惠券" 包含通用、宅配，用於購物車結帳前取得用戶所有優惠券

}