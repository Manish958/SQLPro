package com.project.SQLpro.Repo;

import com.project.SQLpro.model.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class SQLRepo {

    private final JdbcTemplate template;
    @Autowired
    public SQLRepo(JdbcTemplate template) {
        this.template = template;
    }

    public List<Sql> executeCustomQuery(String query) {
        System.out.print("Enter Repo funtion");
    	if (!isValidQuery(query)) {
            System.out.print("❌"+ query);
            return Collections.emptyList();
        }
        

        try {
            List<Sql> result = template.query(query, (rs, rowNum) -> {
                Sql s = new Sql();
                s.setId(rs.getInt("ID"));
                s.setName(rs.getString("NAME"));
                s.setTech(rs.getString("TECH"));
                return s;
            });

            return result;
        } catch (Exception e) {
        	System.out.print(e);
            return Collections.emptyList();
        }
    }
    private boolean isValidQuery(String query) {
        String regex = "^(?i)\\s*SELECT\\s+.*";
        return Pattern.matches(regex, query);
    }

}   