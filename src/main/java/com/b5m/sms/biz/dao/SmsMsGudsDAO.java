package com.b5m.sms.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsGudsVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;





import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsGudsDAO")
public class SmsMsGudsDAO extends EgovAbstractDAO {

	public SmsMsGudsVO selectSmsMsGuds(String gudsId) throws Exception{
		return (SmsMsGudsVO) select("smsMsGudsDAO.selectSmsMsGuds",gudsId);
	}
	
	//바코드(후보키)로 해당상품을 검색한다  but  Return LIST
	@SuppressWarnings("unchecked")
	public List<SmsMsGudsVO> selectSmsMsGudsByUpcIDforBatch(SmsMsGudsVO smsMsGudsVO) throws Exception{
		return (List<SmsMsGudsVO>) list("smsMsGudsDAO.selectSmsMsGudsByUpcIDforBatch",smsMsGudsVO);
	}
	
	//B5cSkuId 로 해당상품을 검색한다  Return LIST
	@SuppressWarnings("unchecked")
	public List<SmsMsGudsVO> selectSmsMsGudsByB5cSkuIdforBatch(SmsMsGudsVO smsMsGudsVO) throws Exception{
		return (List<SmsMsGudsVO>) list("smsMsGudsDAO.selectSmsMsGudsByB5cSkuIdforBatch",smsMsGudsVO);
	}
	
	public void insertSmsMsGuds_S(SmsMsGudsVO smsMsGudsVO) throws Exception{
		insert("smsMsGudsDAO.insertSmsMsGuds_S", smsMsGudsVO);
	}
	
	@SuppressWarnings("unchecked")
	public List<SmsMsGudsVO> selectSmsMsGudsByVO(SmsMsGudsVO smsMsGudsVo) throws Exception{
		return (List<SmsMsGudsVO>) list("smsMsGudsDAO.selectSmsMsGudsByVO",smsMsGudsVo);
	}
	//바코드(후보키)로 해당상품을 검색한다
	public SmsMsGudsVO selectSmsMsGudsByUpcId(String upcId) throws Exception{
		return (SmsMsGudsVO)select("smsMsGudsDAO.selectSmsMsGudsByUpcId",upcId);
	}
	//인박스 수량을 업데이트한다
	public void updateSmsMsGudsByInbxQty(SmsMsGudsVO smsMsGudsVo) throws Exception{
		update("smsMsGudsDAO.updateSmsMsGudsByInbxQty",smsMsGudsVo);
	}
	//다음 gudsId를 가져온다
	public String selectSmsMsGudsGudsId() throws Exception{
		return (String)select("smsMsGudsDAO.selectSmsMsGudsGudsId");
	}
	//상품삭제
	public void deleteSmsMsGuds(SmsMsGudsVO smsMsGudsVO) throws Exception{
		delete("smsMsGudsDAO.deleteSmsMsGuds",smsMsGudsVO);
	}
}
