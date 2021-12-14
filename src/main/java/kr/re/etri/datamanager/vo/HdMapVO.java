package kr.re.etri.datamanager.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HdMapVO {
	private int hdmap_id = -1;
	private String hdmap_location;
	private String hdmap_location_id;
	private String hdmap_version;
	private int hdmap_layer = -1;
	private String hdmap_layer_name;
	private String hdmap_manufacturer;
	private String file_type;
	private String file_name;
	private String creation_datetime;
	private long file_size = -1;
	private String file_location;
	private String install_location;
	private List<MultipartFile> files;
	private boolean newest_version = false;
	private String hdmap_id_list;
	private int archive_type = -1;
}
