package org.aplugin.invitePlayer.Listeners

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.aplugin.invitePlayer.InvitePlayer
import org.aplugin.invitePlayer.Utils.InvitePlayerP
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerLoginEvent

class onPlayer: Listener {
    val aInvP=InvitePlayer.instance

    @EventHandler
    fun onPlayerJoinEvent(e: PlayerLoginEvent) {
        if (e.player.isOp) return
        if (!Bukkit.getWhitelistedPlayers().contains(e.player)) {
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST
                , Component.text("당신은 이 서버에 초대되어있지 않습니다")
                    .color(TextColor.color(0xFF6969))
                    .decorate(TextDecoration.BOLD)
            )
        }
    }

    @EventHandler
    fun onRightClick(e: PlayerInteractEvent) {
        val player=e.player
        val action=e.action
        if (action.isRightClick) {
            if (e.player.inventory.itemInMainHand.isEmpty) return
            if (!e.player.inventory.itemInMainHand.itemMeta.hasCustomModelData()) return
            if (e.player.inventory.itemInMainHand.itemMeta.customModelData !=512) return
            openSign().open(player)
        }
    }

    val invPP=InvitePlayerP()
    @EventHandler
    fun onSignChange(event: SignChangeEvent) {
        val player = event.player
        val block = event.block

        if (block.type == Material.CHERRY_WALL_SIGN) {
            val line0= event.line(0) ?: Component.text("non")
            if (line0.contains(Component.text("[플레이어 초대]"))) return
            val inputText = event.getLine(1) ?: return

            val iP=invPP.getPlayer(inputText)

            if (iP.isWhitelisted) {
                event.player.sendMessage(Component.text("해당 플레이어는 이미 초대 되어 있습니다.")
                    .color(TextColor.color(0xFF6969))
                    .decorate(TextDecoration.BOLD))

                block.type = Material.AIR
                openSign().open(event.player)
                return

            }

            invPP.RemoveInvPlayer(player)
            iP.isWhitelisted=true
            event.player.sendMessage(Component.text("플레이어 ${iP.name}을(를) 서버에 초대했습니다.")
                .color(TextColor.color(0xB4E380))
                .decorate(TextDecoration.BOLD))

            block.type = Material.AIR

            event.isCancelled = true

        }
    }
}