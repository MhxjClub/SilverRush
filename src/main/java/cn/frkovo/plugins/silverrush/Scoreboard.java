package cn.frkovo.plugins.silverrush;

import fr.mrmicky.fastboard.adventure.FastBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scoreboard implements Runnable{
    @Override
    public void run() {
        List<String> raw = new ArrayList<>(SilverRush.cfg.getStringList("scoreboard.header"));
        switch (info.stage) {
            case WAITING -> raw.addAll(SilverRush.cfg.getStringList("scoreboard.waiting"));
            case ENDING -> raw.addAll(SilverRush.cfg.getStringList("scoreboard.ending"));
            case PLAYING -> raw.addAll(SilverRush.cfg.getStringList("scoreboard.playing"));
        }
        Pattern pattern = Pattern.compile("\\{([^{}]+)}");
        raw.addAll(SilverRush.cfg.getStringList("scoreboard.footer"));
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(!info.boards.containsKey(p)){
                FastBoard board = new FastBoard(p);
                info.boards.put(p,board);
            }
            FastBoard board = info.boards.get(p);
            board.updateTitle(LegacyComponentSerializer.legacyAmpersand().deserialize(SilverRush.cfg.getString("scoreboard.title")));
            List<Component> temp = new ArrayList<>();
            for(String r : raw){
                r = r.replace("[urs]",info.repl.getOrDefault("urs-" + p.getName(),"--"));
                Matcher matcher = pattern.matcher(r);
                while (matcher.find()) {
                    String group = matcher.group();
                    String key = group.substring(1, group.length() - 1);
                    r = r.replace(group, info.repl.getOrDefault(key, "--"));
                }
                temp.add(LegacyComponentSerializer.legacyAmpersand().deserialize(PlaceholderAPI.setPlaceholders(p, r)));
            }
            board.updateLines(temp);
        }
    }
}
