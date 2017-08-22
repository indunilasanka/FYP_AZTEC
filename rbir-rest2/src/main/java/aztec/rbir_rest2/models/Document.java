package aztec.rbir_rest2.models;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by subhahs on 12/06/2017.
 */
@JsonSerialize
public class Document {
	public String title;
	public MultipartFile file;
	public String category;
	public String summary;
	public String securityLevel;
	
    public MultipartFile getFile() {
		return file;
	}



	public void setFile(MultipartFile file) {
		this.file = file;
	}



	public Document(String title,String summary,String category){
        this.title = title;
        this.summary = summary;
        this.category = category;
    }
    
    
    
    public Document() {
	}



	public Document(String title){
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
