package com.b5m.sms.biz.dao;

import java.util.List;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsOrdVO;
import com.b5m.sms.vo.TbMsOrdBatchVO;
import com.b5m.sms.vo.TbMsOrdVO;


@Repository("tbMsOrdDAO")
public class TbMsOrdDAO extends EgovAbstractDAO{

	@SuppressWarnings("unchecked")
	public List<TbMsOrdBatchVO> selectTbMsOrdGudsOptForSmsMsOrdGuds()throws Exception{
		return (List<TbMsOrdBatchVO> ) list ("tbMsOrdDAO.selectTbMsOrdGudsOptForSmsMsOrdGuds");
	}
	
	@SuppressWarnings("unchecked")
	public List<TbMsOrdVO> selectTbMsOrdForSmsMsOrd() throws Exception{
		return (List<TbMsOrdVO>) list ("tbMsOrdDAO.selectTbMsOrdForSmsMsOrd");
	}

	@SuppressWarnings("unchecked")
	public List<TbMsOrdBatchVO> selectTbMsOrdGudsOptForBatch(SmsMsOrdVO smsMsOrdVO)throws Exception{
		return (List<TbMsOrdBatchVO> ) list ("tbMsOrdDAO.selectTbMsOrdGudsOptForBatch",smsMsOrdVO);
	}

}
