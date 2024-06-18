package cn.frkovo.plugins.silverrush;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class SilverFishProMax {
    private final Player player;
    private final Silverfish silverfish;
    public SilverFishProMax(Player player,Location location) {
        this.player = player;
        this.silverfish = (Silverfish) player.getWorld().spawnEntity(location, EntityType.SILVERFISH);
        info.silverfishProMax.put(silverfish,this);
        silverfish.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(0.5);
        silverfish.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);
        silverfish.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(6);
        silverfish.setTarget(player);
        silverfish.setRemoveWhenFarAway(false); // Prevent the Silverfish from despawning
        silverfish.setPersistent(true); // Make the Silverfish persistent
        info.silverfishes.get(player).add(this);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(silverfish.isDead()){
                    cancel();
                }
                if(silverfish.getTarget() != player && silverfish.getTarget() != null && silverfish.getTarget().getType() == EntityType.PLAYER){
                    silverfish.setTarget(player);
                    Location location1 = player.getLocation();
                    Bukkit.getScheduler().runTaskLater(SilverRush.silverRush,()->{
                        silverfish.teleport(location1);
                    },20);
                }
                followAndBreakBlocks();
            }
        }.runTaskTimer(SilverRush.silverRush,0,5);
    }
    private void followAndBreakBlocks() {
            Vector dir = player.getLocation().toVector().subtract(silverfish.getLocation().toVector()).normalize();
            Location eyeLocation = silverfish.getEyeLocation();
            Block targetBlock = eyeLocation.add(dir).getBlock();
            // Check if the target block is solid and break it if necessary
            if (targetBlock.getType().isSolid()) {
                if (targetBlock.getType().getHardness() > 0){
                    targetBlock.breakNaturally();
                } else {
                    silverfish.setVelocity(new Vector(0, 0.5, 0));
                }
            }
            if(targetBlock.getType() == Material.WATER){
                silverfish.setVelocity(dir);
            }
            Location playerLocation = player.getLocation();
            Location silverfishLocation = silverfish.getLocation();
            if(playerLocation.distance(silverfishLocation) > 80){
                silverfish.teleport(playerLocation);
            }

            if (playerLocation.getX() >= silverfishLocation.getX() - 1.5 && playerLocation.getX() <= silverfishLocation.getX() + 1.5 &&
            playerLocation.getZ() >= silverfishLocation.getZ() - 1.5 && playerLocation.getZ() <= silverfishLocation.getZ() + 1.5) {
            silverfish.setVelocity(new Vector(0,(playerLocation.getY()-silverfishLocation.getY())*0.2,0));
            // Adjust Silverfish position to follow the player closely
        }
    }
    public Player getPlayer() {
        return player;
    }
    public Silverfish getSilverfish() {
        return silverfish;
    }
    public void death(){
        info.silverfishProMax.remove(silverfish);
        if(info.silverfishes.containsKey(player) && info.silverfishes.get(player).contains(this)) {
            info.silverfishes.get(player).remove(this);
            new SilverFishProMax(player, silverfish.getLocation());
            if (info.silverfishes.get(player).size() < 128) {
                new SilverFishProMax(player, silverfish.getLocation());
            }
        }
        silverfish.remove();
    }
}
