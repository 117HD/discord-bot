package com.bot

import com.bot.PropertiesData.Companion.getPropString
import com.bot.command.CommandLoader
import com.bot.command.commands
import com.bot.events.MessageListener
import com.bot.events.UserCountListener
import com.bot.utils.Installs
import mu.KotlinLogging
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import java.awt.Color
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
                setActivity(Activity.watching("Installs: ${Installs.getInstalls()}")).
                addEventListeners(MessageListener(), UserCountListener())
           .build().awaitReady()
           CommandLoader.init()
           updateMemeberCount()
        }

        logger.info { "117 Bot Started in $time (ms)" }
    }

    fun updateMemeberCount() {
        jda.guilds.first().getTextChannelById(886747147343626270L)!!.manager.setName(
            "Member Count: ${jda.guilds.first().memberCount}"
        )
    }

    fun commandsList() : EmbedBuilder {
        val eb = EmbedBuilder()

        eb.setColor(Color.ORANGE)
        eb.setTitle("Commands")
        commands.forEach {
            eb.addField(it.value.command, it.value.description,false)
        }

        return eb
    }

}

fun main() {
    Application.initialize()
}


