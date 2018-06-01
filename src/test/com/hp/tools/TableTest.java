package com.hp.tools;

import model.Column;
import model.Table;
import org.junit.Test;
import utils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TableTest {

    @Test
    public void testGetAllTables() {
        DbUtils dbConn = DbUtils.getInstance();
        Connection conn = dbConn.getConnection();

        if (conn == null) {
            System.out.println("连接失败");
        } else {
            System.out.println("连接成功");
        }

        try {

            //取出当前用户的所有表
            List<Table> tableList = DbUtils.getInstance().getAllTables();
            //List tableList = dbConn.getColumnNameList(conn, "LOGIN");//表名称必须是大写的，取出当前表的所有列
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
        DbUtils dbConn = DbUtils.getInstance();
        Connection conn = dbConn.getConnection();

        if (conn == null) {
            System.out.println("连接失败");
        } else {
            System.out.println("连接成功");
        }

        try {

            //取出当前用户的所有表
            List<Column> tableList = DbUtils.getInstance().getStructOfTable("ACC_TERMINAL_TASK_LOG");//表名称必须是大写的，取出当前表的所有列
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
