package parser;

import java.util.regex.Pattern;

public class QueryParser {

    public static boolean parser(String query) throws Exception {
        boolean isSyntaxCorrect;
        String lowercaseQuery = query.toLowerCase();
        if (query == null || query.isEmpty()) {
            throw new Exception("The query syntax is invalid.");
        } else if (lowercaseQuery.contains("create database")) {
            isSyntaxCorrect = Pattern.matches("^(?i)(CREATE\\sDATABASE\\s[a-zA-Z\\d]+;)$", lowercaseQuery);
            if (!isSyntaxCorrect) {
                throw new Exception("Invalid query for creating database!");
            }
        } else if (lowercaseQuery.contains("use database")) {
            isSyntaxCorrect = Pattern.matches("^(?i)(USE\\sDATABASE\\s[a-zA-Z\\d]+;)$", lowercaseQuery);
            if (!isSyntaxCorrect) {
                throw new Exception("Invalid query for using database!");
            }
        } else if (lowercaseQuery.contains("create table")) {
            isSyntaxCorrect = Pattern.matches("^(?i)(CREATE\\sTABLE\\s[a-zA-Z\\d]+\\s\\(([a-zA-Z\\d]+\\s(INT|TEXT|FLOAT|BOOLEAN)(\\sPRIMARY KEY|\\sREFERENCES\\s[a-zA-Z\\d]+\\([a-zA-Z\\d]+\\))?(,\\s[a-zA-Z\\d]+\\s(INT|TEXT|FLOAT|BOOLEAN)(\\sPRIMARY KEY|\\sREFERENCES\\s[a-zA-Z\\d]+\\([a-zA-Z\\d]+\\))?)*)\\);)$", query);
            if (!isSyntaxCorrect) {
                throw new Exception("Invalid query for creating table!");
            }

        } else if (lowercaseQuery.contains("insert into")) {
            isSyntaxCorrect = Pattern.matches("^(?i)(INSERT INTO (\\S+).*\\s+\\((.*?)\\).*\\s+VALUES.*\\s+\\((.*?)\\).*\\;)$", query);
            if (!isSyntaxCorrect) {
                throw new Exception("Invalid query for inserting records in table database!");
            }
        } else if (lowercaseQuery.contains("select")) {
            isSyntaxCorrect = Pattern.matches("^(?i)(SELECT\\s+(.+?)\\s*\\s+FROM\\s+(.*?)\\s*(WHERE\\s+(.*?)\\s*)?;)$", lowercaseQuery);
            System.out.println(isSyntaxCorrect);
            if (!isSyntaxCorrect) {
                throw new Exception("Invalid query for select operation!");
            }
        }else if(lowercaseQuery.contains("update")){
            isSyntaxCorrect = Pattern.matches("UPDATE\\s+(\\S+)\\s*SET\\s+(.*?)\\s*(WHERE\\s+(.*?))?;",lowercaseQuery);
            if (!isSyntaxCorrect) {
                throw new Exception("Invalid query for updating record!");
            }

        }else if(lowercaseQuery.contains("delete")){
            isSyntaxCorrect = Pattern.matches("DELETE FROM\\s+(\\S+)\\s*(WHERE\\s(.*?)\\s*)?;",lowercaseQuery);{
                if(!isSyntaxCorrect){
                    throw new Exception("Invalid query for deleting record!");
                }
            }
        }else{
            throw new Exception("The query syntax is invalid.");
        }
        return isSyntaxCorrect;
    }
}