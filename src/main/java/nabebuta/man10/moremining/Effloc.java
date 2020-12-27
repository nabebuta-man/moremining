package nabebuta.man10.moremining;

import org.bukkit.Location;
import java.sql.ResultSet;

public class Effloc{
    Tokei tokei = new Tokei();
    Moremining mrmn = new Moremining();
    MySQLManager data = new MySQLManager(mrmn,"moremining");
    public void main(int efla) {

        int eli = data.count("effect_location");
        tokei.setIlimit(eli);
        ResultSet rs = data.query(String.format("select * from user limit " + efla + ", 1;"));
        Location efl = (Location)rs;
        mrmn.particlefef(efl,50,0.5,0.5,0.5);
    }
}
