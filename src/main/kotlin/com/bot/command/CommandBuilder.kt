package com.bot.command

import com.bot.Application
import com.bot.Roles
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button
import kotlin.reflect.KClass

@DslMarker
private annotation class CommandBuilderDslMarker

annotation class DiscordCommand

val commands : MutableMap<String, CommandBuilder> = emptyMap<String,CommandBuilder>().toMutableMap()
val commandsSlash : MutableMap<String, String> = emptyMap<String,String>().toMutableMap()

@CommandBuilderDslMarker
class CommandBuilder(var command : String, val types: List<KClass<out Any>>) {

    var rights: Roles = Roles.EVERYONE
    var description: String = "Unknown"
    var aliases: List<String> = emptyList()
    var buttons: MutableList<Button> = emptyList<Button>().toMutableList()
    private var message: String? = null

    fun description(builder: () -> String) { this.description = builder() }
    fun requiredRoles(builder: () -> Roles) { this.rights = builder() }
    fun setAliases(builder: () -> List<String>) { this.aliases = builder() }
    fun addButtons(builder: () -> List<Button>) { this.buttons = builder().toMutableList() }
    fun addButton(builder: () -> Button) { this.buttons.add(builder()) }
    fun message(content: () -> String) { message = content() }

    fun execute(inputs : Array<String>, event: MessageReceivedEvent) {

        println(inputs.toString())
        if(!typeCheck(types, inputs)) {
            //WRONG ARGS SEND MESSAGE
        } else {
            if(message != null) {
                val message = event.channel.sendMessage(message!!)
                if (buttons.isNotEmpty()) {
                    val rows : MutableList<ActionRow> = emptyList<ActionRow>().toMutableList()
                    buttons.forEach {
                        rows.add(ActionRow.of(it))
                    }
                    message.setActionRows(rows)
                }
                message.queue()
            }
        }
    }

}

fun buildCommand(command : String,types: List<KClass<out Any>> = emptyList(), builder: CommandBuilder.() -> Unit)  {
    val bld = CommandBuilder(command,types)
    builder.invoke(bld)
    commands[bld.command] = bld
    commandsSlash[bld.command] = bld.description
    bld.aliases.forEach {
        commands[it] = bld
        commandsSlash[it] = bld.description
    }
}

/**
 * Checks that the list of command inputs can be cast to the given types.
 */
private fun typeCheck(classes : List<KClass<out Any>>, input : Array<String>) : Boolean{
    if(input.size != classes.size) return false

    try {
        for (i in input.indices) {
            if (when (classes[i]) {
                    Int::class -> { return input[i].count { it.isLetter() } == 0 }
                    Double::class -> input[i].toDoubleOrNull()
                    Float::class -> input[i].toFloatOrNull()
                    Byte::class -> input[i].toByteOrNull()
                    Boolean::class -> input[i].toBoolean()
                    String::class ->  input[i].count { it.isLetter() } != 0
                    else ->  null
                } == null) {
                return false
            }
        }
    } catch(ex : Exception) { return false }

    return true
}