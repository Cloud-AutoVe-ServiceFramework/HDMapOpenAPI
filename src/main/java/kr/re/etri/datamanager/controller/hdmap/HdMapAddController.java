package kr.re.etri.datamanager.controller.hdmap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.re.etri.datamanager.config.FtpPropertiesConfig;
import kr.re.etri.datamanager.controller.ApiResponseMessage;
import kr.re.etri.datamanager.controller.CommonController;
import kr.re.etri.datamanager.service.HdMapService;
import kr.re.etri.datamanager.utils.Utils;
import kr.re.etri.datamanager.vo.HdMapVO;

@RequestMapping
public class HdMapAddController extends CommonController {
    @Autowired
    HdMapService hdMapService;
	
    @Autowired
    FtpPropertiesConfig ftpPropertiesConfig;
	
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> add(@ModelAttribute HdMapVO param) {
    	super.param = param;
    	
    	ApiResponseMessage response = null;
    	try {
    		// 필수 항목 체크
    		if (param.getFiles() == null || param.getFiles().size() == 0 || param.getHdmap_location() == null || param.getHdmap_location_id() == null || param.getHdmap_version() == null || param.getHdmap_layer() == -1 
    				|| param.getHdmap_manufacturer() == null || param.getInstall_location() == null || param.getCreation_datetime() == null ) {
    			return ResponseEntity.badRequest().build();
    		}
    		
    		List<MultipartFile> files = param.getFiles();
    		for (int i = 0; i < files.size(); i++) {
    			Map<String, Object> fileUploadData = Utils.saveFile(ftpPropertiesConfig.getHdmapPath(), files.get(i), super.logger);
    			
    			if (fileUploadData != null && !fileUploadData.isEmpty()) {
    				param.setFile_location((String) fileUploadData.get("file_location"));
        			param.setFile_name((String) fileUploadData.get("file_name"));
        			param.setFile_type((String) fileUploadData.get("file_type"));
        			param.setFile_size((long) fileUploadData.get("file_size"));
        			param.setCreation_datetime(Utils.convertGMTDateFormat(param.getCreation_datetime()));
        			
        			hdMapService.insert(param);	
    			}
    		}
	        
	        response = responseSuccess();
    	} catch(Exception e) {
    		response = responseError(e.getMessage());
    	}
    	
    	return ResponseEntity.ok(response);
    }
}
