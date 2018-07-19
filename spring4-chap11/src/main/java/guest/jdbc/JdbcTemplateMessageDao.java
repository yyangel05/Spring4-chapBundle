package guest.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import guest.Message;
import guest.MessageDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcTemplateMessageDao implements MessageDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplateMessageDao(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<Message> MessageRowMapper = new MessageRowMapper();

	@Override
	public List<Message> select(int start, int end) {
		// TODO Auto-generated method stub
		List<Message> messages =
				jdbcTemplate.query("select id, name, message, creationTime from ( "
								+ "		select rownum rnum, id, name, message, creationTime from (  "
								+ "			select * from guestmessage m order by m.id desc "
								+ ")	 where rownum <=? "
								+ ") where rnum >= ?",
							new Object[] {end, start},
						MessageRowMapper
						);
		return messages;
	}

	@Override
	public int counts() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select count(*) from guestmessage", Integer.class);
	}

	@Override
	public int insert(final Message message) {
		// TODO Auto-generated method stub
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement pstmt = conn.prepareStatement(
						"insert into guestmessage(id,name, message, creationTime) values(id_seq.NEXTVAL,?,?,?)", new String[] {"id"});
				
				pstmt.setString(1, message.getName());
				pstmt.setString(2, message.getMessage());
				pstmt.setTimestamp(3, new Timestamp(message.getCreationTime().getTime()));
				
				return pstmt;
			}
		}, keyHolder);
		Number idNum = keyHolder.getKey();
		
		return idNum.intValue();
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("delete from guestmessage where id=?", id);
	}

}
