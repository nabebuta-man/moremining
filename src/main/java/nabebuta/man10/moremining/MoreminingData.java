package nabebuta.man10.moremining;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.sql.ResultSet;

public class MoreminingData {
    private Moremining plugin;
    MySQLManager data = null;
    public MoreminingData(Moremining plugin){
        this.plugin = plugin;
        plugin.getLogger().info("MySQLmanager worked");
        this.data = new MySQLManager(plugin,"Moreminig");
    }
    public void Locationrecorder(World blw,double blx,double bly,double blz) {
        data.execute("insert into effect_location values ( '" + blw + "'," + blx + "," + bly + "," + blz + ")");
    }
    public int RowCounterEL(String table){
        int i = data.countRows(table);
            String str = String.valueOf(i);
            if(str != null) {
                Bukkit.getConsoleSender().sendMessage("MD worked!");
            } else {
                i = 0;
            }
        return i;
    }

    public ResultSet SingleLineExtracttion(int i){
        ResultSet rs = data.query(String.format("select * from effect_location limit " + i + ", 1;"));
        return rs;
    }
    public void setPlugin(Moremining plugin){
        this.plugin = plugin;
    }
}
