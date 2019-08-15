package com.bmw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmw.entity.response.RestResponse;

import io.swagger.annotations.ApiOperation;

@RestController("Model endpoints")
@RequestMapping("/models")
public class ModelController {
	@GetMapping(value = "/{dearlerId}", produces = "application/json")
	@ApiOperation(value = "订单匹配模型列表接口")
	public RestResponse<Object> getModels(
			@PathVariable(value = "dearlerId", required = true) String dearlerId) {

		return new RestResponse<>();
	}
}
