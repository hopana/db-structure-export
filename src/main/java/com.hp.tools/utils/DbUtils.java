package com.hp.tools.utils;

import com.hp.tools.model.Column;
import com.hp.tools.model.Table;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 获取数据库连接
 *
 * @author hupan
 */
public class DbUtils {

    private int dbType;
    private static String db;

    /**
     * 单例
     */
    private static DbUtils instance = null;

    /**
     * 数据库驱动
     */
    private static String oracleDriverName;
    private static String mysqlDriverName;
    /**
     * 数据库连接url
     */
    private static String oracleUrl;
    private static String mysqlUrl;
    /**
     * 用户名
     */
    private static String oracleUsername;
    private static String mysqlUsername;
    /**
     * 密码
     */
    private static String oraclePassword;
    private static String mysqlPassword;

    static {
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = DbUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            // 使用properties对象加载输入流
            properties.load(new InputStreamReader(in, StandardCharsets.UTF_8));

            oracleDriverName = properties.getProperty("oracle.jdbc.driverName");
            mysqlDriverName = properties.getProperty("mysql.jdbc.driverName");
            oracleUrl = properties.getProperty("oracle.jdbc.url");
            mysqlUrl = properties.getProperty("mysql.jdbc.url");
            oracleUsername = properties.getProperty("oracle.jdbc.username");
            mysqlUsername = properties.getProperty("mysql.jdbc.username");
            oraclePassword = properties.getProperty("oracle.jdbc.password");
            mysqlPassword = properties.getProperty("mysql.jdbc.password");

            db = properties.getProperty("mysql.db");
        } catch (IOException e) {
            System.err.println("jdbc.properties文件不存在！");
        }
    }

    private DbUtils(int dbType) {
        this.dbType = dbType;
    }

    /**
     * 单例
     *
     * @return DbUtils
     */
    public static DbUtils getInstance(Type type) {
        if (instance == null) {
            instance = new DbUtils(type.getType());
        }

        return instance;
    }

    public Connection getConnection() {
        if (dbType == Type.ORACLE.getType()) {
            return getOracleConnection();
        }

        return getMysqlConnection();
    }

    /**
     * 获取数据库连接
     *
     * @return Connection
     */
    public Connection getOracleConnection() {
        Connection conn = null;

            try {
                Class.forName(oracleDriverName);
                try {
                    conn = DriverManager.getConnection(oracleUrl, oracleUsername, oraclePassword);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        return conn;
    }

    /**
     * 获取数据库连接
     *
     * @return Connection
     */
    public Connection getMysqlConnection() {
        Connection conn = null;

            try {
                Class.forName(mysqlDriverName);
                try {
                    conn = DriverManager.getConnection(mysqlUrl, mysqlUsername, mysqlPassword);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        return conn;
    }

    /**
     * 执行sql
     *
     * @param sql
     * @return
     */
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        Connection conn = getConnection();

        try {
            try (Statement sm = conn != null ? conn.createStatement() : null) {
                if (sm != null) {
                    rs = sm.executeQuery(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;

    }

    private void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        try {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库中所有表的表名，并添加到列表结构中
     *
     * @return 表名集合
     * @throws SQLException
     */
    public List<String> getTableNameList() {
        List<String> tableNameList = new ArrayList<>();

        Connection conn = getConnection();
        DatabaseMetaData dbmd = null;
        try {
            if (conn == null || conn.isClosed()) {
                DbUtils db = DbUtils.getInstance(Type.MYSQL);
                conn = db.getConnection();
            }
            dbmd = conn.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (dbmd != null) {
            //访问当前用户下的所有表
            ResultSet rs = null;
            try {
                rs = dbmd.getTables(conn.getCatalog(), oracleUsername, "%", new String[]{"TABLE"});
                while (rs.next()) {
                    tableNameList.add(rs.getString("table_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeAll(conn, null, rs);
            }
        }

        return tableNameList;

    }

    /**
     * 从数据库中获取指定条件的表名
     *
     * @return
     */
    public List<Table> getAllTables() {
        List<Table> tableList = new ArrayList<>();

        Connection conn = getConnection();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
            if (dbType == Type.MYSQL.getType()) {
                rs = statement.executeQuery("select table_name,table_comment from information_schema.tables where table_schema='" + db + "'");
            } else {
                rs = statement.executeQuery("select t.table_name,c.comments table_comment from user_tables t left join user_tab_comments c on t.table_name = c.table_name");
            }

            while (rs.next()) {
                Table table = new Table();
                table.setTableName(rs.getString("table_name"));
                table.setTableComment(rs.getString("table_comment"));
                tableList.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableList.sort((o1, o2) -> o1.getTableName().compareToIgnoreCase(o2.getTableName()));

        return tableList;
    }

    /**
     * 获取数据表中所有列的列名，并添加到列表结构中
     *
     * @param tableName 表名
     * @return 列名集合
     * @throws SQLException
     */
    public List<String> getColumnNameList(String tableName) {
        List<String> columnNameList = new ArrayList<>();

        Connection conn = getConnection();
        DatabaseMetaData dbmd = null;
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (dbmd != null) {
            ResultSet rs = null;
            try {
                rs = dbmd.getColumns(conn.getCatalog(), "%", tableName, "%");
                while (rs.next()) {
                    columnNameList.add(rs.getString("column_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeAll(conn, null, rs);
            }
        }

        columnNameList.sort(String::compareToIgnoreCase);

        return columnNameList;
    }


    /**
     * 根据表名获取表结构信息
     *
     * @param tableName
     * @return
     */

    public List<Column> getStructOfTable(String tableName) {
        List<Column> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        if (dbType == Type.MYSQL.getType()) {
            sql.append("SELECT column_name,data_type,character_maximum_length data_length,numeric_precision data_precision,numeric_scale data_Scale,is_nullable nullable,column_default data_default,column_comment FROM information_schema.columns ");
            sql.append("WHERE table_name=? and table_schema=?");
        } else {
            sql.append("SELECT u.column_name,u.data_type,u.data_length,u.data_precision,u.data_Scale,u.nullable,u.data_default,c.comments column_comment FROM user_tab_columns u, user_col_comments c ");
            sql.append("WHERE u.table_name=? and u.table_name=c.table_name and c.column_name=u.column_name");
        }

        Connection conn = getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement(sql.toString());
            if (dbType == Type.MYSQL.getType()) {
                pstm.setString(1, tableName);
                pstm.setString(2, db);
            } else {
                pstm.setString(1, tableName);
            }

            rs = pstm.executeQuery();

            while (rs.next()) {
                Column entity = new Column();
                entity.setColumnName(rs.getString("column_name"));

                String dataType = rs.getString("data_type");
                int dataLength = rs.getInt("data_length");
                int dataPrecision = rs.getInt("data_precision");
                int data_Scale = rs.getInt("data_Scale");

                if ("NUMBER".equalsIgnoreCase(dataType) || "tinyint".equalsIgnoreCase(dataType) || "smallint".equalsIgnoreCase(dataType) || "mediumint".equalsIgnoreCase(dataType) || "int".equalsIgnoreCase(dataType) || "integer".equalsIgnoreCase(dataType)
                        || "bigint".equalsIgnoreCase(dataType)) {
                    entity.setDataType(dataType + "(" + dataPrecision + ")");
                } else if ("float".equalsIgnoreCase(dataType) || "double".equalsIgnoreCase(dataType) || "decimal".equalsIgnoreCase(dataType)){
                    entity.setDataType(dataType + "(" + dataPrecision + "," + data_Scale + ")");
                } else if ("TIMESTAMP(6)".equalsIgnoreCase(dataType)) {
                    entity.setDataType(dataType);
                } else {
                    entity.setDataType(dataType + "(" + dataLength + ")");
                }

                entity.setDataLength(dataLength);
                entity.setDataPrecision(dataPrecision);
                entity.setDataScale(rs.getInt("data_Scale"));
                entity.setNullable(rs.getString("nullable"));
                entity.setDataDefault(rs.getString("data_default"));
                entity.setColumnComments(rs.getString("column_comment"));

                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstm, rs);
        }

        return list;
    }

    public enum Type {
        ORACLE(1), MYSQL(2);

        Type(int type) {
            this.type = type;
        }

        int type;

        public int getType() {
            return type;
        }

    }

}