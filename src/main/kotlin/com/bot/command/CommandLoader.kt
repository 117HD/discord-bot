package com.bot.command

import com.bot.Application.jda
import mu.KotlinLogging
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
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
        commandsSlash.forEach { list.add(Commands.slash(it.key,it.value)) }
        jda.updateCommands().addCommands(list)
    }

}