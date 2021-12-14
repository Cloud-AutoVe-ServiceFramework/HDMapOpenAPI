package kr.re.etri.datamanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.re.etri.datamanager.controller.CodeController;
import kr.re.etri.datamanager.controller.CommonController;
import kr.re.etri.datamanager.controller.DownloadResultController;
import kr.re.etri.datamanager.controller.advehicle.info.AdvehicleInfoAddController;
import kr.re.etri.datamanager.controller.advehicle.info.AdvehicleInfoDeleteController;
import kr.re.etri.datamanager.controller.advehicle.info.AdvehicleInfoSearchController;
import kr.re.etri.datamanager.controller.advehicle.info.AdvehicleInfoUpdateController;
import kr.re.etri.datamanager.controller.advehicle.softwares.AdvehicleSWAddController;
import kr.re.etri.datamanager.controller.advehicle.softwares.AdvehicleSWDeleteController;
import kr.re.etri.datamanager.controller.advehicle.softwares.AdvehicleSWDownloadController;
import kr.re.etri.datamanager.controller.advehicle.softwares.AdvehicleSWSearchController;
import kr.re.etri.datamanager.controller.advehicle.softwares.AdvehicleSWUpdateController;
import kr.re.etri.datamanager.controller.edge.info.EdgeInfoAddController;
import kr.re.etri.datamanager.controller.edge.info.EdgeInfoDeleteController;
import kr.re.etri.datamanager.controller.edge.info.EdgeInfoSearchController;
import kr.re.etri.datamanager.controller.edge.info.EdgeInfoUpdateController;
import kr.re.etri.datamanager.controller.edge.softwares.EdgeSWAddController;
import kr.re.etri.datamanager.controller.edge.softwares.EdgeSWDeleteController;
import kr.re.etri.datamanager.controller.edge.softwares.EdgeSWDownloadController;
import kr.re.etri.datamanager.controller.edge.softwares.EdgeSWSearchController;
import kr.re.etri.datamanager.controller.edge.softwares.EdgeSWUpdateController;
import kr.re.etri.datamanager.controller.hdmap.HdMapAddController;
import kr.re.etri.datamanager.controller.hdmap.HdMapDeleteController;
import kr.re.etri.datamanager.controller.hdmap.HdMapDownloadController;
import kr.re.etri.datamanager.controller.hdmap.HdMapSearchController;
import kr.re.etri.datamanager.controller.hdmap.HdMapUpdateController;

@Configuration
public class ControllerConfig {

    @Autowired
    private PropertiesConfig propertiesConfig;
    
	@Bean
    public CommonController myController() {
		CommonController controller;
		
		if (propertiesConfig.isHdmapAdd()) {
			controller = new HdMapAddController();
		} else if (propertiesConfig.isHdmapUpdate()) {
			controller = new HdMapUpdateController();
		} else if (propertiesConfig.isHdmapDelete()) {
			controller = new HdMapDeleteController();
		} else if (propertiesConfig.isHdmapSearch()) {
			controller = new HdMapSearchController();
		} else if (propertiesConfig.isHdmapDownload()) {
			controller = new HdMapDownloadController();
		} else if (propertiesConfig.isCodeList()){
			controller = new CodeController();
		} else {
			controller = new DownloadResultController();
		}
		
        return controller;
    }
}
