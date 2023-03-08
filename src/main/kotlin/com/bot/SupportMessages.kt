package com.bot

import net.dv8tion.jda.api.entities.MessageEmbed

enum class SupportMessages(val key: String, val embed: MessageEmbed? = null) {
    LACKS_SUPPORT(key = "The GPU is lacking OpenGL")
}