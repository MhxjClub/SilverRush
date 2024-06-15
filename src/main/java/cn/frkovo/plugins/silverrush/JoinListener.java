package cn.frkovo.plugins.silverrush;

import cn.frkovo.plugins.silverrush.GameProcess.Stage;
import cn.frkovo.plugins.silverrush.GameProcess.StagePlaying;
import cn.frkovo.plugins.silverrush.GameProcess.StageWaiting;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.HashSet;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        e.joinMessage(Component.text(""));
        if(info.stage != Stage.WAITING){
            player.kick(Component.text("§c游戏已经开始"));
            return;
        }
        if(Bukkit.getOnlinePlayers().size() >= 2){
            if(StageWaiting.task == null){
                StageWaiting.RunWaiting();
            }
        }
        player.setGameMode(GameMode.ADVENTURE);
         info.repl.put("players","&f玩家: &b" + Bukkit.getOnlinePlayers().size());
        e.joinMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(PlaceholderAPI.setPlaceholders(player,"&f%luckperms_prefix%%player_name%%luckperms_suffix% &e加入了游戏！")));
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        e.quitMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(PlaceholderAPI.setPlaceholders(player,"&f%luckperms_prefix%%player_name%%luckperms_suffix% &e断开连接！踢出游戏。")));
        if(!info.silverfishes.containsKey(player)){
            e.quitMessage(Component.text(""));
            return;
        }
        if(info.stage != Stage.WAITING){
            StagePlaying.HandleDeath(e.getPlayer());
            return;
        }
        info.repl.put("players","&f玩家: &b" + Bukkit.getOnlinePlayers().size());
        e.quitMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(PlaceholderAPI.setPlaceholders(player,"&f%luckperms_prefix%%player_name%%luckperms_suffix% &e离开了游戏！")));
    }
    @EventHandler
    public void onPing(ServerListPingEvent e){
        if(info.stage != Stage.WAITING){
            e.motd(Component.text("游戏进行中"));
        }
    }
}
