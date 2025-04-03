package com.hansheung.mob22_mvi.core

fun String.crop(count: Int): String {
    if(this.length <= count){
        return this
    }
    return this.take(count) + "..."
}
