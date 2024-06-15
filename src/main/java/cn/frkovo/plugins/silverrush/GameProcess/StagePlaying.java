package cn.frkovo.plugins.silverrush.GameProcess;

import cn.frkovo.plugins.silverrush.SilverFishProMax;
import cn.frkovo.plugins.silverrush.SilverRush;
import cn.frkovo.plugins.silverrush.info;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StagePlaying {
    public static void RunPlaying(){
        players = Bukkit.getOnlinePlayers().size();
        task = Bukkit.getScheduler().runTaskTimer(SilverRush.silverRush,StagePlaying::go,0,20);
        Title title = Title.title(Component.text("§e§l游戏开始！"),Component.text("§a活到最后！"));
        List<TextComponent> e = SilverRush.cfg.getStringList("start-notice").stream().map(a -> LegacyComponentSerializer.legacyAmpersand().deserialize(a)).toList();
        for(Component a : e){
            Bukkit.broadcast(a);
        }
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setGameMode(GameMode.SURVIVAL);
            p.showTitle(title);
            info.silverfishes.put(p,new HashSet<>());
        }
    }
    public static BukkitTask task;
    public static int timer = 20;
    public static int boost = 0;
    public static int players;
    public static boolean start = false;
    private static void go(){
        timer --;
        if(start){
            info.repl.put("double",StageWaiting.color(timer)+timer+" &f秒后蠹虫数量翻倍");
        }else {
            info.repl.put("double","&f准备环节: "+StageWaiting.color(timer)+timer+"&f秒");
            if(timer <= 5){
                Title title = Title.title(Component.text("§c§l"+timer),Component.text("§a倒计时"));
                for(Player p : info.silverfishes.keySet()){
                    p.showTitle(title);
                }
            }
        }
        if(timer <= 0){
            timer = 60;
            if(start){
                timer -= boost*5;
                boost ++;
                Bukkit.broadcast(Component.text("§e蠹虫数量翻倍！"));
                Set<Player> e2 = new HashSet<>(info.silverfishes.keySet());
                for(Player p : e2){
                    HashSet<SilverFishProMax> e = new HashSet<>(info.silverfishes.get(p));
                    for(SilverFishProMax s : e){
                        s.death();
                    }
                }
            }else{
                Title title = Title.title(Component.text("§e§l逃！"),Component.text("§a蠹虫已释放！"));
                ItemStack stack = new ItemStack(Material.COOKED_BEEF);
                stack.setAmount(64);
                for(Player p : info.silverfishes.keySet()){
                    new SilverFishProMax(p,p.getLocation());
                    p.showTitle(title);
                    p.getInventory().addItem(stack);
                }
                Bukkit.broadcast(Component.text("§eRELEASE THE BABY！！！！"));

            }
            start = true;
        }
         List<Player> rank = info.silverfishes.keySet().stream().sorted((p1,p2)->Integer.compare(info.silverfishes.get(p2).size(),info.silverfishes.get(p1).size())).toList();
         for(Player p : Bukkit.getOnlinePlayers())
             if(info.silverfishes.containsKey(p)){
                info.repl.put("urs-" + p.getName(),"&f你的蠹虫：&6" + info.silverfishes.get(p).size() + "&f只 (&e#&a"+(rank.indexOf(p)+1)+"&f)");
             }

         info.repl.put("rank_1","&e#&c1&f " + (!rank.isEmpty() ? rank.get(0).getName() : "--") + " &6" + (!rank.isEmpty() ? info.silverfishes.get(rank.get(0)).size()+"只" : "--"));
         info.repl.put("rank_2","&e#&62&f " + (rank.size() > 1 ? rank.get(1).getName() : "--") + " &6" + (rank.size() > 1 ? info.silverfishes.get(rank.get(1)).size()+"只" : "--"));
         info.repl.put("rank_3","&e#&e3&f " + (rank.size() > 2 ? rank.get(2).getName() : "--") + " &6" + (rank.size() > 2 ? info.silverfishes.get(rank.get(2)).size()+"只" : "--"));
    }
    public static void HandleDeath(Player p){
        HashSet<SilverFishProMax> a = new HashSet<>(info.silverfishes.get(p));
        info.silverfishes.remove(p);
        a.forEach(SilverFishProMax::death);
        Bukkit.broadcast(Component.text("§e剩余玩家数量: §a"+info.silverfishes.size()));
        if(info.silverfishes.size() == 1){
            task.cancel();
            task = null;
             info.rank.add(info.silverfishes.keySet().iterator().next());
             info.finalBugs.put(info.silverfishes.keySet().iterator().next(),info.silverfishes.get(info.silverfishes.keySet().iterator().next()).size());
            StageEnding.RunEnding();
        }
        if(!p.isOnline())return;
        p.setGameMode(GameMode.SPECTATOR);
        info.rank.add(p);
        info.finalBugs.put(p,a.size());
        Title title = Title.title(Component.text("§c§l你好像丝了"),Component.text("§e被淘汰力。 位列第§6"+ (players-info.rank.size()) + "§e名"));
        p.showTitle(title);
    }
}
