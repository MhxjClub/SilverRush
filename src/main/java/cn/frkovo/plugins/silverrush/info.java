package cn.frkovo.plugins.silverrush;

import cn.frkovo.plugins.silverrush.GameProcess.Stage;
import fr.mrmicky.fastboard.adventure.FastBoard;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class info {
    public static final HashMap<Player, HashSet<SilverFishProMax>> silverfishes = new HashMap<>();
    public static final HashMap<Silverfish,SilverFishProMax> silverfishProMax = new HashMap<>();
    public static Stage stage = Stage.WAITING;
    public static final List<Player> rank = new ArrayList<>();
    public static final HashMap<Player,Integer> finalBugs = new HashMap<>();
    public static final HashMap<Player, FastBoard> boards = new HashMap<>();
    public static final HashMap<String,String> repl = new HashMap<>();

}
