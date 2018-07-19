package guest;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		//useJdbc();
		//useJdbcTemplate();
		//useJdbcTemplate2();
		useNamedJdbcTemplate();
	}

	private static void useJdbc() {
		// TODO Auto-generated method stub
		String configLocation = "classpath:applicationContext.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation);
		
		MessageDao messageDao = ctx.getBean("jdbcMessageDao", MessageDao.class);
		runMessageDao(messageDao);
		ctx.close();
	}
	
	private static void useJdbcTemplate() {
		// TODO Auto-generated method stub
		String configLocation = "classpath:applicationContext.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation);
		
		MessageDao messageDao = ctx.getBean("jdbcTemplateMessageDao", MessageDao.class);
		runMessageDao(messageDao);
		ctx.close();
		
	}
	
	private static void useJdbcTemplate2() {
		// TODO Auto-generated method stub
		String configLocation = "classpath:applicationContext.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation);
		
		MessageDao messageDao = ctx.getBean("jdbcTemplateMessageDao2", MessageDao.class);
		runMessageDao(messageDao);
		ctx.close();
	}

	private static void useNamedJdbcTemplate() {
		// TODO Auto-generated method stub
		String configLocation = "classpath:applicationContext.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation);
		
		MessageDao messageDao = ctx.getBean("namedTemplateDao", MessageDao.class);
		runMessageDao(messageDao);
		ctx.close();
	}
	

	private static void runMessageDao(MessageDao messageDao) {
		// TODO Auto-generated method stub
		Message message = new Message();
		message.setMessage("메세지-갯뻘의해산물");
		message.setCreationTime(new Date());
		message.setName("낙지");
		int id = messageDao.insert(message);
		System.out.printf("Message[%d]가 추가되었습니다. \n", id);
		
		int count = messageDao.counts();
		System.out.printf("전체 개수 : %d\n", count);
		List<Message> messages = messageDao.select(1, 10);
		System.out.printf("읽어온 메세지 개수 : %d\n", messages.size());
		
	}

}
