package com.bot.utils

import org.apache.commons.lang.StringUtils
import org.jsoup.Jsoup
import java.text.DecimalFormat

import java.text.NumberFormat





object Installs {

    /*
     * Get the Installs from the plugin hub
     */
    fun getInstalls(): String {
        val runeliteVersion = getRuneliteVerison()
        if (runeliteVersion == "ERROR") {
            return "0"
        }
        return try {
            val doc = Jsoup.connect("https://api.runelite.net/runelite-${runeliteVersion}/pluginhub")
                .userAgent("Mozilla").timeout(3000).ignoreContentType(true)
                .get().body().text()

            NumberFormat.getIntegerInstance().format(StringUtils.substringBetween(doc, "\"117hd\":", ",").toInt())
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /*
   * Get the Runelite Version
   */
    private fun getRuneliteVerison(): String {
        return try {
            val doc = Jsoup.connect("https://static.runelite.net/bootstrap.json")
                .userAgent("Mozilla").timeout(3000).ignoreContentType(true)
                .get()
            StringUtils.substringBetween(doc.body().text(), "\"version\": \"", "\" }")
        } catch (e: Exception) {
            e.printStackTrace()
            "ERROR"
        }
    }

}