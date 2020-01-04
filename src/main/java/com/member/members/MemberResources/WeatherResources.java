package com.member.members.MemberResources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.members.model.Member;
import com.member.members.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600) //it is to enable the cors for the api class
public class WeatherResources {

    @Autowired
    private WeatherService weatherService;

    @GetMapping(value="/weather")
    public String getWeatherForecast (@RequestBody String json) {

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<String, Object>();
        String result = "";

        try{
            map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
            result = weatherService.getAllWeather(map.get("date").toString(), map.get("location").toString());

            //Print JSON output
            System.out.println(map);

        }  catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       return result;
    }
}
