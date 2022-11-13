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
        description { "Post a list of relevant GitHub links" }
        message { "List of relevant GitHub links:" }
        addButtons { listOf(
            Button.link("https://github.com/117HD", "Organization").withEmoji(Emoji.fromFormatted("<:github:849286315580719104>")),
            Button.link("https://github.com/117HD/RLHD", "Plugin Repository").withEmoji(Emoji.fromFormatted("<:github:849286315580719104>")),
            Button.link("https://github.com/117HD/RLHD/issues", "Issues").withEmoji(Emoji.fromFormatted("<:github:849286315580719104>"))
        )}
    }

    @DiscordCommand
    fun discordLink() = buildCommand("discord") {
        setAliases { listOf("invite") }
        description { "Post an invite link to this Discord" }
        message { "The invite link to this Discord: https://discord.gg/U4p6ChjgSE" }
    }

    @DiscordCommand
    fun hdos() = buildCommand("hdos") {
        description { "Post an invite link to the HDOS Discord" }
        message { "If you are looking for a recreation of 2009-era RuneScape, check out HDOS: https://discord.gg/hdos" }
    }

    @DiscordCommand
    fun logs() = buildCommand("logs") {
        setAliases { listOf("errors") }
        description { "Post instructions for locating RuneLite's log files" }
        embed {
            val eb = EmbedBuilder()
            eb.setColor(Color.CYAN)
            eb.setTitle("Accessing Your RuneLite Log Files:")
            eb.addField("Windows:",
                "Press the Windows key + R, then paste in the following path into the popup window:\n" +
                "```\n%userprofile%/.runelite/logs\n```\n" +
                "Once you are there, drag `client` or `client.log` into this Discord channel.",false)
            eb.addField("macOS:",
                "Open Finder and press Cmd + Shift + G, then paste the following path into the popup window:\n" +
                "```\n~/.runelite/logs\n```\n" +
                "Once you are there, drag `client` or `client.log` into this Discord channel.",false)
            eb.addField("Linux:",
                "Navigate to the following path in a file browser:\n" +
                "```\n~/.runelite/logs\n```\n" +
                "Once you are there, drag `client` or `client.log` into this Discord channel.",false)
        }
    }

    @DiscordCommand
    fun safeMode() = buildCommand("safemode") {
        description { "Post instructions for launching RuneLite in safe mode" }
        embed {
            val eb = EmbedBuilder()
            eb.setTitle("Launching RuneLite in Safe Mode", null)
            eb.setColor(Color.GREEN)

            eb.addField("What is Safe Mode?:", "Launching RuneLite in safe mode disables the loading of third-party plugins. If after installing the HD plugin you cannot get RuneLite to remain open please run the applicable command below so that you can uninstall the plugin. Afterwards you can reopen RuneLite normally.", false)
            eb.addField("Windows:", "Press the Windows key + R and paste in the following command:\n" +
                "```\n\"%localappdata%\\runelite\\runelite.exe\" --clientargs --safe-mode\n```", false)
            eb.addField("macOS:", "Open a terminal window and run the following command:\n" +
                "```\n/Applications/RuneLite.app/Contents/MacOS/RuneLite --clientargs --safe-mode\n```", false)
            eb.addField("Linux:", "Open a terminal window and run the following command:\n" +
                "```\n$(which runelite) --clientargs --safe-mode\n```\n" +
                "If you are using the AppImage, or some alternative way of launching RuneLite, append the same arguments to your command.", false)
            eb.setThumbnail("https://static.wikia.nocookie.net/2007scape/images/9/97/Unknown_NPC.png/revision/latest/scale-to-width-down/115?cb=20180507162450")
        }
    }

    @DiscordCommand
    fun commands() = buildCommand("commands") {
        sendPrivate { true }
        hide { true }
        embed { Application.commandsList() }
    }

    @DiscordCommand
    fun settingGuide() = buildCommand("settings") {
        description { "Post a link to our settings guide" }
        message { "See our settings guide for more details on how each setting may impact performance:" }
        addButtons { listOf(
            Button.link("https://github.com/RS117/RLHD/blob/master/settings-guide.md", "Settings Guide"),
        )}
    }
}
