package cn.frkovo.plugins.silverrush;

import cn.frkovo.plugins.silverrush.GameProcess.Stage;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityMountEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AntiAbuse implements Listener {
    // Starting Process
    @EventHandler
    public void onOpen(InventoryOpenEvent e){
        if(info.stage != Stage.WAITING){
            return;
        }
        if(e.getInventory().getType() != InventoryType.PLAYER){
            e.setCancelled(true);
            e.getPlayer().getLocation().createExplosion(10);
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        if(info.stage != Stage.WAITING){
            return;
        }
        e.setCancelled(true);
    }
    @EventHandler
    public void onInteractB(PlayerInteractEvent e){
        if(info.stage != Stage.WAITING){
            return;
        }
        if(e.getMaterial() == Material.CHEST)
            return;
        e.setCancelled(true);
    }


    @EventHandler
    public void onSpawn(EntitySpawnEvent e){
        if(info.stage != Stage.WAITING){
            return;
        }
        if(e.getEntity().getType() != EntityType.PLAYER)
            e.setCancelled(true);
    }
    @EventHandler
    //阻止蠹虫上船或者矿车
    public void onBoat(EntityMountEvent e){
        if(e.getEntity().getType() == EntityType.SILVERFISH){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if(e.getEntity().getType() == EntityType.SILVERFISH){
            if(info.silverfishProMax.containsKey(e.getEntity())){
                info.silverfishProMax.get(e.getEntity()).death();
            }
        }
    }
}
