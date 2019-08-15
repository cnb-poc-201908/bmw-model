package com.bmw.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmw.common.BMWPocConstants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/redis")
public class RedisAddDataController {
	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@GetMapping(value = "", produces = "application/json")
	public Map<String,Map<String, Float>> redis() throws IOException {

		Map<String,Map<String, Float>> map = new HashMap<>();

		// start set data into redis
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


		FileInputStream fis = new FileInputStream("/Users/xiongl/Desktop/CDK/dealer-model.csv");
		InputStreamReader isr = new InputStreamReader(fis);

		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		while(line != null) {
			String[] items = line.split(",");

			Map<String, Float> dealerMap = new HashMap<>();
			dealerMap.put("StockDepth", Float.valueOf(items[1]));
			dealerMap.put("FundStatus", Float.valueOf(items[2]));
			dealerMap.put("SalesAbility", Float.valueOf(items[3]));

			map.put(items[0], dealerMap);

			line = br.readLine();
		}

		br.close();

		ops.set(BMWPocConstants.REDIS_DEALER_MODELS_KEY, objectMapper.writeValueAsString(map));
		return map;
	}
}
