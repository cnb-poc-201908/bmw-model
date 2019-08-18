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

	@Autowired
	private Map<String,Map<String, Object>> oemModel;

	public SortedMap<String,Map<String, Map<String, Object>>> getModels() {
		return dealerModels;
	}

	public Map<String, Map<String, Object>> getModel(String dealerId) {
		return dealerModels.get(dealerId);
	}

	public Map<String, Map<String, Object>> updateModel(String dealerId, Map<String, Map<String, Object>> inputs) {
		Map<String, Map<String, Object>> map = dealerModels.get(dealerId);
		Iterator<String> it = inputs.keySet().iterator();
		if(!inputs.isEmpty()) {
			while(it.hasNext()) {
				String key =  it.next();
				Map<String, Object> destMap = inputs.get(key);
				Map<String, Object> sourceMap = map.get(key);
				Iterator<String> subIt = destMap.keySet().iterator();
				if(!destMap.isEmpty()) {
					while(subIt.hasNext()) {
						String subKey = subIt.next();
						sourceMap.put(subKey, destMap.get(subKey));
					}
				}
			}
		}
		return map;
	}

	public Map<String, Map<String, Object>> addModelItem(String dealerId, Map<String, Map<String, Object>> item) {
		Map<String, Map<String, Object>> map = dealerModels.get(dealerId);
		Iterator<String> it = item.keySet().iterator();
		if(!item.isEmpty()) {
			while(it.hasNext()) {
				String key =  it.next();
				map.put(key, item.get(key));
			}
		}
		return map;
	}

	public Map<String, Map<String, Object>> getOEMModel() {
		return oemModel;
	}
}
