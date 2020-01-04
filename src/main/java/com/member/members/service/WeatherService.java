package com.member.members.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Component
public class WeatherService {

    RestTemplate restTemplate = new RestTemplate();

    public String getAllWeather(String date, String place)  {
        String jsonResult = "";
        String location = place;
        //obtain the json result from the api in
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity  = new HttpEntity<String>(headers);
        String hyperlink = "https://api.data.gov.sg/v1/environment/2-hour-weather-forecast?date="+date;
        String result =restTemplate.exchange(hyperlink, HttpMethod.GET,entity,String.class).getBody();
        //end of the declaration of obtain the json result
        ObjectMapper mapper = new ObjectMapper();

        HashMap<String, Object> map = new HashMap<String, Object>();
        try{
            //obtaining the first nest of the json result
            map = mapper.readValue(result, new TypeReference<Map<String, Object>>(){});
            ArrayList<Object> x = (ArrayList<Object>) map.get("items");
            ArrayList<String> specificWeathers = new ArrayList<String>();
            //Print JSON output
//            System.out.println(x.size());
//            x.forEach((n)->System.out.println(n));
            for(int i=0; i<x.size(); i++){
                //obtaining the second nest of the json result
                ObjectMapper mapper1 = new ObjectMapper();
                mapper1.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); //method to convert the object for the mapper to map
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                String json = new ObjectMapper().writeValueAsString(x.get(i));
                map1 = mapper1.readValue(json, new TypeReference<Map<String, Object>>(){});
                ArrayList<Object> forecasts = (ArrayList<Object>)map1.get("forecasts");
                for(int j=0; j<forecasts.size(); j++){
                    //obtaining the third nest of the json result
                    ObjectMapper mapper2 = new ObjectMapper();
                    mapper2.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                    HashMap<String, Object> map2 = new HashMap<String, Object>();
                    String json1 = new ObjectMapper().writeValueAsString(forecasts.get(j));
                    map2 = mapper2.readValue(json1, new TypeReference<Map<String, Object>>(){});
                    //System.out.println(map2);
                    if(map2.get("area").toString().equals(location)){
                        specificWeathers.add(map2.get("forecast").toString());
                    }
                }
            }
           // System.out.println(specificWeathers.size());
           // System.out.println(specificWeathers.get(0));
            Map<String, Integer> resultSet = new HashMap<String, Integer>();
            for (int k=0; k<specificWeathers.size(); k++){
                String a = specificWeathers.get(k);
                if(resultSet.containsKey(a)==false){
                    resultSet.put(a,1);
                }else{
                    resultSet.put(a,resultSet.get(a)+1);
                }
            }

            jsonResult = mapper.writeValueAsString(resultSet);
            //System.out.println(jsonResult);
        }  catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    public String getLocations(){
        String jsonResult = "";
        //obtain the json result from the api in
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity  = new HttpEntity<String>(headers);
        String hyperlink = "https://api.data.gov.sg/v1/environment/2-hour-weather-forecast?date="+"2019-01-04";
        String result =restTemplate.exchange(hyperlink, HttpMethod.GET,entity,String.class).getBody();
        //end of the declaration of obtain the json result
        ObjectMapper mapper = new ObjectMapper();

        HashMap<String, Object> map = new HashMap<String, Object>();
        try{
            //obtaining the first nest of the json result
            map = mapper.readValue(result, new TypeReference<Map<String, Object>>(){});
            ArrayList<Object> x = (ArrayList<Object>) map.get("items");
            ArrayList<String> specificLocation = new ArrayList<String>();
            //Print JSON output
//            System.out.println(x.size());
//            x.forEach((n)->System.out.println(n));
            for(int i=0; i<x.size(); i++){
                //obtaining the second nest of the json result
                ObjectMapper mapper1 = new ObjectMapper();
                mapper1.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); //method to convert the object for the mapper to map
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                String json = new ObjectMapper().writeValueAsString(x.get(i));
                map1 = mapper1.readValue(json, new TypeReference<Map<String, Object>>(){});
                ArrayList<Object> forecasts = (ArrayList<Object>)map1.get("forecasts");
                for(int j=0; j<forecasts.size(); j++){
                    //obtaining the third nest of the json result
                    ObjectMapper mapper2 = new ObjectMapper();
                    mapper2.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                    HashMap<String, Object> map2 = new HashMap<String, Object>();
                    String json1 = new ObjectMapper().writeValueAsString(forecasts.get(j));
                    map2 = mapper2.readValue(json1, new TypeReference<Map<String, Object>>(){});
                    //System.out.println(map2);
                    if(!specificLocation.contains(map2.get("area").toString())){
                        specificLocation.add(map2.get("area").toString());
                    }
                }
            }
            // System.out.println(specificWeathers.size());
            // System.out.println(specificWeathers.get(0));


            jsonResult = mapper.writeValueAsString(specificLocation);
            //System.out.println(jsonResult);
        }  catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
