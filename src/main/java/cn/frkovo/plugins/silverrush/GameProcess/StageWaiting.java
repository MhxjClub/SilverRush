package cn.frkovo.plugins.silverrush.GameProcess;

import cn.frkovo.plugins.silverrush.SilverRush;
import cn.frkovo.plugins.silverrush.info;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class StageWaiting {
    public static void RunWaiting(){
        task = Bukkit.getScheduler().runTaskTimer(SilverRush.silverRush,StageWaiting::go,0,20);
    }
    public static BukkitTask task = null;
    static int countdown = 20; //倒计时
    private static void go(){
        int players = Bukkit.getOnlinePlayers().size();
        if(players < 2){
            Title title = Title.title(Component.text("§c§l取消"),Component.text("§c人数不足"));
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.showTitle(title);
                player.sendActionBar(Component.text("§c人数不足"));
            });
            Bukkit.broadcast(Component.text("§c人数不足"));
            info.repl.remove("countdown");
            task.cancel();
            task = null;
            countdown = 20;
        }
        countdown --;
        info.repl.put("countdown","&f即将在 "+color(countdown)+countdown+" &f秒后开始");
        Bukkit.getOnlinePlayers().forEach(player ->
            player.sendActionBar(Component.text(color(countdown))));
        if(countdown == 30){
            Bukkit.broadcast(Component.text("§e游戏将在§b30§e秒后开始"));
        }
        if(countdown == 10){
            Bukkit.broadcast(Component.text("§e游戏将在§b10§e秒后开始"));
        }
        if(countdown <= 5){
            Bukkit.broadcast(Component.text("§e游戏将在§c"+countdown+"§e秒后开始"));
        }
        if(countdown == 0){
            info.stage = Stage.PLAYING;
            task.cancel();
            task = null;
            StagePlaying.RunPlaying();

        }
        
    }
    public static String color(int s){
        if(s > 30){
            return "§a";
        }else if(s > 10){
            return "§e";
        }else if (s > 5){
            return "§6";
        }else if(s > 3){
            return "§c";
        }else{
            return "§4";
        }

    }

}
