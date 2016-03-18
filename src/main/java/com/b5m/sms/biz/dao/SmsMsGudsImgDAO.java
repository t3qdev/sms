package com.b5m.sms.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsGudsImgVO;
import com.b5m.sms.vo.TbMsOrdBatchVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsGudsImgDAO")
public class SmsMsGudsImgDAO extends EgovAbstractDAO {
	@SuppressWarnings("unchecked")
	public List<SmsMsGudsImgVO> selectSmsMsGudsImg(String gudsId){
		return (List<SmsMsGudsImgVO>)list("smsMsGudsImgDAO.selectSmsMsGudsImg",gudsId);
	}
	
	public void insertTbMsGudsImgToSmsMsGudsImg(TbMsOrdBatchVO tbMsOrdBatchVO){
		insert("smsMsGudsImgDAO.insertTbMsGudsImgToSmsMsGudsImg",tbMsOrdBatchVO);
	}
	//PO확정에서 이미지 삽입
	public void insertSmsMsGudsImg(SmsMsGudsImgVO smsMsGudsImgVO){
		insert("smsMsGudsImgDAO.insertSmsMsGudsImg",smsMsGudsImgVO);
	}
	//이미지 식제
	public void deleteSmsMsGudsImg(SmsMsGudsImgVO smsMsGudsImgVO){
		delete("smsMsGudsImgDAO.deleteSmsMsGudsImg",smsMsGudsImgVO);
	}
	//이미지 일괄로 가져오기
	@SuppressWarnings("unchecked")
	public List<SmsMsGudsImgVO> selectSmsMsGudsImgAll(){
		return (List<SmsMsGudsImgVO>)list("smsMsGudsImgDAO.selectSmsMsGudsImgAll");
	}
	//이미지 코드검색으로 가져오기 
	public SmsMsGudsImgVO selectSmsMsGudsImgByCd(SmsMsGudsImgVO smsMsGudsImgVo){
		return (SmsMsGudsImgVO)select("smsMsGudsImgDAO.selectSmsMsGudsImgByCd",smsMsGudsImgVo);
	}
	
	public void deleteSmsMsGudsImg_S(){
		delete("smsMsGudsImgDAO.deleteSmsMsGudsImg_S");
	}
}
