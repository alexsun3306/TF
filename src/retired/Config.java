package retired;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import test_pack.EndpointName;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Config {

	public	static	 EnumMap<EndpointName, EndpointData> LoadEndpointMap()
	{
		 String filePath = ".\\res\\endpoints.json";

	        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
	            Gson gson = new Gson();
	            
	            Type type = new TypeToken<Map<EndpointName, List<String>>>(){}.getType();
	            Map<EndpointName, List<String>> data = gson.fromJson(reader, type);

	            EnumMap<EndpointName, EndpointData> endpointMap = new EnumMap<>(EndpointName.class);
	            for (Map.Entry<EndpointName, List<String>> entry : data.entrySet()) {
	                endpointMap.put(entry.getKey(), 
	                		new EndpointData(
	                				entry.getValue().get(0), 
	                				entry.getValue().get(1),
	                				entry.getValue().get(2),
	                				entry.getValue().get(3)
	                				)
	                		);
	              
	            }
	            return endpointMap;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	}
	
	
}
