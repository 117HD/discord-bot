package com.bot.events

import com.bot.PropertiesData
import com.bot.PropertiesData.Companion.getPropString
import com.bot.PropertiesManager
import com.bot.command.commands
import mu.KotlinLogging
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener : ListenerAdapter() {

    private val logger = KotlinLogging.logger {}

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val author = event.author
        val message: Message = event.message
        val msg: String = message.contentDisplay

        when(event.channelType) {
            ChannelType.TEXT -> {
                if(msg.startsWith(getPropString(PropertiesData.BOT_PREFIX)!!,true)) {
                    val parts = msg.split(" ",ignoreCase = true)
                    val commandName = parts[0].lowercase().replace(getPropString(PropertiesData.BOT_PREFIX)!!,"")
                    when(commands.containsKey(commandName))  {
                        true ->  commands[parts[0].lowercase().replace(getPropString(PropertiesData.BOT_PREFIX)!!,"")]!!.execute(parts.drop(1).toTypedArray(),event)
                        false -> event.author.openPrivateChannel().flatMap { it.sendMessage("Command $msg not Found") }.queue()
                    }
                    event.message.delete().queue()
                }
            }

            ChannelType.PRIVATE -> {
                event.channel.sendMessage("Sorry we don't like you yet")
            }
            else -> { logger.info { "Unhandled Channel type ${event.channelType.name}" }  }
        }


    }


}