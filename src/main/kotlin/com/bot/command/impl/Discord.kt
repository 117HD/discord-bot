package com.bot.command.impl

import com.bot.command.DiscordCommand
import com.bot.command.buildCommand
import net.dv8tion.jda.api.entities.emoji.CustomEmoji
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle

class Discord {

    @DiscordCommand
    fun discordLinks() = buildCommand("github") {
        setAliases { listOf("issues","org") }
        message { "Oh Hey! I see you are looking for some git links." }
        description { "List of all the git links" }
        addButtons { listOf(
            Button.link("https://github.com/117HD", "Github Organization").withEmoji(Emoji.fromFormatted("<:github:849286315580719104>")),
            Button.link("https://github.com/117HD/RLHD", "117 HD Plugin").withEmoji(Emoji.fromFormatted("<:github:849286315580719104>")),
            Button.link("https://github.com/117HD/RLHD/issues", "117 HD Issues").withEmoji(Emoji.fromFormatted("<:github:849286315580719104>"))
        )}
    }

}