package cn.frkovo.plugins.silverrush;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class SilverRush extends JavaPlugin {
    public static SilverRush silverRush;
    public static Configuration cfg;

    @Override
    public void onEnable() {
        // Plugin startup logic
        silverRush = this;
        saveDefaultConfig();
        cfg = getConfig();
        info.repl.put("serial", UUID.randomUUID().toString().replace("-","").substring(0,7));
        Bukkit.getScheduler().runTaskTimerAsynchronously(this,new Scoreboard(),0,5);
        getServer().getPluginManager().registerEvents(new AntiAbuse(),this);
        getServer().getPluginManager().registerEvents(new JoinListener(),this);
        getServer().getPluginManager().registerEvents(new Death(),this);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
