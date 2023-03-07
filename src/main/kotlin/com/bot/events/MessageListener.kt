package com.bot.events

import com.bot.Application.banned
import com.bot.PropertiesData
import com.bot.PropertiesData.Companion.getPropString
import com.bot.SupportMessages
import com.bot.command.commands
import mu.KotlinLogging
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.ItemComponent
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder
import java.io.File
import java.util.concurrent.CompletableFuture

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
                if (message.attachments.count { it.fileName == "client.log" } == 1) {
                    val downloadLoc = File("${message.id}.txt")
                    downloadLoc.mkdirs()
                    message.attachments[0].proxy.downloadToFile(downloadLoc).thenAccept {
                        handleLogMessages(it.readText(),event)
                        downloadLoc.delete()
                    }

                }
            }

            ChannelType.PRIVATE -> {
                event.channel.sendMessage("Sorry, our bot only accepts messages in our Discord server.")
            }
            else -> { logger.info { "Unhandled channel type: ${event.channelType.name}" }  }
        }


    }

    private fun handleLogMessages(content : String, event: MessageReceivedEvent) {
        val supportMessage = SupportMessages.values().first { content.contains(it.key) }

        val message = MessageCreateBuilder()
        var embed = supportMessage.embed

        if (embed == null) {
            embed = when(supportMessage) {
                SupportMessages.LACKS_SUPPORT -> {
                    val isGenericGpu = content.contains("device: GDI Generic")
                    val is32 = !content.contains("Client is 64-bit")

                    val clientVerMessage = if (is32) "\nif using a 64 bit system please Install the 64-bit version of RuneLite as you are currently on 32\n" else "\n"

                    EmbedBuilder().setTitle(
                        if(isGenericGpu) "117HD was unable to access your GPU." else "Your GPU is currently not supported by 117HD"
                    ).addField("","If your system actually has a supported GPU, try the following steps:${clientVerMessage} • If you're on a desktop PC, make sure your monitor is plugged into the graphics card instead of the motherboard's display output.\n•Reinstall the drivers for your graphics card and restart your system.",true).build()
                }
                else -> null
            }
        }

        if(embed != null) {
            message.setEmbeds(embed)
        }

        when(supportMessage) {
            SupportMessages.LACKS_SUPPORT -> {
                message.addActionRow(
                    Button.link("https://www.nvidia.com/download/index.aspx", "Nvidia"),
                    Button.link("https://www.amd.com/en/support", "Amd"),
                    Button.link("https://www.intel.co.uk/content/www/uk/en/search.html#sort=relevancy&f:@tabfilter=[Downloads]&f:@stm_10385_en=[Graphics]", "Intel")
                )

            }
        }

        event.channel.sendMessage(message.build()).queue()

    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(commands.containsKey(event.name)) {
            event.reply(commands[event.name]!!.execute().build()).queue()
        }
    }

}
