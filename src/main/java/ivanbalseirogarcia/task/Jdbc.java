package ivanbalseirogarcia.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Jdbc {

    private final static String URL = "jdbc:postgresql://dumbo.db.elephantsql.com:5432/xuwoxird";
    private final static String USER = "xuwoxird";
    private final static String PASSWORD ="16S3_hDFqDp6NLv5KIX1bW9Uj7Hxzr52";

    //Connection method that establishes connection to the PostgreSQL database giving it and URL, a USER and a PASSWORD
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

}

