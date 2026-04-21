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
            eb.setTitle("Accessing RuneLite's log files:")
            LogInstructions.all.forEach {
                eb.addField("${it.label}:", it.description, false)
            }
            eb
        }
    }


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
            eb.setDescription(
                """
                This happens occasionally when the plugin fails to update. The two things to try are:
                
                • Press Windows key + R and paste in:
                ```
                %userprofile%/.runelite/cache
                ```
                and delete the folder called `okhttp`, then restart RuneLite
                
                • If that doesn't work, try connecting either your mobile internet to your PC, or use a VPN, and see if RuneLite successfully updates the plugin then
                """.trimIndent()
            )
            eb
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
                "Here's a real scene that shows the objects rendering wrong.",
                false
            )
            eb.setImage("https://media.discordapp.net/attachments/1419633364817674351/1435701805475434578/image.png?ex=6912dbc0&is=69118a40&hm=d555769211e529d67c9175b88740382c417f2da691aaccdbb50a60f57fd45b09&=&format=webp&quality=lossless&width=1308&height=874")
            eb
        }
    }

    @DiscordCommand
    fun legacy() = buildCommand("legacy") {
        description { "Explain the legacy renderer limitations with Sailing" }
        embed {
            val eb = EmbedBuilder()
            eb.setColor(Color.CYAN)
            eb.setDescription(
                "The legacy renderer does not support sailing and will no longer be receiving updates, go to the Legacy section in the 117HD settings to disable it. This should resolve any issues with invisible boats or objects."
            )
            eb.setImage("https://media.discordapp.net/attachments/1220742338192609350/1441271093154287849/image.png?ex=69212fce&is=691fde4e&hm=7b358bd463228847cbe561bc5d76fd49bbda379622ab68639ccb2f54920abda0&=&format=webp&quality=lossless")
            eb
        }
    }

    @DiscordCommand
    fun amddrivers() = buildCommand("amddrivers") {
        description { "Help with AMD driver issues causing plugin failures" }
        embed {
            val eb = EmbedBuilder()
            eb.setColor(Color.CYAN)
            eb.setTitle("AMD Driver Issues")
            eb.setDescription(
                """
                AMD and Windows have shipped some faulty OpenGL drivers and as a result, the plugin fails to turn on. Rolling back your GPU drivers will likely resolve this issue. See the Video Guide linked to at the bottom, or follow the instructions below to reinstall an older version manually:
                
                **Steps to roll back drivers:**
                1. Go to https://www.amd.com/en/support/download/drivers.html and scroll down to the browse products section
                2. Choose your GPU from the selection
                3. Scroll down to previous versions
                4. Select your OS
                5. Download the drivers dated 2025-10-29 and run the installer, it will handle the removal of the old drivers
                """.trimIndent()
            )
            eb
        }
        addButtons { listOf(
            Button.link("https://www.amd.com/en/support/download/drivers.html", "AMD Drivers"),
            Button.link("https://www.youtube.com/watch?v=5vkkeb_X9hs", "Video Guide")
        )}
    }

    @DiscordCommand
    fun timers() = buildCommand("timers") {
        description { "Instructions for using the timers feature" }
        embed {
            val eb = EmbedBuilder()
            eb.setColor(Color.CYAN)
            eb.setDescription(
                """
                Type `::117hd timers` in chat to open the timers window, then send a screenshot of the window.
                
                Type `::117hd timers` again to close it.
                """.trimIndent()
            )
            eb.setImage("https://media.discordapp.net/attachments/1041155084794466315/1441505177713770678/image.png?ex=692209d0&is=6920b850&hm=4f8719113144f224932e3c75a1c51975f07c9723d0f1156d94e94d3464bcbf05&=&format=webp&quality=lossless")
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

    @DiscordCommand
    fun memoryLimit() = buildCommand("memory-limit") {
        setAliases { listOf("memory", "oom") }
        description { "Post info about OOMs and how to increase the memory limit" }
        embed {
            val eb = EmbedBuilder()
            eb.setColor(Color.CYAN)
            eb.setTitle("Out of memory (OOM)")
            eb.setDescription(
                """
                If the client is stuttering heavily or crashing while loading new scenes, it's frequently caused by running too close to RuneLite's memory limit. Try uninstalling other hub plugins or increasing the limit.
                
                **Steps to increase the memory limit:**
                1. Open `RuneLite (configure)` from the start menu. If you cannot find it, try installing the latest RuneLite from https://runelite.net.
                2. In the JVM arguments box, add the following argument on its own line:
                ```
                -Xmx2G
                ```
                3. Click Save and restart RuneLite to apply the changes.
                4. If the stutter persists, the issue is likely caused by a hub plugin.
                """.trimIndent()
            )
            eb
        }
    }
}
