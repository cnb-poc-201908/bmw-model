package com.bmw.service;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bmw.common.BMWPocConstants;

@Service
public class ModelService {

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	private static Logger logger = LoggerFactory.getLogger(ModelService.class);

	@Autowired
	private SortedMap<String,SortedMap<String, Float>> dealerModels;

	public SortedMap<String,SortedMap<String, Float>> getModels() {
		return dealerModels.subMap(BMWPocConstants.START_DEALER_ID, BMWPocConstants.END_DEALER_ID);
	}

	public SortedMap<String, Float> getModel(String dealerId) {
		return dealerModels.get(dealerId);
	}

	public SortedMap<String, Float> updateModel(String dealerId, Map<String, Float> inputs) {
		SortedMap<String, Float> map = dealerModels.get(dealerId);
		Iterator<String> it = inputs.keySet().iterator();
		if(inputs != null && !inputs.isEmpty()) {
			while(it.hasNext()) {
				String key =  it.next();
				Float value = inputs.get(key);
				map.put(key, value);
				logger.info("key is {}, value is {}", key, value);
			}
		}

		return map;
	}
}
