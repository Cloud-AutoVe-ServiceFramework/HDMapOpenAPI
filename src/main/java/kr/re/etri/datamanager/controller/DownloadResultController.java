package kr.re.etri.datamanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.re.etri.datamanager.vo.DownloadResultVO;

@RequestMapping()
public class DownloadResultController extends CommonController {
	
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> downloadResult(@RequestBody DownloadResultVO param) {
    	super.param = param;
    	
    	ApiResponseMessage response = null;
    	try {
	        response = responseSuccess();
    	} catch(Exception e) {
    		response = responseError(e.getMessage());
    	}
    	
    	return ResponseEntity.ok(response);
    }
}