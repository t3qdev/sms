package com.b5m.sms.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.b5m.sms.biz.dao.SmsMsGudsDAO;
import com.b5m.sms.biz.dao.SmsMsGudsImgDAO;
import com.b5m.sms.biz.dao.SmsMsOrdDAO;
import com.b5m.sms.biz.dao.SmsMsOrdGudsDAO;
import com.b5m.sms.biz.dao.TbMsOrdDAO;
import com.b5m.sms.biz.service.GoodsService;
import com.b5m.sms.vo.SmsMsGudsImgVO;
import com.b5m.sms.vo.SmsMsGudsVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;
import com.b5m.sms.vo.TbMsOrdBatchVO;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

	@Resource(name="smsMsOrdGudsDAO")
	private SmsMsOrdGudsDAO smsMsOrdGudsDAO;
	
	@Resource(name="smsMsGudsDAO")
	private SmsMsGudsDAO smsMsGudsDAO;
	
	@Resource(name="smsMsGudsImgDAO")
	private SmsMsGudsImgDAO smsMsGudsImgDAO;
	
	@Resource(name="tbMsOrdDAO")
	private TbMsOrdDAO tbMsOrdDAO;
	
	@Resource(name="smsMsOrdDAO")
	private SmsMsOrdDAO smsMsOrdDAO;
	
	@Override
	public List<SmsMsOrdGudsVO> selectSmsMsOrdGudsByOrdNo(String ordNo) throws Exception {
		return smsMsOrdGudsDAO.selectSmsMsOrdGudsByOrdNo(ordNo);
	}

	@Override
	public SmsMsGudsVO selectSmsMsGuds(String gudsId) throws Exception {
		return smsMsGudsDAO.selectSmsMsGuds(gudsId);
	}

	@Override
	public List<SmsMsGudsImgVO> selectSmsMsGudsImg(String gudsId) throws Exception {
		return smsMsGudsImgDAO.selectSmsMsGudsImg(gudsId);
	}
	
	@Override
	public String selectSmsMsOrdGudsSeqCount(String ordNo) throws Exception {
		// TODO Auto-generated method stub
		return smsMsOrdGudsDAO.selectSmsMsOrdGudsSeqCount(ordNo);
	}

	@Override
	public String selectSmsMsOrdGudsGudsIdCount() throws Exception {
		// TODO Auto-generated method stub
		return smsMsOrdGudsDAO.selectSmsMsOrdGudsGudsIdCount();
	}

	@Override
	public List<SmsMsGudsVO> selectSmsMsGudsByVO(SmsMsGudsVO smsMsGudsVo) throws Exception {

		return smsMsGudsDAO.selectSmsMsGudsByVO(smsMsGudsVo);
	}


}





















