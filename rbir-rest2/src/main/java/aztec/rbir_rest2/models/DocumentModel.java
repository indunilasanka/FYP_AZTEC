package aztec.rbir_rest2.models;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by subhahs on 12/06/2017.
 */
@JsonSerialize
public class DocumentModel {
	private String title;
	private File file;
	private String category;
	private String summary;
	private String securityLevel;
	
    public File getFile() {
		return file;
	}



	public void setFile(File file) {
		this.file = file;
	}



	public DocumentModel(String title, String summary, String category){
        this.title = title;
        this.summary = summary;
        this.category = category;
    }
    
    
    
    public DocumentModel() {
	}



	public DocumentModel(String title){
    	this.title = title;
    }
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
//	public File getFile() {
//		return file;
//	}
//	public void setFile(File file) {
//		this.file = file;
//	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

}
