package kr.re.etri.datamanager.controller.hdmap;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.re.etri.datamanager.controller.CommonController;
import kr.re.etri.datamanager.service.HdMapService;
import kr.re.etri.datamanager.utils.FtpClientUtils;
import kr.re.etri.datamanager.vo.HdMapVO;

@RequestMapping
public class HdMapDownloadController extends CommonController {
    @Autowired
    HdMapService hdMapService;
    
    @GetMapping()
    public ResponseEntity<?> download(@ModelAttribute HdMapVO param) {
    	super.param = param;
    	
        try {
        	// 필수 항목 체크
    		if (param.getHdmap_id() == -1  && param.getHdmap_location_id() == null && param.getHdmap_location() == null && param.getHdmap_id_list() == null) {
    			return ResponseEntity.badRequest().build();
    		}
    		
        	List<HdMapVO> list = hdMapService.selectDownloadList(param);
            if (list != null && list.size() > 0) {
            	ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream zipOut = new ZipOutputStream(baos);
			
				for (HdMapVO data : list) {
					int id = data.getHdmap_id();
					String fileName = data.getFile_name();
	                String filePath = data.getFile_location();
	                String installLocationPath = data.getInstall_location();
	                
	                ByteArrayOutputStream os = new ByteArrayOutputStream();
	                FtpClientUtils.loadFile(filePath, os);
					
					ZipEntry zipEntry = new ZipEntry(id + "/" + fileName);
					zipEntry.setExtra(installLocationPath.getBytes("UTF-8"));
					
			        zipOut.putNextEntry(zipEntry);
			        zipOut.write(os.toByteArray());
			        
			        os.close();
            	}
			    
				zipOut.closeEntry();
				zipOut.close();
				
				baos.close();

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header("Content-Disposition", "attachment; filename=\"hdmap.zip\"")
                        .body(baos.toByteArray());
            } else {
            	return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
        	return ResponseEntity.internalServerError().build();
        }
        
    }
}