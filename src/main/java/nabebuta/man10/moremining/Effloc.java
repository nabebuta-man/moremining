package nabebuta.man10.moremining;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Effloc{
    LinkTiEf lte = new LinkTiEf();
    //int i = 0;
    Moremining plugin;
    public void effloc(int i) {
        JavaPlugin jp = lte.getJp();
        //i = lte.getI();
        int eli = plugin.data.count("effect_location");
        lte.setI(eli);
        if(i != 0){
            ResultSet rs = plugin.data.query(String.format("select * from effect_location limit " + i + ", 1;"));
            try {
                while (rs.next()) {
                    String eflsw = rs.getString("world");
                    double eflsx = rs.getDouble("x");
                    double eflsy = rs.getDouble("y");
                    double eflsz = rs.getDouble("z");
                    World eflsnew = Bukkit.getWorld(eflsw);
                    Location efl = new Location(eflsnew, eflsx, eflsy, eflsz);

                    efl.getWorld().spawnParticle(
                            Particle.END_ROD, // パーティクルの種類
                            efl, // 発生させる場所
                            50,// 発生させる数
                            0.5, // 散開させるXの範囲
                            0.5, // 散開させるYの範囲
                            0.5 // 散開させるZの範囲
                    );
                }rs.close();
            } catch (SQLException e) {

            }
        }
        jp.getLogger().info("Effloc worked");
    }
}
