package com.hp.tools;

import com.hp.tools.model.Column;
import com.hp.tools.model.Table;
import com.hp.tools.utils.DbUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TableTest {

    @Test
    public void testGetAllTables() {
        DbUtils dbUtils = DbUtils.getInstance(DbUtils.Type.ORACLE);
        Connection conn = dbUtils.getConnection();

        if (conn == null) {
            System.out.println("连接失败");
        } else {
            System.out.println("连接成功");
        }

        try {

            //取出当前用户的所有表
            List<Table> tableList = DbUtils.getInstance(DbUtils.Type.ORACLE).getAllTables();
            //List tableList = dbUtils.getColumnNameList(conn, "LOGIN");//表名称必须是大写的，取出当前表的所有列
            for (Table table : tableList) {
                System.out.println(table.getTableName() + ":" + table.getTableComment());
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testGetTableColumns() {
        DbUtils dbConn = DbUtils.getInstance(DbUtils.Type.ORACLE);
        Connection conn = dbConn.getOracleConnection();

        if (conn == null) {
            System.out.println("连接失败");
        } else {
            System.out.println("连接成功");
        }

        try {

            //取出当前用户的所有表
            List<Column> tableList = DbUtils.getInstance(DbUtils.Type.ORACLE).getStructOfTable("ACC_TERMINAL_TASK_LOG");//表名称必须是大写的，取出当前表的所有列
            for (Column column : tableList) {
                System.out.println(column);
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
