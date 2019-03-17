package ivanbalseirogarcia.task;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Doc {

    private String id;

    private String journal;

    private String eissn;

    private Date publication_date;

    private String article_type;

    private List<String> author_display = new ArrayList();

    private List<String> _abstract = null;

    private String title_display;

    private Double score;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getEissn() {
        return eissn;
    }

    public void setEissn(String eissn) {
        this.eissn = eissn;
    }

    public Date getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(Date publication_date) {
        this.publication_date = publication_date;
    }

    public String getArticle_type() {
        return article_type;
    }

    public List<String> getAuthor_display() {
        return author_display;
    }

    public void setAuthor_display(List<String> author_display) {
        this.author_display = author_display;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
    }

    public String getTitle_display() {
        return title_display;
    }

    public void setTitle_display(String title_display) {
        this.title_display = title_display;
    }

    public List<String> getAbstract() {
        return _abstract;
    }

    public void setAbstract(List<String> _abstract) {
        this._abstract = _abstract;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @Override
    public String toString() {
        return "Doc{" +
                "id='" + id + '\'' +
                ", journal='" + journal + '\'' +
                ", eissn='" + eissn + '\'' +
                ", publicationDate='" + publication_date + '\'' +
                ", articleType='" + article_type + '\'' +
                ", authorDisplay=" + author_display +
                ", _abstract=" + _abstract +
                ", titleDisplay='" + title_display + '\'' +
                ", score=" + score +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    //Custom method to display all the information separated by comas to generate the CSV with it.
    public String toCSVString() {
        return id + ',' + journal + ',' + eissn + ',' + publication_date + ',' + article_type + ',' + author_display + ',' + _abstract + ',' + title_display + ',' + score +"\n";
    }

}
