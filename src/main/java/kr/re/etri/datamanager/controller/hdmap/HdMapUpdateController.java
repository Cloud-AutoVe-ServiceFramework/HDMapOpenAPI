package kr.re.etri.datamanager.controller.hdmap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.re.etri.datamanager.config.FtpPropertiesConfig;
import kr.re.etri.datamanager.controller.ApiResponseMessage;
import kr.re.etri.datamanager.controller.CommonController;
import kr.re.etri.datamanager.service.HdMapService;
import kr.re.etri.datamanager.utils.FtpClientUtils;
import kr.re.etri.datamanager.utils.Utils;
import kr.re.etri.datamanager.vo.HdMapVO;

@RequestMapping
public class HdMapUpdateController extends CommonController {
    @Autowired
    HdMapService hdMapService;
	    
    @Autowired
    FtpPropertiesConfig ftpPropertiesConfig;
    
	@PutMapping(value="/{hdmap_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@ModelAttribute HdMapVO param) {
		super.param = param;
		
		ApiResponseMessage response = null;
    	try {
    		// 필수 항목 체크 (고정밀 맵 ID)
    		if (param.getHdmap_id() == -1) {
    			return ResponseEntity.badRequest().build();
    		}
    		
    		if (param.getFiles() != null && param.getFiles().size() > 0) {
    			HdMapVO oldData = hdMapService.select(param);
    			String oldFileLocation = oldData.getFile_location();
                FtpClientUtils.deleteFile(oldFileLocation);
    			
                List<MultipartFile> files = param.getFiles();
        		for (int i = 0; i < files.size(); i++) {
        			Map<String, Object> fileUploadData = Utils.saveFile(ftpPropertiesConfig.getHdmapPath(), files.get(i), super.logger);
        			
        			if (fileUploadData != null && !fileUploadData.isEmpty()) {
        				param.setFile_location((String) fileUploadData.get("file_location"));
            			param.setFile_name((String) fileUploadData.get("file_name"));
            			param.setFile_type((String) fileUploadData.get("file_type"));
            			param.setFile_size((long) fileUploadData.get("file_size"));
            			param.setCreation_datetime(Utils.convertGMTDateFormat(param.getCreation_datetime()));
            			
            			hdMapService.update(param);
        			}
        		}
    		} else {
    			hdMapService.update(param);
    		}
    		
	        response = responseSuccess();
    	} catch(Exception e) {
    		response = responseError(e.getMessage());
    	}
    	
    	return ResponseEntity.ok(response);
    }
}