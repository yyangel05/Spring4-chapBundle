package com.kh.yytest.event;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class EventService {
	
	private SortedMap<Long, Event> eventMap = new TreeMap<Long, Event>();
	
	public EventService() {
		eventMap.put(1L,  Event.create(1L, "가재구이", EventType.CONFERENCE));
		eventMap.put(2L,  Event.create(2L, "노르스름하게", EventType.CONFERENCE));
		eventMap.put(3L,  Event.create(3L, "두더지플래쉬~!", EventType.FLASHMOB));
		eventMap.put(4L,  Event.create(4L, "라이터플래쉬~!", EventType.FLASHMOB));
		eventMap.put(5L,  Event.create(5L, "마이구미", EventType.CONFERENCE));
		eventMap.put(6L,  Event.create(6L, "조커조커", EventType.CIRCUS));
	}
	
	public Event getEvent(Long eventId) {
		return eventMap.get(eventId);
	}
	
	public List<Event> getRecommendedEventService() {
		List<Event> recommendList = new ArrayList<Event>();
		recommendList.add(eventMap.get(1L));
		return recommendList;
	}
	
	public List<Event> getOpenedEventList(SearchOption option) {
		List<Event> result = new ArrayList<Event>();
		for (Event event : eventMap.values()) {
			if (option.isAllType()) { //전체로 선택되어있다면 
				result.add(event);
			} 
			else { //타입이 선택되어있다면
				for (EventType type : option.getTypes()) { 
					if (type == event.getType()) {
						result.add(event);
						break;
					}
				}
			}
		}
		return result;
	}

}
