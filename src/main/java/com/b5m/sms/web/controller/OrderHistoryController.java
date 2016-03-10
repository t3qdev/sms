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

import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.biz.service.UserService;
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
		model.addAttribute("ordNo", ordNo);
		return result;
	}

	@ResponseBody
	@RequestMapping("/orderHistoryLoad.ajax")
	public List<SmsMsOrdHistVO> orderHistoryLoad(HttpSession session, Model model, String ordNo){
		// UserManagementController에서의 userManagementLoad.ajax 와 전체적인 맥락이 같다.
		// 받아온 String ordNo 에 대한 history 를 열람해와야 한다.
		// order Number 는 20160126002    형식.
		System.out.println("orderNo = "+ordNo);
		List<SmsMsOrdHistVO> smsMsOrdHistVOList = null;
		SmsMsOrdHistVO smsMsOrdHistVO = new SmsMsOrdHistVO();
		smsMsOrdHistVO.setOrdNo(ordNo);
		try {
			smsMsOrdHistVOList = orderService.selectSmsMsOrdHist(smsMsOrdHistVO);
			for(int i=0; i<smsMsOrdHistVOList.size(); i++){
				System.out.println(smsMsOrdHistVOList.get(i).toString());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("사용자 리스트 가져오는 데 에러가 발생 했습니다.");
		}
		return smsMsOrdHistVOList;
	}
	@ResponseBody
	@RequestMapping("/orderHistorySave.ajax")
	public List<SmsMsOrdHistVO> orderHistorySave(HttpSession session, Model model, SmsMsOrdHistVO smsMsOrdHistVO) throws Exception {
		// UserManagementController에서의 userManagementSave.ajax 와 전체적인 맥락이 같다.
		System.out.println("컨틀롤러에 들어온 vo : "+smsMsOrdHistVO.toString());
		//1. 받아온 vo에 ordHistSeq 가 null 이거나 "" 이면, 새로 insert 해야 할 대상
		if(smsMsOrdHistVO.getOrdHistSeq()==null || "".equals(smsMsOrdHistVO.getOrdHistSeq())){
			System.out.println(1);
			String ordHistSeq = null;
			ordHistSeq = orderService.selectSmsMsOrdHistSeqCount(smsMsOrdHistVO);     // db 에 넣을 seq 값을 가져와서
			int tempSeq = Integer.parseInt(ordHistSeq)+1;
		    ordHistSeq=Integer.toString(tempSeq);
			smsMsOrdHistVO.setOrdHistSeq(ordHistSeq);
			orderService.insertSmsMsOrdHist(smsMsOrdHistVO);
		}
		//2. DB에 ordNo, ordHistSeq로 검색해서 검색이 된다?  -> update 현재날짜, 상태, 작성자, 상세 내용
		else{  		
			System.out.println(2);
			orderService.updateSmsMsOrdHist(smsMsOrdHistVO);
		}
		SmsMsOrdVO smsMsOrdVO = new SmsMsOrdVO();
		System.out.println("getOrdNo: " +smsMsOrdHistVO.getOrdNo());
		System.out.println("getOrdStatCd : " + smsMsOrdHistVO.getOrdStatCd());
		smsMsOrdVO.setOrdNo(smsMsOrdHistVO.getOrdNo());
		smsMsOrdVO.setOrdStatCd(smsMsOrdHistVO.getOrdStatCd());
		orderService.updateSmsMsOrdStatCd(smsMsOrdVO);
//		updateSmsMsOrdStatCd
		System.out.println("컨틀롤러의 마지막 VO : "+smsMsOrdHistVO.toString());

		return null;
	}

}
