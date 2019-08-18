package com.bmw.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static Logger logger = LoggerFactory.getLogger(RedisAddDataController.class);

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@GetMapping(value = "", produces = "application/json")
	public Map<String,Map<String, Map<String, Object>>> redis() throws IOException {



		try (
				FileInputStream fis = new FileInputStream("/Users/xiongl/Desktop/CDK/dealer-model.csv");
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);

				){
			Map<String,Map<String, Map<String, Object>>> map = new HashMap<>();

			// start set data into redis
			ValueOperations<String, String> ops = redisTemplate.opsForValue();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			String line = br.readLine();
			while(line != null) {
				String[] items = line.split(",");
				Random random = new Random();
				Map<String, Map<String, Object>> dealerMap = new HashMap<>();
				Map<String, Object> valueMap = new HashMap<>();
				valueMap.put("value", Float.valueOf(items[1]));
				valueMap.put("weigth", random.nextInt(10)+1);
				dealerMap.put("StockDepth", valueMap);

				Map<String, Object> valueMap2 = new HashMap<>();
				valueMap2.put("value", Float.valueOf(items[2]));
				valueMap2.put("weigth", random.nextInt(10)+1);
				dealerMap.put("FundStatus", valueMap2);

				Map<String, Object> valueMap3 = new HashMap<>();
				valueMap3.put("value", Float.valueOf(items[3]));
				valueMap3.put("weigth", random.nextInt(10)+1);
				dealerMap.put("SalesAbility", valueMap3);

				map.put(items[0], dealerMap);

				line = br.readLine();
			}
			ops.set(BMWPocConstants.REDIS_DEALER_MODELS_KEY, objectMapper.writeValueAsString(map));
			return map;
		}catch(IOException e) {
			logger.error("failed to generate json string", e);
		}
		return null;
	}
}
