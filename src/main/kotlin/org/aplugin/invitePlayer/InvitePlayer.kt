package org.aplugin.invitePlayer

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.aplugin.invitePlayer.Commands.ConsoleCmds
import org.aplugin.invitePlayer.Commands.InvitePlayerCmds
import org.aplugin.invitePlayer.Listeners.onPlayer
import org.bukkit.plugin.java.JavaPlugin

class InvitePlayer : JavaPlugin() {

    override fun onEnable() {
        if (instance!=null) return
        instance=this

        logger.info(
            Component.text("초대권 By.아포칼립스").color(TextColor.color(0xB9AAFF)).decorate(
                TextDecoration.BOLD).content())


        ConsoleCmds().ConsoleCmds(this)
        InvitePlayerCmds().InvitePlayerCmds(this)
        server.pluginManager.registerEvents(onPlayer(), this)
    }

    companion object {
        var instance: InvitePlayer?=null
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
