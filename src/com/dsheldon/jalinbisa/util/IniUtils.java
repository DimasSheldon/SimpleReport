package com.dsheldon.jalinbisa.util;

//import org.apache.log4j.Logger;

import org.ini4j.Ini;

import java.io.File;

public class IniUtils {
    private Ini ini = null;
    private String iniFile;
    private String iniDir;
//    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
//    private final Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    public IniUtils(String iniFile, String iniDir) {
        this.iniFile = iniFile;
        this.iniDir = iniDir;
        try {
            ini = new Ini(new File(iniDir.concat(iniFile)));
        } catch (Exception e) {
//            log.error(tools.printStackTrace(e));
            System.out.printf("Error: %s\n", e);
            System.exit(1);
        }
    }

    public String getVal(String section, String key) {
        return ini.get(section, key);
    }


}
