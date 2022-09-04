package Appium.utils;

import java.io.*;
import java.util.Properties;

/**
 * @description : To Read data from property file
 * @Author: Prashanth
 */
public class ConfigurationManager {

    private static ConfigurationManager instance;
    private Properties prop = new Properties();

    ConfigurationManager(String configFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(configFile);
        System.out.println("Config file :" + configFile);
        prop.load(inputStream);
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }


    public static ConfigurationManager getInstance() throws IOException {
        if (instance == null) {
            String configFile = getPropertyFilePath() + File.separator + "config.properties";
            instance = new ConfigurationManager(configFile);
        }
        return instance;
    }

    private static String getPropertyFilePath() {

        String propFilePath = "";
        String currentDir = System.getProperty("user.dir");

        File file = new File(currentDir);
        String capsPath = file.getParentFile().getPath() + File.separator + "caps";
        File capsDir = new File(capsPath);

        if (capsDir.exists()) {
            propFilePath = capsDir.getPath();
        } else {
            propFilePath = currentDir + File.separator + "caps";
        }
        return propFilePath;
    }
}

