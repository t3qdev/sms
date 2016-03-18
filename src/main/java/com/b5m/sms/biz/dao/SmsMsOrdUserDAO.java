package com.b5m.sms.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

import com.b5m.sms.vo.SmsMsOrdUserVO;


@Repository("smsMsOrdUserDAO")
public class SmsMsOrdUserDAO extends EgovAbstractDAO {

    @SuppressWarnings("unchecked")
	public List<SmsMsOrdUserVO> selectSmsMsOrdUserByOrdNo(String ordNo){
    	return (List<SmsMsOrdUserVO>)list("smsMsOrdUserDAO.selectSmsMsOrdUserByOrdNo", ordNo);
    }
    
    public void insertSmsMsOrdUser_S(SmsMsOrdUserVO smsMsOrdUserVO){
    	insert("smsMsOrdUserDAO.insertSmsMsOrdUser_S",smsMsOrdUserVO);
    }
    
    public void deleteSmsMsOrdUserByOrdNo(String ordNo){
    	delete("smsMsOrdUserDAO.deleteSmsMsOrdUserByOrdNo",ordNo);
    }
    
    public void deleteSmsMsOrdUser_S( ){
    	delete("smsMsOrdUserDAO.deleteSmsMsOrdUser_S");
    }
    
    
}
