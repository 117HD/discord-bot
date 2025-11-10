package com.bot.command.impl

import com.bot.Application
import com.bot.LogInstructions
import com.bot.command.DiscordCommand
import com.bot.command.buildCommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.components.buttons.Button
import net.dv8tion.jda.api.entities.emoji.Emoji
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
            LogInstructions.all.forEach {
                eb.addField("${it.label}:", it.description, false)
            }
            eb
        }
    }

    val missingPlugin = listOf(
        "1: Close RuneLite",
        "2: Locate: [%userprofile%\\.runelite\\cache]",
        "3: delete the folder in there named okhttp",
        "4: then restart RuneLite and see if it's resolved"
    )

    val installInstructions = listOf(
        "1: Download RuneLite from their website: https://runelite.net",
        "2: Launch RuneLite",
        "3: Click the Wrench icon on the top right of the RuneLite window",
        "4: Click the Plugin-Hub button on the right side near the top",
        "5: Search for \"117 HD\"",
        "6: Click Install"
    )

    @DiscordCommand
    fun install() = buildCommand("install") {
        setAliases { listOf("download") }
        description { "Post install instructions for the plugin" }
        embed {
            val eb = EmbedBuilder()
            eb.setColor(Color.CYAN)
            eb.setTitle("How do I install the plugin?")
            eb.addField("", installInstructions.joinToString("\n"),true)
        }
        addButtons { listOf(
            Button.link("https://i.imgur.com/aTZzsXD.gif", "See a video"),
        )}
    }

    @DiscordCommand
    fun autumn() = buildCommand("autumn") {
        description { "Autumn Mode Config" }
        embed {
            val eb = EmbedBuilder()
            eb.setColor(Color(205, 95, 24))
            eb.setTitle("Autumn is Here: How to Revert Your Game's Look to the Original [Summer (Default look)]")
            eb.setImage("https://media.discordapp.net/attachments/1220742338192609350/1279861777089433751/EmSiwFS.gif?ex=66d5fbaa&is=66d4aa2a&hm=ad62e59125caf764b13d53c347c96cfbcb86eb05447d1a9b8c9ee8ddb52e7254&=")
        }
    }

    @DiscordCommand
    fun missing() = buildCommand("missing") {
        description { "Missing Plugin" }
        embed {
            val eb = EmbedBuilder()
            eb.setColor(Color.CYAN)
            eb.setTitle("Missing Plugin?")
            eb.addField("", missingPlugin.joinToString("\n"),true)
        }
    }

    @DiscordCommand
    fun zbuffering() = buildCommand("zbuffering") {
        setAliases { listOf("zbuffer") }
        description { "Explain the Z-buffering changes introduced for Sailing" }
        embed {
            val eb = EmbedBuilder()
            eb.setColor(Color(0x66, 0x99, 0xCC))
            eb.setTitle("Why does the world flicker after the Sailing update?")
            eb.setDescription(
                "In order for Sailing to work, Jagex are rendering the game a little bit differently than before, referred to as Z-buffering. This currently still breaks some things, and may cause flickering whenever two surfaces line up exactly. Jagex are still working on fixing the issues introduced by this change, and it will likely take a while before everything is fixed."
            )
            eb.addField(
                "Like, for example…",
                "Here’s a real scene that shows the objects rendering wrong.",
                false
            )
            eb.setImage("https://media.discordapp.net/attachments/1419633364817674351/1435701805475434578/image.png?ex=6912dbc0&is=69118a40&hm=d555769211e529d67c9175b88740382c417f2da691aaccdbb50a60f57fd45b09&=&format=webp&quality=lossless&width=1308&height=874")
            eb
        }
    }


    @DiscordCommand
    fun runelite() = buildCommand("runelite") {
        setAliases { listOf("client", "rl") }
        description { "Post relevant RuneLite links" }
        message { "Official RuneLite links: " }
        addButtons { listOf(
            Button.link("https://runelite.net", "Website"),
            Button.link("https://github.com/runelite", "GitHub"),
            Button.link("https://runelite.net/discord", "Discord"),
        )}
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
