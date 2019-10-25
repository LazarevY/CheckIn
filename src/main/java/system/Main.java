package system;


import fileinteracting.reading.GeoObjectsLoader;
import map.objects.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(UsualObj.class);
        CHSysRun run = new CHSysRun();
        run.checkInStart();

    }
}
