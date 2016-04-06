package com.b5m.sms.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b5m.sms.biz.dao.SmsMsOrdDAO;
import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.biz.service.UserService;
import com.b5m.sms.vo.OrderDetailVO;
import com.b5m.sms.vo.SmsMsOrdHistVO;
import com.b5m.sms.vo.SmsMsOrdVO;
import com.b5m.sms.vo.SmsMsUserVO;

@Controller
public class OrderHistoryController {

	@Resource(name = "orderService")
	public OrderService orderService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderHistoryController.class);

	@RequestMapping(value = "/orderHistoryView")
	public String orderHistory(HttpSession session, Model model, String ordNo) {
		String result = "orderHistory";
		String ordStatCd = null;
		
		model.addAttribute("ordNo", ordNo);
		OrderDetailVO orderDetailVO = new OrderDetailVO();
		
		try {
			orderDetailVO = orderService.selectSmsMsOrdDetail(ordNo);
			ordStatCd = orderDetailVO.getOrdStatCd();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("ordStatCd",ordStatCd);

		return result;
	}

	@ResponseBody
	@RequestMapping("/orderHistoryLoad.ajax")
	public List<SmsMsOrdHistVO> orderHistoryLoad(HttpSession session, Model model, String ordNo){
		// UserManagementController에서의 userManagementLoad.ajax 와 전체적인 맥락이 같다.
		// 받아온 String ordNo 에 대한 history 를 열람해와야 한다.
		// order Number 는 20160126002    형식.
		List<SmsMsOrdHistVO> smsMsOrdHistVOList = null;
		SmsMsOrdHistVO smsMsOrdHistVO = new SmsMsOrdHistVO();
		smsMsOrdHistVO.setOrdNo(ordNo);
		try {
			smsMsOrdHistVOList = orderService.selectSmsMsOrdHist(smsMsOrdHistVO);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return smsMsOrdHistVOList;
	}
	@ResponseBody
	@RequestMapping("/orderHistorySave.ajax")
	public List<SmsMsOrdHistVO> orderHistorySave(HttpSession session, Model model, SmsMsOrdHistVO smsMsOrdHistVO) throws Exception {
		// UserManagementController에서의 userManagementSave.ajax 와 전체적인 맥락이 같다.
		//1. 받아온 vo에 ordHistSeq 가 null 이거나 "" 이면, 새로 insert 해야 할 대상
		if(smsMsOrdHistVO.getOrdHistSeq()==null || "".equals(smsMsOrdHistVO.getOrdHistSeq())){
			String ordHistSeq = null;
			ordHistSeq = orderService.selectSmsMsOrdHistSeqCount(smsMsOrdHistVO);     // db 에 넣을 seq 값을 가져와서
			int tempSeq = Integer.parseInt(ordHistSeq)+1;
		    ordHistSeq=Integer.toString(tempSeq);
			smsMsOrdHistVO.setOrdHistSeq(ordHistSeq);
			orderService.insertSmsMsOrdHist(smsMsOrdHistVO);
		}
		//2. DB에 ordNo, ordHistSeq로 검색해서 검색이 된다?  -> update 현재날짜, 상태, 작성자, 상세 내용
		else{  		
			orderService.updateSmsMsOrdHist(smsMsOrdHistVO);
		}
		SmsMsOrdVO smsMsOrdVO = new SmsMsOrdVO();
		smsMsOrdVO.setOrdNo(smsMsOrdHistVO.getOrdNo());
		smsMsOrdVO.setOrdStatCd(smsMsOrdHistVO.getOrdStatCd());
		orderService.updateSmsMsOrdStatCd(smsMsOrdVO);
//		updateSmsMsOrdStatCd

		return null;
	}

}
