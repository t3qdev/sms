package com.b5m.sms.web.controller;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.common.util.StringUtil;
import com.b5m.sms.vo.OrderCalculateVO;


@Controller
public class OrderCalculateController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCalculateController.class);
	
	@Resource(name = "orderService")
	public OrderService orderService;	
		
	@RequestMapping(value="/orderCalculateView")
	public String orderCalculate(String ordNo, Model model) throws Exception{
		//1.정산내역을 조회 (작성자가 존재하면 첫정산이 완료된 내용, statcde로도 구분가능)
		String first="Y";
		OrderCalculateVO ordcalc =orderService.selectSmsMsOrdCalculate(ordNo);
		//<<select해서 해당값을 가져오는 부분>>
		if(ordcalc!=null){
			if(!StringUtil.isNullOrEmpty(ordcalc.getBactRegrEml())){
				first="N";
			}
		}
		model.addAttribute("ordcalc", ordcalc);
		model.addAttribute("ordNo", ordNo);
		model.addAttribute("fisrt", first);
		
		return "orderCalculate";
		
	}
	

	
	@ResponseBody
	@RequestMapping(value="/orderCalculateSave")
	public Boolean orderCalculateSave(OrderCalculateVO orderCalculateVO, Model model,String first) throws Exception{

		OrderCalculateVO ordcalc =orderService.selectSmsMsOrdCalculate(orderCalculateVO.getOrdNo());

		if(!orderCalculateVO.getBactRegrEml().equals(ordcalc.getBactRegrEml())&&!"Y".equals(first)){
			return false;
		}
		
		
		//히스토리와 calculate를 동시에 관리해야하므로 Service에서 처리
		orderService.updateSmsMsOrdCalculate(orderCalculateVO, first);
		return true;		
	}
	
	
}
