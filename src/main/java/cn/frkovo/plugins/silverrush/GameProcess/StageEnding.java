package cn.frkovo.plugins.silverrush.GameProcess;

import cn.frkovo.plugins.silverrush.SilverRush;
import cn.frkovo.plugins.silverrush.info;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;

public class StageEnding {
    public static int cnt = 10;
    public static void RunEnding(){
        info.stage = Stage.ENDING;
        Title title = Title.title(Component.text("§c§l游戏结束"), Component.text("§e"+info.rank.get(0).getName()+"§a获胜!"));
        Title win = Title.title(Component.text("§6§l胜利"), Component.text("§eVICTORY"));
        for(int i = 0; i < info.rank.size(); i++){
            info.rank.get(i).showTitle(title);
            info.rank.get(i).sendActionBar(Component.text("§e游戏结束"));
        }
        info.rank.get(0).showTitle(win);
        Bukkit.getScheduler().runTaskLaterAsynchronously(SilverRush.silverRush,()->{
            for(String s : SilverRush.cfg.getStringList("end-notice")){
                s = s.replace("{winner}", !info.rank.isEmpty() ? info.rank.get(0).getName() : "--")
                     .replace("{name1}", !info.rank.isEmpty() ? info.rank.get(0).getName() : "--")
                     .replace("{name2}", info.rank.size() > 1 ? info.rank.get(1).getName() : "--")
                     .replace("{name3}", info.rank.size() > 2 ? info.rank.get(2).getName() : "--")
                     .replace("{qty1}", info.rank.isEmpty() ? info.finalBugs.get(info.rank.get(0)) + "只" : "--")
                     .replace("{qty2}", info.rank.size() > 1 ? info.finalBugs.get(info.rank.get(1)) + "只" : "--")
                     .replace("{qty3}", info.rank.size() > 2 ? info.finalBugs.get(info.rank.get(2)) + "只" : "--");
                Bukkit.broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(s));
            }
        },20L);
        Bukkit.getScheduler().runTaskTimer(SilverRush.silverRush,()->{
            cnt --;
            Bukkit.broadcast(Component.text("§e服务器即将在§c"+cnt+"§e秒后重启"));
            if(cnt == 0){
                Bukkit.shutdown();
            }
        },200L,20L);
    }
}
