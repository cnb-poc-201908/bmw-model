package com.bmw.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

		Random random = new Random();

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

				Map<String, Map<String, Object>> dealerMap = new HashMap<>();
				Map<String, Object> valueMap = new HashMap<>();
				valueMap.put(BMWPocConstants.KEY_NAME_VALUE, Float.valueOf(items[1]));
				valueMap.put(BMWPocConstants.KEY_NAME_WEIGHT, random.nextInt(10)+1);
				dealerMap.put("StockDepth", valueMap);

				Map<String, Object> valueMap2 = new HashMap<>();
				valueMap2.put(BMWPocConstants.KEY_NAME_VALUE, Float.valueOf(items[2]));
				valueMap2.put(BMWPocConstants.KEY_NAME_WEIGHT, random.nextInt(10)+1);
				dealerMap.put("FundStatus", valueMap2);

				Map<String, Object> valueMap3 = new HashMap<>();
				valueMap3.put(BMWPocConstants.KEY_NAME_VALUE, Float.valueOf(items[3]));
				valueMap3.put(BMWPocConstants.KEY_NAME_WEIGHT, random.nextInt(10)+1);
				dealerMap.put("SalesAbility", valueMap3);

				map.put(items[0], dealerMap);

				line = br.readLine();
			}
			ops.set(BMWPocConstants.REDIS_DEALER_MODELS_KEY, objectMapper.writeValueAsString(map));

			Map<String,Map<String, Object>> oemMap = new HashMap<>();
			Map<String, Object> colorMap = new HashMap<>();
			colorMap.put(BMWPocConstants.KEY_NAME_WEIGHT, 7);


			String[] colors = new String[] {"300", "475","A72","A75", "A96", "B45", "C25", "C2P"};
			List<Map<String, Object>> colorList = new ArrayList<>();
			for(String color : colors) {
				Map<String, Object> colorItem = new HashMap<>();
				Map<String, Object> colorWeight = new HashMap<>();
				colorWeight.put(BMWPocConstants.KEY_NAME_VALUE, getRandomValue(random));
				colorWeight.put(BMWPocConstants.KEY_NAME_WEIGHT, random.nextInt(10)+1);
				colorItem.put(color, colorWeight);
				colorList.add(colorItem);
			}
			colorMap.put("colors", colorList);

			Map<String, Object> upholsteryMap = new HashMap<>();
			upholsteryMap.put(BMWPocConstants.KEY_NAME_WEIGHT, 5);
			String[] upholsteries = new String[] {"KCMY", "KCSW", "LCL5", "LCMY"};
			List<Map<String, Object>> upholsteryList = new ArrayList<>();
			for(String upholstery : upholsteries) {
				Map<String, Object> upholsteryItem = new HashMap<>();
				Map<String, Object> upholsteryWeigth = new HashMap<>();
				upholsteryWeigth.put(BMWPocConstants.KEY_NAME_VALUE, getRandomValue(random));
				upholsteryWeigth.put(BMWPocConstants.KEY_NAME_WEIGHT, random.nextInt(10)+1);
				upholsteryItem.put(upholstery, upholsteryWeigth);
				upholsteryList.add(upholsteryItem);
			}
			upholsteryMap.put("upholsteries", upholsteryList);


			Map<String, Object> configMap = new HashMap<>();
			configMap.put(BMWPocConstants.KEY_NAME_WEIGHT, 5);
			String[] configs = new String[] {"201805ZOM", "201809ZMC", "201809ZLS", "201809ZOM", "201709ZLS", "201805ZSM", "201801ZSM",
					"201801ZLS", "201805ZLS", "201709ZSM", "201609ZLU", "201809ZSM", "201609ZOM"};
			List<Map<String, Object>> configList = new ArrayList<>();
			for(String config : configs) {
				Map<String, Object> configItem = new HashMap<>();
				Map<String, Object> configWeigth = new HashMap<>();
				configWeigth.put(BMWPocConstants.KEY_NAME_VALUE, getRandomValue(random));
				configWeigth.put(BMWPocConstants.KEY_NAME_WEIGHT, random.nextInt(10)+1);
				configItem.put(config, configWeigth);
				configList.add(configItem);
			}
			configMap.put("configs", configList);

			Map<String, Object> addMap = new HashMap<>();
			addMap.put(BMWPocConstants.KEY_NAME_WEIGHT, 3);
			String[] addCodes = new String[] {"ZWP", "ZCC"};
			List<Map<String, Object>> addCodeList = new ArrayList<>();
			for(String addCode : addCodes) {
				Map<String, Object> addItem = new HashMap<>();
				Map<String, Object> addWeigth = new HashMap<>();
				addWeigth.put(BMWPocConstants.KEY_NAME_VALUE, getRandomValue(random));
				addWeigth.put(BMWPocConstants.KEY_NAME_WEIGHT, random.nextInt(10)+1);
				addItem.put(addCode, addWeigth);
				addCodeList.add(addItem);
			}
			addMap.put("adds", addCodeList);

			oemMap.put("color", colorMap);
			oemMap.put("upholstery", upholsteryMap);
			oemMap.put("config", configMap);
			oemMap.put("add", addMap);

			ops.set(BMWPocConstants.REDIS_OEM_MODEL_KEY, objectMapper.writeValueAsString(oemMap));

			return map;
		}catch(IOException e) {
			logger.error("failed to generate json string", e);
		}
		return null;
	}

	private Float getRandomValue(Random random) {
		BigDecimal b = new BigDecimal(random.nextFloat());
		return b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
	}
}
