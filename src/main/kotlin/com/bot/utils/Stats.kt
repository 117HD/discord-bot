package com.bot.utils

import org.apache.commons.lang.StringUtils
import org.jsoup.Jsoup
import java.text.NumberFormat

object Stats {

    fun getRank(): String {
        return try {
            return  NumberFormat.getIntegerInstance().format(Jsoup.connect("https://api.phstatistics.com/rank/plugin/117hd")
                .userAgent("Mozilla").timeout(3000).ignoreContentType(true)
                .get().body().text().toInt())
        } catch (e: Exception) {
            e.printStackTrace()
            "2"
        }
    }

    fun getInstalls(): String {
        return try {
            return NumberFormat.getIntegerInstance().format(Jsoup.connect("https://api.phstatistics.com/installs/plugin/117hd")
                .userAgent("Mozilla").timeout(3000).ignoreContentType(true)
                .get().body().text().toInt())
        } catch (e: Exception) {
            e.printStackTrace()
            "2"
        }
    }

}