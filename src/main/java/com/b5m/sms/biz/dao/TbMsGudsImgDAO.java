package com.b5m.sms.biz.dao;

import java.util.List;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsGudsImgVO;
import com.b5m.sms.vo.SmsMsOrdVO;
import com.b5m.sms.vo.TbMsOrdBatchVO;
import com.b5m.sms.vo.TbMsOrdVO;


@Repository("tbMsGudsImgDAO")
public class TbMsGudsImgDAO extends EgovAbstractDAO{


	@SuppressWarnings("unchecked")
	public List<SmsMsGudsImgVO> selectTbMsGudsImgForFileCopy(TbMsOrdBatchVO tbMsOrdBatchVO)throws Exception{
		return (List<SmsMsGudsImgVO> ) list ("tbMsGudsImgDAO.selectTbMsGudsImgForFileCopy",tbMsOrdBatchVO);
	}

}
