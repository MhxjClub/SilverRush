package cn.frkovo.plugins.silverrush;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Papi extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "silverrush";
    }

    @Override
    public @NotNull String getAuthor() {
        return "frkovo";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }
    @Override
    public boolean canRegister() {
        return true;
    }
    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equalsIgnoreCase("bugs")){
            if(info.silverfishes.containsKey(player)){
                return String.valueOf(info.silverfishes.get(player).size());
            }else{
                return "0";
            }
        }
        return "--";
    }
}
