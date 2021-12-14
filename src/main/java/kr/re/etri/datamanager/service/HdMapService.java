package kr.re.etri.datamanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.re.etri.datamanager.mapper.HdMapMapper;
import kr.re.etri.datamanager.vo.HdMapVO;

@Service
public class HdMapService {
	@Autowired
	HdMapMapper hdMapMapper;
	
	public List<HdMapVO> selectList(HdMapVO param) throws Exception {
		return hdMapMapper.selectList(param);
	}
	
	public List<HdMapVO> selectDownloadList(HdMapVO param) throws Exception {
		return hdMapMapper.selectDownloadList(param);
	}
	
	public HdMapVO select(HdMapVO param) throws Exception {
		return hdMapMapper.select(param);
	}
	
	public int insert(HdMapVO param) throws Exception {
		return hdMapMapper.insert(param);
	}
	
	public int update(HdMapVO param) throws Exception {
		return hdMapMapper.update(param);
	}
	
	public int delete(HdMapVO param) throws Exception {
		return hdMapMapper.delete(param);
	}
}
