/**
 *
 *  @author Domariev Vladyslav S19314
 *
 */

package zad1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;


public class Tools {
    public static Options createOptionsFromYaml(String fileName){
    	
    	InputStream input = null; 
    	try {
    		input = new FileInputStream(new File(fileName));
    	}catch(FileNotFoundException fileNofFoundExcpetion) {
    		
    		fileNofFoundExcpetion.printStackTrace();	
    	}
    	
    	Yaml yaml = new Yaml();
    	Map<String, Object> map = (HashMap)yaml.load(input);
    	// System.out.println("Host " + map.get("host"));
        // System.out.println(map);
    	/*
    	for (Object data : yaml.loadAll(input)) {
            System.out.println(data);
        //    counter++;
        }
    	*/
    	// Object data = yaml.load(input);
    	
    	
    	
    	return new Options(
    						map.get("host").toString(),
    						Integer.parseInt((map.get("port").toString())), 
							Boolean.parseBoolean(map.get("concurMode").toString()), 
							Boolean.parseBoolean(map.get("showSendRes").toString()),
							((Map<String, List<String>>)map.get("clientsMap")));
    }
    
    
}

