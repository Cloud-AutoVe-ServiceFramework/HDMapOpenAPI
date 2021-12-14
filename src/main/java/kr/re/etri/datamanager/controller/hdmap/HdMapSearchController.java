package kr.re.etri.datamanager.controller.hdmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.re.etri.datamanager.controller.ApiResponseMessage;
import kr.re.etri.datamanager.controller.CommonController;
import kr.re.etri.datamanager.service.HdMapService;
import kr.re.etri.datamanager.vo.HdMapVO;

@RequestMapping
public class HdMapSearchController extends CommonController {
    @Autowired
    HdMapService hdMapService;
    
    @GetMapping()
    public ResponseEntity<?> search(@ModelAttribute HdMapVO param) {
    	super.param = param;
    	
    	ApiResponseMessage response = null;
    	try {
	        response = responseSuccess(hdMapService.selectList(param));
    	} catch(Exception e) {
    		response = responseError(e.getMessage());
    	}
    	
    	return ResponseEntity.ok(response);
    }
}