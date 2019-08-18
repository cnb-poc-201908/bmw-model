package com.bmw.service;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	private static Logger logger = LoggerFactory.getLogger(ModelService.class);

	@Autowired
	private SortedMap<String,Map<String, Map<String, Object>>> dealerModels;

	public SortedMap<String,Map<String, Map<String, Object>>> getModels() {
		return dealerModels;
	}

	public Map<String, Map<String, Object>> getModel(String dealerId) {
		return dealerModels.get(dealerId);
	}

	public Map<String, Map<String, Object>> updateModel(String dealerId, Map<String, Float> inputs) {
		Map<String, Map<String, Object>> map = dealerModels.get(dealerId);
		Iterator<String> it = inputs.keySet().iterator();
		if(!inputs.isEmpty()) {
			while(it.hasNext()) {
				String key =  it.next();
				Float value = inputs.get(key);
				Map<String, Object> valueMap = map.get(key);
				valueMap.put("value", value);
				logger.info("key is {}, value is {}", key, value);
			}
		}
		return map;
	}
}
