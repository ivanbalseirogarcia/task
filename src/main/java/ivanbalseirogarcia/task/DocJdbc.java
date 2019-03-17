package ivanbalseirogarcia.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocJdbc {

    private static Connection con = null;

    //Method to avoid errors from the database when executing again
    public void clearDB() {

        final String clearAuthor = "DELETE FROM doc";
        final String clearDoc = "DELETE from author";

        PreparedStatement ps = null;

        try {

            con = Jdbc.getConnection();

            ps = con.prepareStatement(clearDoc);
            ps.executeUpdate();
            ps = con.prepareStatement(clearAuthor);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ex) {
                }
            }
        }

    }
    //Takes all the information from doc and the author
    public List<Doc> getDocs() {

        final String getDocs = "Select * from doc";
        final String getAuthors = "Select name from author where author.idDoc = ?";

        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        List<Doc> docs = new ArrayList<Doc>();
        List<String> authors = null;

        try {

            con = Jdbc.getConnection();

            ps = con.prepareStatement(getDocs);
            rs = ps.executeQuery();

            while (rs.next()) {

                Doc doc = new Doc();
                List<String> _abstract = new ArrayList<String>();
                _abstract.add(rs.getString("abstract"));
                doc.setAbstract(_abstract);
                doc.setArticle_type(rs.getString("article_type"));
                doc.setId(rs.getString("id"));
                doc.setJournal(rs.getString("journal"));
                doc.setEissn(rs.getString("eissn"));
                doc.setPublication_date(rs.getDate("publication_date"));
                doc.setTitle_display(rs.getString("title_display"));
                doc.setScore(rs.getDouble("score"));

                ps2 = con.prepareStatement(getAuthors);
                ps2.setString(1, doc.getId());
                rs2 = ps2.executeQuery();

                authors = new ArrayList<String>();

                while(rs2.next()){
                   authors.add(rs2.getString("name"));
                }

                rs2.close();
                ps2.close();

                doc.setAuthor_display(authors);
                docs.add(doc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ex) {
                }
            }
        }

        return docs;
    }

    //Method to save all information on the doc tables except for the Authors that go in Author table.
    public void saveDocs(List<Doc> docs) {

        final String insert = "INSERT INTO doc(id, journal, eissn, publication_date, article_type, abstract, title_display, score) VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement ps = null;

        try {

            con = Jdbc.getConnection();

            ps = con.prepareStatement(insert);

            for (Doc doc : docs) {
                ps.setString(1, doc.getId());
                ps.setString(2, doc.getJournal());
                ps.setString(3, doc.getEissn());
                ps.setString(4, doc.getPublication_date().toString());
                ps.setString(5, doc.getArticle_type());
                ps.setString(6, doc.getAbstract().get(0));
                ps.setString(7, doc.getTitle_display());
                ps.setDouble(8, doc.getScore());

                ps.executeUpdate();

                if (doc.getAuthor_display().size() > 0) {
                    for (int i = 0; i < doc.getAuthor_display().size(); i++) {
                        Author author = new Author(doc.getId(), doc.getAuthor_display().get(i));
                        saveAuthors(author);
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    //Method to save the authors in the author table in the database
    public void saveAuthors(Author author) {

        final String insert = "INSERT INTO author(idDoc,name) VALUES(?,?)";

        PreparedStatement ps = null;

        try {

            con = Jdbc.getConnection();

            ps = con.prepareStatement(insert);

            ps.setString(1, author.getIdDoc());
            ps.setString(2, author.getName());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    //Method to create the doc table (execute this one first). Call only one time with Author.
    public void createTableDoc() {

        final String createtableDoc = "CREATE TABLE doc(\n" +
                "   id                VARCHAR(250) PRIMARY KEY\n" +
                "  ,journal           VARCHAR(250)\n" +
                "  ,eissn             VARCHAR(250)\n" +
                "  ,publication_date  VARCHAR(250)\n" +
                "  ,article_type      VARCHAR(250)\n" +
                "  ,abstract        VARCHAR(4000)\n" +
                "  ,title_display     VARCHAR(250)\n" +
                "  ,score             DOUBLE PRECISION \n" +
                ");";

        PreparedStatement ps = null;

        try {

            con = Jdbc.getConnection();

            ps = con.prepareStatement(createtableDoc);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    //Method to create the author table (execute first the doc as it references doc). Only used one time.
    public void createTableAuthor() {

        final String createtableAuthor = "CREATE TABLE author(\n" +
                "   idDoc           VARCHAR(250) references doc(id)\n" +
                "  ,name           \tVARCHAR(100)\n" +
                ");";

        PreparedStatement ps = null;

        try {

            con = Jdbc.getConnection();

            ps = con.prepareStatement(createtableAuthor);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ex) {
                }
            }
        }
    }


}

