package roomley.util;

import java.io.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This interface contains a default method that can be used anywhere a Properties
    * object is needed to be loaded.
    * @author Eric Knapp
    *
    */
public interface PropertiesLoader {

    /**
     * This default method will load a properties file into a Properties instance
        * and return it.
        * @param propertiesFilePath a path to a file on the java classpath list
        * @return a populated Properties instance or an empty Properties instance if
        * the file path was not found.
        */
    default Properties loadProperties(String propertiesFilePath){
        Properties properties = new Properties();

        Logger logger = LogManager.getLogger(this.getClass());

        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilePath));

        } catch (IOException ioException) {
            logger.error("There was an issue reading this file", ioException);

        } catch (Exception exception) {
            logger.error("There was an issue loading this file", exception);
            
        }

        return properties;
    }
}