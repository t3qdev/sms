package com.b5m.sms.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.b5m.sms.vo.SmsMsEstmGudsVO;
import com.b5m.sms.vo.SmsMsEstmVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsEstmGudsDAO")
public class SmsMsEstmGudsDAO extends EgovAbstractDAO {
	
	public void insertSmsMsEstmGuds(SmsMsEstmGudsVO smsMsEstmGudsVo) throws Exception{
		insert("smsMsEstmGudsDAO.insertSmsMsEstmGuds",smsMsEstmGudsVo);
	}
	public void deleteSmsMsEstmGudsByOrdNm(String ordNo) throws Exception{
		delete("smsMsEstmGudsDAO.deleteSmsMsEstmGudsByOrdNm",ordNo);
		
	}
	@SuppressWarnings("unchecked")
	public List<SmsMsEstmGudsVO> selectSmsMsEstmGuds(String ordNo) throws Exception{
		return (List<SmsMsEstmGudsVO>)list("smsMsEstmGudsDAO.selectSmsMsEstmGuds",ordNo);
	}
	@SuppressWarnings("unchecked")
	public List<SmsMsEstmGudsVO> selectSmsMsEstmGudsGroupByPrvd(String ordNo) throws Exception{
		return (List<SmsMsEstmGudsVO>)list("smsMsEstmGudsDAO.selectSmsMsEstmGudsGroupByPrvd",ordNo);
	}
	
}
