package guest.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import guest.Message;
import guest.MessageDao;


public class JdbcMessageDao implements MessageDao {
	
	private DataSource dataSource;
	private SQLExceptionTranslator exceptionTranslator;
	
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
	}
	
	
	@Override
	public List<Message> select(int start, int end) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id, name, message, creationTime from ( "
				+ " 	select rownum rnum, id, name, message, creationTime from ( "
				+ " 		select * from guestmessage m order by m.id desc "
				+ " 	) where rownum <= ?"
				+ " ) where rnum >= ? ";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				List<Message> messages = new ArrayList<Message>();
				do {
					Message m = new Message();
					m.setId(rs.getInt("id"));
					m.setName(rs.getString("name"));
					m.setMessage(rs.getString("message"));
					m.setCreationTime(rs.getTime("creationTime"));
					messages.add(m);
				} while(rs.next());
				
				return messages;
			}
			else {
				return Collections.emptyList();
			}
		} catch (SQLException ex) {
			// TODO: handle exception
			throw exceptionTranslator.translate("select", sql, ex);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(pstmt);
			JdbcUtils.closeConnection(conn);
		}
		
		
	}
	@Override
	public int counts() {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from guestmessage";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				return rs.getInt(1);
			}
			else {
				return 0;
			}
		} catch (SQLException ex) {
			// TODO: handle exception
			throw exceptionTranslator.translate("counts", sql, ex);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
			JdbcUtils.closeConnection(conn);
		}
	}
	
	@Override
	public int insert(Message message) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "insert into guestmessage(id, name, message, creationTime) values(id_seq.NEXTVAL,?,?,?)";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, message.getName());
			pstmt.setString(2, message.getMessage());
			pstmt.setTimestamp(3, new Timestamp(message.getCreationTime().getTime()));
			int insertedCount = pstmt.executeUpdate();
			if(insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select id_seq.CURRVAL from dual");
				if(rs.next()) {
					return rs.getInt(1);
				}
			}
			return -1;
		} catch(SQLException ex) {
			throw exceptionTranslator.translate("insert", sql, ex);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
			JdbcUtils.closeStatement(pstmt);
			JdbcUtils.closeConnection(conn);
		}
	}
	
	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from guestmessage where id= ? ";
		
		try {
			conn = dataSource.getConnection();
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			return pstmt.executeUpdate();
		} catch(SQLException ex) {
			throw exceptionTranslator.translate("delete", sql, ex);
		} finally {
			JdbcUtils.closeStatement(pstmt);
			JdbcUtils.closeConnection(conn);
		}
	}
	
	

}
