package com.bot.events

import com.bot.Application.banned
import com.bot.PropertiesData
import com.bot.PropertiesData.Companion.getPropString
import com.bot.command.commands
import mu.KotlinLogging
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener : ListenerAdapter() {

    private val logger = KotlinLogging.logger {}

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message: Message = event.message
        val msg: String = message.contentDisplay

        val words = msg.lowercase().split("\\s+".toRegex())
        val containsBadWords = words.firstOrNull { it in banned } != null

        if(containsBadWords) {
            event.message.delete().queue()
        }

        when(event.channelType) {
            ChannelType.TEXT -> {
                if(msg.startsWith(getPropString(PropertiesData.BOT_PREFIX)!!,true)) {
                    val parts = msg.split(" ",ignoreCase = true)
                    val commandName = parts[0].lowercase().replace(getPropString(PropertiesData.BOT_PREFIX)!!,"")
                    when(commands.containsKey(commandName))  {
                        true ->  commands[parts[0].lowercase().replace(getPropString(PropertiesData.BOT_PREFIX)!!,"")]!!.execute(parts.drop(1).toTypedArray(),event)
                        false -> event.author.openPrivateChannel().flatMap { it.sendMessage("Command '$msg' not found") }.queue()
                    }
                    event.message.delete().queue()
                }
            }

            ChannelType.PRIVATE -> {
                event.channel.sendMessage("Sorry, our bot only accepts messages in our Discord server.")
            }
            else -> { logger.info { "Unhandled channel type: ${event.channelType.name}" }  }
        }


    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(commands.containsKey(event.name)) {
            event.reply(commands[event.name]!!.execute().build()).queue()
        }
    }

}
