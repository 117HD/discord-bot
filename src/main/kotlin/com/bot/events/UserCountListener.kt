package com.bot.events

import com.bot.Application
import mu.KotlinLogging
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class UserCountListener : ListenerAdapter() {

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        //Application.updateMemeberCount()
    }

    override fun onGuildMemberRemove(event: GuildMemberRemoveEvent) {
       // Application.updateMemeberCount()
    }

}