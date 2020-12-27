package nabebuta.man10.moremining;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;

public class Effloc{
    LinkTiEf lte = new LinkTiEf();
    public void main(JavaPlugin jp, int efla) {
        MySQLManager data = new MySQLManager(jp,"moremining");
        int eli = data.count("effect_location");
        lte.setI(eli);
        ResultSet rs = data.query(String.format("select * from user limit " + efla + ", 1;"));
        Location efl = (Location)rs;
        efl.getWorld().spawnParticle(
                Particle.VILLAGER_HAPPY, // パーティクルの種類
                efl, // 発生させる場所
                50,// 発生させる数
                0.5, // 散開させるXの範囲
                0.5, // 散開させるYの範囲
                0.5 // 散開させるZの範囲
        );
    }
}
