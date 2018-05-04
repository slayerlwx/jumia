package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 连接池工具类
 */
public class DBUtil {
    private static Logger log = LoggerFactory.getLogger(DBUtil.class);
    private static DataSource dataSource;

    static {
        try {
            dataSource = DruidDataSourceFactory.createDataSource(Property.getProperties(null));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("连接池出错：" + e.getLocalizedMessage());
        }
    }

    /**
     * 获取链接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    /**
     * 销毁链接
     *
     * @throws SQLException
     */
    public static void destory(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (conn != null) conn.close();
    }

    public static void insert(String sql, List<Object> data) throws Exception {
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int j = 0; j < data.size(); j++) {
            ps.setObject(j + 1, data.get(j));
        }
        ps.execute();
        DBUtil.destory(conn, ps, null);
    }

    /**
     * 批量插入
     *
     * @param sql
     * @param data
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void insertBatch(String sql, List<List<Object>> data) throws Exception {
        Connection conn = DBUtil.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                ps.setObject(j + 1, data.get(i).get(j));
            }
            ps.addBatch();
        }
        ps.executeBatch();
        conn.commit();
        DBUtil.destory(conn, ps, null);
    }

    /**
     * 批量插入
     *
     * @param sql
     * @param data
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public synchronized static void insertBatchByMap(String sql, Map<String, Object> data) {
        try {
            Connection conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            int j = 1;
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                ps.setObject(j, entry.getValue());
                j++;
            }
            ps.executeUpdate();
            conn.commit();
            DBUtil.destory(conn, ps, null);
        } catch (Exception ex) {
            log.info("批量插入错误：" + ex.getMessage());
            System.out.println("批量插入错误：" + ex.getMessage());
        }
    }

    /**
     * 获取sql结果
     *
     * @param sql
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<List<Object>> getResultData(String sql) throws Exception {
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<List<Object>> data = new ArrayList<List<Object>>();
        ResultSetMetaData rsm = rs.getMetaData();
        while (rs.next()) {
            List<Object> row = new ArrayList<Object>();
            int columnCount = rsm.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
            data.add(row);
        }
        DBUtil.destory(conn, ps, rs);
        return data;
    }

    public static List<Map<String, Object>> getMapResultData(String sql) throws Exception {
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        ResultSetMetaData rsm = rs.getMetaData();
        while (rs.next()) {
            Map<String, Object> row = new HashMap<String, Object>();
            int columnCount = rsm.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                row.put(rsm.getColumnName(i), rs.getObject(i));
            }
            data.add(row);
        }
        DBUtil.destory(conn, ps, rs);
        return data;
    }
}
