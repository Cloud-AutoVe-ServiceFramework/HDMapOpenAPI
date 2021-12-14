package kr.re.etri.datamanager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.re.etri.datamanager.vo.HdMapVO;

@Mapper
public interface HdMapMapper {

	List<HdMapVO> selectList(HdMapVO param);
	
	List<HdMapVO> selectDownloadList(HdMapVO param);
	
	HdMapVO select(HdMapVO param);
	
	int insert(HdMapVO param);
	
	int update(HdMapVO param);
	
	int delete(HdMapVO param);
}
