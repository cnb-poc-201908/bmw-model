package com.bmw.controller;

import java.util.Map;
import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmw.entity.response.RestResponse;
import com.bmw.service.ModelService;

import io.swagger.annotations.ApiOperation;

@RestController("Model endpoints")
@RequestMapping("/models")
public class ModelController {

	@Autowired
	private ModelService modelService;

	@GetMapping(value = "", produces = "application/json")
	@ApiOperation(value = "订单匹配模型列表接口")
	public RestResponse<SortedMap<String,SortedMap<String, Float>>> getModels() {
		RestResponse<SortedMap<String,SortedMap<String, Float>>> response = new RestResponse<>();
		response.setData(modelService.getModels());
		return response;
	}

	@PutMapping(value = "/{dealerId}", produces = "application/json")
	@ApiOperation(value = "订单匹配模型列表接口")
	public RestResponse<SortedMap<String, Float>> updatemodel(
			@PathVariable(value = "dealerId", required = true) String dealerId,
			@RequestBody Map<String, Float> inputs) {
		RestResponse<SortedMap<String, Float>> response = new RestResponse<>();
		response.setData(modelService.updateModel(dealerId, inputs));
		return response;
	}

	@GetMapping(value = "/{dealerId}", produces = "application/json")
	@ApiOperation(value = "订单匹配模型详情接口")
	public RestResponse<SortedMap<String, Float>> getModel(
			@PathVariable(value = "dealerId", required = true) String dealerId) {
		RestResponse<SortedMap<String, Float>> response = new RestResponse<>();
		response.setData(modelService.getModel(dealerId));
		return response;
	}
}
