package com.bot

import mu.KotlinLogging
import java.io.File
import java.util.*
import kotlin.system.exitProcess

object PropertiesManager {

    private val logger = KotlinLogging.logger {}
    var properties : Properties? = null

    fun loadProps() {

        if(!File("./config.properties").exists()) {
            error("config.properties Missing in Root Dir")
            exitProcess(0)
        }
        properties = Properties()
        with(properties) { this?.load(File("./config.properties").inputStream()) }
        logger.info { "Properties Loaded" }
    }

}

enum class PropertiesData(val key : String) {
    BOT_KEY("bot.key");

    companion object {
        fun getPropString(prop : PropertiesData) : String? {
            if(PropertiesManager.properties == null) {
                error("Unable to get Properties since its null")
            }
            return PropertiesManager.properties!!.getProperty(prop.key).toString()
        }
    }

}