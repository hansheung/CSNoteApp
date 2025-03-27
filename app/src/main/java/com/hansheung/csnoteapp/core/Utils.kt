package com.hansheung.mob22_mvi.core

object Utils {
    fun formatTime(time:Int): String{ //Time in seconds
        //format hh:mm:ss
        var ss = time
        val hh = ss/(3600)
        ss = ss%3600
        val mm = ss/60
        ss = ss%60
        return String.format("%02d:%02d:%02d", hh, mm, ss)
    }
}

/*
  3650s
  3650/(60*60) = 1
  mm = 3650/60 = 0
  3650%60 = 50
 */