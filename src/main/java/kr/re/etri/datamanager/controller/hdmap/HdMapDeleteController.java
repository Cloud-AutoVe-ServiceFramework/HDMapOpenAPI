package kr.re.etri.datamanager.controller.hdmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.re.etri.datamanager.controller.ApiResponseMessage;
import kr.re.etri.datamanager.controller.CommonController;
import kr.re.etri.datamanager.service.HdMapService;
import kr.re.etri.datamanager.utils.FtpClientUtils;
import kr.re.etri.datamanager.vo.HdMapVO;

@RequestMapping
public class HdMapDeleteController extends CommonController {
    @Autowired
    HdMapService hdMapService;
	
	@DeleteMapping(value = "/{hdmap_id}")
    public ResponseEntity<?> delete(@ModelAttribute HdMapVO param) {
		super.param = param;
		
		ApiResponseMessage response = null;
    	try {
    		// 필수 항목 체크 (고정밀 맵 ID)
    		if (param.getHdmap_id() == -1) {
    			return ResponseEntity.badRequest().build();
    		}
    		
    		HdMapVO oldData = hdMapService.select(param);
    		if (oldData != null) {
    			String oldFileLocation = oldData.getFile_location();
    			FtpClientUtils.deleteFile(oldFileLocation);
        		
        		hdMapService.delete(param);	
    		}
	        response = responseSuccess();
    	} catch(Exception e) {
    		response = responseError(e.getMessage());
    	}
    	
    	return ResponseEntity.ok(response);
    }
}
