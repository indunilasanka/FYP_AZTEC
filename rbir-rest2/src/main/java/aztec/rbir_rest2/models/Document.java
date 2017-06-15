package aztec.rbir_rest2.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by subhahs on 12/06/2017.
 */
@JsonSerialize
public class Document {
    private String title;
    private String summary;
    private String category;

    public Document(String title,String summary,String category){
        this.title = title;
        this.summary = summary;
        this.category = category;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getSummary(){
        return this.summary;
    }

    public void setSummary(String summary){
        this.title = summary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
