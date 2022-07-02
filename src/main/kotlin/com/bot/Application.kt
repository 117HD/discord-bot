package com.bot

import com.bot.PropertiesData.Companion.getPropString
import com.bot.command.CommandLoader
import com.bot.events.MessageListener
import com.bot.utils.Installs
import mu.KotlinLogging
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import kotlin.system.measureTimeMillis

object Application {

    /**
     * Main Logger for the Application.
     */
    private val logger = KotlinLogging.logger {}

    lateinit var jda : JDA

    fun initialize() {
        logger.info { "Starting 117 Bot" }
        val time = measureTimeMillis {
            PropertiesManager.loadProps()
            jda = JDABuilder.
                createDefault(getPropString(PropertiesData.BOT_KEY)).
                setActivity(Activity.watching("Active Installs ${Installs.getInstalls()}")).
                addEventListeners(MessageListener())
           .build().awaitReady()
           CommandLoader.init()
        }

        logger.info { "117 Bot Started in $time (ms)" }
    }



}

fun main() {
    Application.initialize()
}


