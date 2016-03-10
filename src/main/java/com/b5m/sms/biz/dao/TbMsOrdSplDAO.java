package com.b5m.sms.biz.dao;

import java.util.List;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsOrdVO;
import com.b5m.sms.vo.TbMsOrdBatchVO;
import com.b5m.sms.vo.TbMsOrdVO;


@Repository("tbMsOrdSplDAO")
public class TbMsOrdSplDAO extends EgovAbstractDAO{

	@SuppressWarnings("unchecked")
	public List<TbMsOrdBatchVO> selectTbMsOrdGudsOptForSmsMsOrdGuds()throws Exception{
		return (List<TbMsOrdBatchVO> ) list ("tbMsOrdSplDAO.selectTbMsOrdGudsOptForSmsMsOrdGuds");
	}
	
	@SuppressWarnings("unchecked")
	public List<TbMsOrdVO> selectTbMsOrdSplForSmsMsOrd() throws Exception{
		return (List<TbMsOrdVO>) list ("tbMsOrdSplDAO.selectTbMsOrdSplForSmsMsOrd");
	}

	@SuppressWarnings("unchecked")
	public List<TbMsOrdBatchVO> selectTbMsOrdGudsOptForBatchSpecial(SmsMsOrdVO smsMsOrdVO)throws Exception{
		return (List<TbMsOrdBatchVO> ) list ("tbMsOrdDAO.selectTbMsOrdGudsOptForBatchSpecial",smsMsOrdVO);
	}

}
