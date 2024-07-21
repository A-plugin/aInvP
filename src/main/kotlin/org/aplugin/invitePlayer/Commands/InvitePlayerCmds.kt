package org.aplugin.invitePlayer.Commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.aplugin.invitePlayer.Utils.InvitePlayerP
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class InvitePlayerCmds:TabExecutor {
    fun InvitePlayerCmds(plugin: JavaPlugin) {
        plugin.getCommand("invP")?.setExecutor(this::onCommand)
        plugin.getCommand("invP")?.setTabCompleter(this::onTabComplete)
    }

    val invP=InvitePlayerP()

    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) return false
        if (!sender.hasPermission("invp.cmds")) {
            sender.sendMessage(
                Component.text("[aInvP] 명령어를 실행할 권한이 없습니다")
                .color(TextColor.color(0xFF4C4C)).decorate(TextDecoration.ITALIC))
            return true
        }
        if (args==null || args.size<2) {
            sender.sendMessage(Component.text("[aInvP] /invP [<플레이어 닉네임>] [수량]")
                .color(TextColor.color(0xFF4C4C)).decorate(TextDecoration.ITALIC))
            return true
        }
        val nick=args[0]
        val amount=args[1].toIntOrNull() ?: run { sender.sendMessage(Component.text("[aInvP] 올바른 수량을 입력하세요")
            .color(TextColor.color(0xFF4C4C)).decorate(TextDecoration.ITALIC))
            return true }

        val Tplayer=Bukkit.getPlayer(nick) ?: run {
            sender.sendMessage(Component.text("[aInvP]해당 플레이어가 존재하지 않습니다!")
                .color(TextColor.color(0xFF4C4C)).decorate(TextDecoration.ITALIC))
            return true
        }

        val savePaper=invP.getIP()
        savePaper.amount=amount

        Tplayer.inventory.addItem(savePaper)
        sender.sendMessage(Component.text("[aInvP] 플레이어 ${Tplayer.name}에게 초대권 $amount 개를 지급했습니다")
            .color(TextColor.color(0xB4E380)).decorate(TextDecoration.ITALIC))

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val tab = mutableListOf<String>()

        if (args.isEmpty() || args.size == 1) {
            val PlayerList= Bukkit.getOnlinePlayers()
            for (p in PlayerList) {
                tab.add(p.name)
            }
        } else if (args.size == 2) {
            tab.add("1")
            tab.add("8")
            tab.add("16")

        }
        return tab
    }
}