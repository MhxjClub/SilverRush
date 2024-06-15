package cn.frkovo.plugins.silverrush;

import cn.frkovo.plugins.silverrush.GameProcess.Stage;
import cn.frkovo.plugins.silverrush.GameProcess.StagePlaying;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class Death implements Listener {
    @EventHandler
    public void onDeath(EntityDamageEvent e){
        if(e.getDamageSource() instanceof Player && e.getEntity() instanceof Player)return;
        if(info.stage == Stage.PLAYING){
            if(e.getEntity() instanceof Player p) {
                if (p.getHealth() - e.getFinalDamage() <= 0) {
                    e.setCancelled(true);
                    p.setHealth(20);
                    p.setGameMode(GameMode.SPECTATOR);
                    StagePlaying.HandleDeath(p);
                    Bukkit.broadcast(Component.text("§c" + p.getName() + "§f噶了"));
                }
            }
        }else{
            e.setCancelled(true);
        }
    }
    private final Random random = new Random();
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity().getType() == EntityType.SILVERFISH && e.getDamageSource().getDamageType() == DamageType.LAVA){
            Vector launchVelocity = new Vector(random.nextDouble(-2, 2), random.nextDouble(1,2), random.nextDouble(-2, 2)); // Launch upwards
            e.getEntity().setVelocity(launchVelocity);
            e.getEntity().setFireTicks(1);
        }
    }
}
