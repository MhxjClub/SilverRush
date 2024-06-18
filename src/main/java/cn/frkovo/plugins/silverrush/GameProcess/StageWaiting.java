package cn.frkovo.plugins.silverrush.GameProcess;

import cn.frkovo.plugins.silverrush.SilverRush;
import cn.frkovo.plugins.silverrush.info;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class StageWaiting {
    public static void RunWaiting(){
        task = Bukkit.getScheduler().runTaskTimer(SilverRush.silverRush,StageWaiting::go,0,20);
    }
    public static BukkitTask task = null;
    static int countdown = 100; //倒计时
    private static void go(){
        int players = Bukkit.getOnlinePlayers().size();
        if(players < 2){
            Title title = Title.title(Component.text("§c§l取消"),Component.text("§c人数不足"));
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.showTitle(title);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c人数不足"));
            });
            Bukkit.broadcast(Component.text("§c人数不足"));
            info.repl.remove("countdown");
            task.cancel();
            task = null;
            countdown = 100;
        }
        if(players > 6 && countdown > 20){
            countdown = 20;
        }
        countdown --;
        info.repl.put("countdown","&f即将在 "+color(countdown)+countdown+" &f秒后开始");
        Bukkit.getOnlinePlayers().forEach(player ->
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(color(countdown) + countdown))
                );

        if(countdown == 30){
            Bukkit.broadcast(Component.text("§e游戏将在§b30§e秒后开始"));
        }
        if(countdown == 10){
            Bukkit.broadcast(Component.text("§e游戏将在§b10§e秒后开始"));
        }
        if(countdown <= 10){
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING,1.0f,1.0f));
        }
        if(countdown <= 5){
            Title title = Title.title(Component.text(""),Component.text("§c§l"+countdown));
            Bukkit.getOnlinePlayers().forEach(player -> player.showTitle(title));
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
