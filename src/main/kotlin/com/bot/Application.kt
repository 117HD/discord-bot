package com.bot

import com.bot.PropertiesData.Companion.getPropString
import com.bot.command.CommandLoader
import com.bot.command.commands
import com.bot.events.MessageListener
import com.bot.events.UserCountListener
import com.bot.utils.Stats
import mu.KotlinLogging
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import java.awt.Color
import java.text.NumberFormat
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

object Application {

    /**
     * Main Logger for the Application.
     */
    private val logger = KotlinLogging.logger {}

    lateinit var jda : JDA

    fun initialize(args : Array<String>) {

        logger.info { "Starting 117 Bot" }
        val time = measureTimeMillis {
            PropertiesManager.loadProps()
            val token = when(args.isNotEmpty()) {
                true -> args[0].replace("-token:","")
                false -> getPropString(PropertiesData.BOT_KEY)
            }

            jda = JDABuilder
                .createDefault(token)
                .setActivity(Activity.watching("installs: ${Stats.getInstalls()}"))
                .addEventListeners(MessageListener(), UserCountListener())
                .enableIntents(
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.DIRECT_MESSAGES,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_WEBHOOKS,
                    GatewayIntent.MESSAGE_CONTENT
                )
                .build()
                .awaitReady()
           CommandLoader.init()
        }

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
            val installs = Stats.getInstalls()
            jda.presence.activity = Activity.watching("installs: $installs")
            updateGeneralTopic(installs)
        }, 0, 1, TimeUnit.HOURS)

        logger.info { "117 Bot Started in $time (ms)" }
    }

    private fun updateGeneralTopic(installs : String) {
        val rank = Stats.getRank()
        val discordMembers = NumberFormat.getIntegerInstance().format(jda.guilds.first().memberCount)
        val topic = "Plugin Installs: $installs | Plugin Rank #${rank} | Discord Members: $discordMembers"
        jda.guilds.first().getTextChannelById(886738668109307935)!!.manager.setTopic(topic).queue()
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

    val banned = listOf(
        "C.0m", "C 0m", "cashapp",
        "coingame", "trusted reviewer", "gpsta",
        "runestake", "sell gp", "buy gp",
        "infernocape", "cheapgp", "Runestake",
        "Ç", "ü", "é", "â", "ä", "à", "å", "ª", 
        "º", "ç", "ê", "ë", "è", "ï", "î", 
        "ì", "¡", "Ä", "Å", "É", 
        "æ", "Æ", "ô", "ö", "ò", "û",
        "ù", "ÿ", "Ö", "Ü", "¢", "£",
        "¥", "₧", "ƒ", "á", "í", "ó", "ú", "ñ", "Ñ", "ª", "α",
        "ß", "µ", "Φ", "Θ", "Ω", "δ"
    )

}

fun main(args : Array<String>) {
    Application.initialize(args)
}


