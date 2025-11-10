package com.bot.command

import com.bot.Application.jda
import com.bot.LogInstructions
import mu.KotlinLogging
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import java.lang.reflect.Method
import kotlin.system.measureTimeMillis

object CommandLoader {

    private val logger = KotlinLogging.logger {}

    fun init() {
        val time = measureTimeMillis {
            val reflections = Reflections(
                ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("com.bot.command.impl")).setScanners(
                    Scanners.MethodsAnnotated
                )
            )

            val methods: Set<Method> = reflections.getMethodsAnnotatedWith(DiscordCommand::class.java)

            methods.forEach {
                val classz = Class.forName(it.declaringClass.name).newInstance()
                val method2: Method = classz::class.java.getDeclaredMethod(it.name)
                method2.invoke(classz)
            }
        }
        logger.info { "Commands Loaded in $time ms [Commands Registered ${commands.size}]" }
        val list = emptyList<SlashCommandData>().toMutableList()
        commandsSlash.forEach {
            val name = it.key.lowercase()
            val data = Commands.slash(name,it.value)
            if (name == "logs" || name == "errors") {
                val platformOption = OptionData(
                    OptionType.STRING,
                    "platform",
                    "Choose the platform you are using. If omitted, the channel determines it.",
                    false
                )
                LogInstructions.choices().forEach { (label, value) ->
                    platformOption.addChoice(label, value)
                }
                data.addOptions(platformOption)
            }
            list.add(data)
        }

        jda.guilds.first().updateCommands().addCommands(list).queue()
    }

}