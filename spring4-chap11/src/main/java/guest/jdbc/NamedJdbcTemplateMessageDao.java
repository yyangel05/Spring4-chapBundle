package guest.jdbc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import guest.Message;
import guest.MessageDao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class NamedJdbcTemplateMessageDao implements MessageDao {
	
	private NamedParameterJdbcTemplate template;
	
	public NamedJdbcTemplateMessageDao(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public List<Message> select(int start, int end) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("end", end);
		List<Message> messages =
				template.query( 
							  "select id, name, message, creationTime from ( "
							+ "		select rownum rnum, id, name, message, creationTime from (  "
							+ "			select * from guestmessage m order by m.id desc "
							+ "		) where rownum <= :end "
							+ ") where rnum >= :start", 
						params, 
					new MessageRowMapper()
				);	
		return messages;
	}

	@Override
	public int counts() {
		// TODO Auto-generated method stub
		return template.queryForObject("select count(*) from guestmessage", Collections.<String, Object>emptyMap() ,Integer.class);
	}

	@Override
	public int insert(final Message message) {
		// TODO Auto-generated method stub
		SqlParameterSource paramSouece = new BeanPropertySqlParameterSource(message);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		int num = template.update("insert into guestmessage(id,name, message, creationTime) values(id_seq.NEXTVAL, :name, :message, :creationTime)", paramSouece, keyHolder);
		return num;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", id);
		return template.update("delete from guestmessage where id= :id", paramSource);
	}

}
