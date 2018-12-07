package com.cusc;

import org.junit.Test;

import java.sql.*;

/**
 * @Author Administrator
 * @Date 2018/11/30 0030 16 51
 * @Description
 */
public class PhoenixTest {
    
    @Test
    public void test01() {
        try {
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
            
            Connection con = DriverManager.getConnection("jdbc:phoenix:172.23.101.47,172.23.101.48,172.23.101.49","","");
            Statement stmt = con.createStatement();
            stmt.execute("DROP TABLE IF EXISTS test");
            stmt.execute("CREATE TABLE test (id INTEGER NOT NULL PRIMARY KEY, f1.column1 VARCHAR, f1.column2 VARCHAR, f2.column2 VARCHAR, f2.column3 VARCHAR)");
            stmt.execute("CREATE INDEX test_idx on test(f1.column1, f1.column2)");
            stmt.executeUpdate("UPSERT INTO test VALUES (1, 'World!')");
            stmt.executeUpdate("UPSERT INTO test VALUES (2, 'Hello')");
            stmt.executeUpdate("UPSERT INTO test VALUES (3, 'World!')");
            con.commit();
            PreparedStatement statement = con.prepareStatement("SELECT id FROM test WHERE column1='Hello'");
            ResultSet rset = statement.executeQuery();
            while (rset.next()) {
                System.out.println(rset.getInt(1));
            }
            
            rset.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}
