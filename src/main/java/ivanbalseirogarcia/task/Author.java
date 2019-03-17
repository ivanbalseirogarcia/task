package ivanbalseirogarcia.task;

public class Author {
    private String idDoc;
    private String name;

    public Author(String idDoc, String name) {
        this.idDoc = idDoc;
        this.name = name;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public String getName() {
        return name;
    }
}
