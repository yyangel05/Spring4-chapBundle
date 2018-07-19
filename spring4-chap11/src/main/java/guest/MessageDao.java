package guest;

import java.util.List;

public interface MessageDao {
	
	List<Message> select(int start, int end);
	
	public int counts();
	
	public int insert(Message message);
	
	public int delete(int id);

}
