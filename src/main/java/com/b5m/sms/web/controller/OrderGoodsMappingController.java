package com.b5m.sms.web.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.anakia.Escape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b5m.sms.biz.service.GoodsService;
import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.common.util.StringUtil;
import com.b5m.sms.vo.SmsMsGudsImgVO;
import com.b5m.sms.vo.SmsMsGudsVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;

@Controller
public class OrderGoodsMappingController extends AbstractFileController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderGoodsMappingController.class);
	
	@Resource(name="goodsService")
	public GoodsService goodsService;	
	
	@Resource(name="orderService")
	public OrderService orderService;
	
	@RequestMapping(value="/orderGoodsMappingView")
	public String orderGoodsMapping(String gudsNm, String UpcId, Model model, String index, String ordNo, String ordGudsSeq ) throws Exception{
		List<SmsMsGudsVO> smsMsGudsList;//검색된 상품정보 List
		System.out.println(gudsNm);

		//1.입력된 상품명,바코드로 상품을 검색한다.
		SmsMsGudsVO smsMsGudsVo = new SmsMsGudsVO();
		smsMsGudsVo.setGudsUpcId(UpcId);
		smsMsGudsVo.setGudsCnsNm(gudsNm);
		smsMsGudsVo.setGudsKorNm(gudsNm);
		smsMsGudsList=goodsService.selectSmsMsGudsByVO(smsMsGudsVo);	
		
		//2.해당상품이 존재하면 img경로를 검색한다. 
		for(SmsMsGudsVO vo : smsMsGudsList){
			List<SmsMsGudsImgVO> gudsImgList = goodsService.selectSmsMsGudsImg(vo.getGudsId());	//한 guds_id에 이미지 여러개일수 있음
			if(!gudsImgList.isEmpty()){ 
				if(!StringUtil.isNullOrEmpty(gudsImgList.get(0).getGudsImgSysFileNm())){				//첫번째이미지를 파일이미지로 사용
					vo.setImgSrcPath(gudsImgList.get(0).getGudsImgSysFileNm());
				}
			}			
		}	

		//3.model에 검색된 상품 출력
		model.addAttribute("smsMsGudsList", smsMsGudsList);
		model.addAttribute("index", index);
		model.addAttribute("ordNo", ordNo);
		model.addAttribute("ordGudsSeq", ordGudsSeq);
		
		return "orderGoodsMapping";
		
	}
	
	@ResponseBody
	@RequestMapping(value="/orderGoodsMappingSearch")
	public List<SmsMsGudsVO> orderGoodsMappingSearch(Model model, String searchText) throws Exception{
		//1.입력된 값으로 상품명, 바코드로 검색한다
		SmsMsGudsVO smsMsGudsVo = new SmsMsGudsVO();
		smsMsGudsVo.setGudsKorNm(searchText);
		smsMsGudsVo.setGudsCnsNm(searchText);
		smsMsGudsVo.setGudsUpcId(searchText);
		List<SmsMsGudsVO> smsMsGudsList=goodsService.selectSmsMsGudsByVO(smsMsGudsVo);	 
		
		
		for(SmsMsGudsVO vo : smsMsGudsList){
			List<SmsMsGudsImgVO> gudsImgList = goodsService.selectSmsMsGudsImg(vo.getGudsId());	//한 guds_id에 이미지 여러개일수 있음
			if(!gudsImgList.isEmpty()){ 
				if(!StringUtil.isNullOrEmpty(gudsImgList.get(0).getGudsImgSysFileNm())){				//첫번째이미지를 파일이미지로 사용
					vo.setImgSrcPath(gudsImgList.get(0).getGudsImgSysFileNm());
				}
			}			
		}	
		
		return smsMsGudsList;
	}
	
	@ResponseBody
	@RequestMapping(value="/orderGoodsMappingSave")
	public void orderGoodsMappingSave(String gudsUpcId, String ordNo, String ordGudsSeq, String gudsId) throws Exception{
		
		
		
		//1.해당 주문상품의 maping상태를 Y로 설정 ~gudsId를 변경
		SmsMsOrdGudsVO smsMsOrdGudsVO = new SmsMsOrdGudsVO();
		smsMsOrdGudsVO.setGudsId(gudsId);
		smsMsOrdGudsVO.setOrdNo(ordNo);
		smsMsOrdGudsVO.setOrdGudsSeq(ordGudsSeq);
		orderService.updateSmsMsOrdGudsMpng(smsMsOrdGudsVO);
		

	}
	
}
