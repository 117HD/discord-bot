package com.bot.command.impl

import com.bot.Application
import com.bot.command.DiscordCommand
import com.bot.command.buildCommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.awt.Color

class BasicCommands {

    @DiscordCommand
    fun githubLinks() = buildCommand("github") {
        setAliases { listOf("issues","org") }
        message { "Oh Hey! I see you are looking for some git links." }
        description { "List of all the git links" }
        addButtons { listOf(
            Button.link("https://github.com/117HD", "Github Organization").withEmoji(Emoji.fromFormatted("<:github:849286315580719104>")),
            Button.link("https://github.com/117HD/RLHD", "117 HD Plugin").withEmoji(Emoji.fromFormatted("<:github:849286315580719104>")),
            Button.link("https://github.com/117HD/RLHD/issues", "117 HD Issues").withEmoji(Emoji.fromFormatted("<:github:849286315580719104>"))
        )}
    }

    @DiscordCommand
    fun discordLink() = buildCommand("discord") {
        setAliases { listOf("invite") }
        description { "Invite Link for 117HD." }
        message { "Oh it seems like you are looking for a invite link here! https://discord.gg/U4p6ChjgSE" }
    }

    @DiscordCommand
    fun hdos() = buildCommand("hdos") {
        description { "Invite Link for HDOS (EWWW ;))." }
        message { "Oh it seems like you are looking for hdos! While we are much better ;) maybe look here https://discord.gg/WrCGAj8MBv" }
    }

    @DiscordCommand
    fun logs() = buildCommand("logs") {
        setAliases { listOf("errors") }
        description { "Shows where to get the client logs." }
        embed {
            val eb = EmbedBuilder()
            eb.setColor(Color.CYAN)
            eb.setTitle("Locating Your RuneLite Log Files:")
            eb.addField("If Your Client Failed to Open:", "Click the `Open logs folder` button.",false)
            eb.addField("Using the Screenshots Button:", "Open the screenshot directory by right-clicking  📷`Camera` button on the RuneLite title bar, navigate 1 directory up, then open `logs` folder.",false)
            eb.addField("Windows:", "Navigate to `%userprofile%/.runelite/logs`",false)
            eb.addField("macOS/Linux:", "Navigate to `home/.runelite/logs`",false)

            eb.setFooter("This guide is from the RuneLite Discord Bot. https://github.com/runelite/runelite-discord-bot")
            eb.setThumbnail("https://camo.githubusercontent.com/c9ab595fc563db74412d31763e2bd668cd26b0368e0d63820e63a9893c76f7ce/68747470733a2f2f72756e656c6974652e6e65742f696d672f6c6f676f2e706e67")
            eb
        }
    }

    @DiscordCommand
    fun safeMode() = buildCommand("safemode") {
        description { "How to activate safemode." }
        embed {
            val eb = EmbedBuilder()
            eb.setTitle("Launching RuneLite in Safe Mode", null)
            eb.setColor(Color.GREEN)

            eb.addField("What is Safe Mode?:", "Launching RuneLite in safe mode disables the loading of third-party plugins. If after installing the HD plugin you cannot get RuneLite to remain open please run the applicable command below so that you can uninstall the plugin. Afterwards you can reopen RuneLite normally.", false)
            eb.addField("Windows:", "Open your Start Menu and select `Run` (or press `Win + R`). Paste in and run the following: `\"%localappdata%\\runelite\\runelite.exe\" --clientargs --safe-mode`", false)
            eb.addField("macOS:", "Open a terminal window and run `/Applications/RuneLite.app/Contents/MacOS/RuneLite --clientargs --safe-mode`", false)
            eb.addField("Linux:", "Open a terminal window and run `$(which runelite) --clientargs --safe-mode`", false)
            eb.setThumbnail("https://static.wikia.nocookie.net/2007scape/images/9/97/Unknown_NPC.png/revision/latest/scale-to-width-down/115?cb=20180507162450")

            eb
        }
    }

    @DiscordCommand
    fun commands() = buildCommand("commands") {
        sendPrivate { true }
        hide { true }
        embed { Application.commandsList() }
    }

}