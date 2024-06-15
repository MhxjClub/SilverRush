package cn.frkovo.plugins.silverrush;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityMountEvent;

public class AntiAbuse implements Listener {
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
