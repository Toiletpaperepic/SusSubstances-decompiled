package toiletpaperepic.SusSubstances;

import org.bukkit.*;
import java.util.*;

public class ItemValues {
    //what make a item
    public Material material;
    public Material block;
    public String name;
    public String deathmessage;

    //who's using a item at any time.
    public ArrayList<UUID> flylist;
    public HashMap<UUID, Integer> ItemList;

    //set by config,
    //leave this alone.
    public Boolean status;
    public Double  rate;
}