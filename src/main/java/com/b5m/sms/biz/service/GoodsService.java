package com.b5m.sms.biz.service;

import java.util.List;

import com.b5m.sms.vo.SmsMsEstmGudsVO;
import com.b5m.sms.vo.SmsMsGudsImgVO;
import com.b5m.sms.vo.SmsMsGudsVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;
import com.b5m.sms.vo.TbMsOrdBatchVO;

public interface GoodsService {
	//주문에 해당하는 상품을 가져온다
	public List<SmsMsOrdGudsVO> selectSmsMsOrdGudsByOrdNo(String ordNo) throws Exception;
	//상품아이디로 SmsMsGuds에서 상품 검색
	public SmsMsGudsVO selectSmsMsGuds(String gudsId) throws Exception;
	//상품아이디로 SmsMsGudsImg에서 해당 이미지 검색
	public List<SmsMsGudsImgVO> selectSmsMsGudsImg(String gudsId) throws Exception;
	
	public String selectSmsMsOrdGudsSeqCount(String ordNo) throws Exception;

	public String selectSmsMsOrdGudsGudsIdCount() throws Exception;
	
	//상품이름, 상품바코드로 상품검색
	public List<SmsMsGudsVO> selectSmsMsGudsByVO(SmsMsGudsVO smsMsGudsVo) throws Exception;
	//이미지 일괄검색
	public List<SmsMsGudsImgVO> selectSmsMsGudsImgAll() throws Exception;
	//PO상품 검색
	public List<SmsMsEstmGudsVO> selectSmsMsEstmGuds(String ordNo) throws Exception;
	//이미지 코드검색으로 가져오기 
	public SmsMsGudsImgVO selectSmsMsGudsImgByCd(SmsMsGudsImgVO smsMsGudsImgVo) throws Exception;
	//PO상품 사업자명으로 정렬
	public List<SmsMsEstmGudsVO> selectSmsMsEstmGudsGroupByPrvd(String ordNo) throws Exception;
}

