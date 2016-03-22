package com.b5m.sms.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.TbMsCmnCdVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;



@Repository("tbMsCmnCdDAO")
public class TbMsCmnCdDAO extends EgovAbstractDAO {

	
	@SuppressWarnings("unchecked")
	public List<TbMsCmnCdVO> selectCmnCdByCdNm(String cdNm) {
		return (List<TbMsCmnCdVO>)list("tbMsCmnCdDAO.selectCmnCdByCdNm", cdNm); // 2015/01/03 이현석 수정
	}
	
	
	//TbMsCmnCd 테이블 기본 Insert
	public void insertTbMsCmnCd(TbMsCmnCdVO vo)throws Exception{
		insert("tbMsCmnCdDAO.insertTbMsCmnCd",vo);
	}
	//TbMsCmnCd 테이블 기본 delete
	public void deleteTbMsCmnCd(String cd)throws Exception{
		delete("tbMsCmnCdDAO.deleteTbMsCmnCd",cd);
	}
	//TbMsCmnCd 테이블 기본 update
	public void updateTbMsCmnCd(TbMsCmnCdVO vo)throws Exception{
		update("tbMsCmnCdDAO.updateTbMsCmnCd",vo);
	}


	//상위코드가 같은 코드값들을 가져온다
	@SuppressWarnings("unchecked")
	public List<TbMsCmnCdVO> selectCmnCdByCd(String cd){
		return (List<TbMsCmnCdVO>)list("tbMsCmnCdDAO.selectCmnCdByCd",cd);
	}
	//모든 상위코드값을 가지고 온다
	@SuppressWarnings("unchecked")
	public List<TbMsCmnCdVO> selectCmnCdMainCd() {
		return (List<TbMsCmnCdVO>)list("tbMsCmnCdDAO.selectCmnCdMainCd","");
	}
	
	//가장큰 상위코드값을 가지고온다
	public String selectCmnCdMaxCd() {
		return (String)select("tbMsCmnCdDAO.selectCmnCdMaxCd");
	}
	
	//상위코드의 값이 같은 코드들중에서 가장 큰 코드들을 가져온다 _been
	@SuppressWarnings("unchecked")
	public List<TbMsCmnCdVO> selectCmnCdMainMaxCd() {
		return  (List<TbMsCmnCdVO>)list("tbMsCmnCdDAO.selectCmnCdMainMaxCd");
	}

	@SuppressWarnings("unchecked")
	public List<TbMsCmnCdVO> selectCmnCdByEtcNCdVal(String etc) {
		return  (List<TbMsCmnCdVO>)list("tbMsCmnCdDAO.selectCmnCdByEtcNCdVal", etc);
	}
	
}
