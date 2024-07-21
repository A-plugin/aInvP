package org.aplugin.invitePlayer.Listeners

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.block.Sign
import org.bukkit.block.sign.Side
import org.bukkit.entity.Player

class openSign {
    fun open(p:Player) {
        p.world.getBlockAt(p.location).setType(Material.CHERRY_WALL_SIGN)

        val sign=p.world.getBlockAt(p.location).getState() as Sign

        sign.getSide(Side.FRONT).line(0, Component.text("[플레이어 초대]"))
        sign.update()
        p.sendBlockChange(p.location, sign.blockData)
        p.sendBlockUpdate(p.location, sign)

        p.openSign(sign)
    }
}