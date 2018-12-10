package com.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.AllArgsConstructor;

/**
 * 
 * High Level Event POJO. This will be same for the various type of events.
 * Based on the event type the payload changes to have specific detail of the
 * event emitting object.
 * 
 * @author gimu2
 *
 */

@AllArgsConstructor
@NoArgsConstructor
public class Event implements IEvent {

	@Getter
	@Setter
	@NonNull
	private String eventObject;
	
	@Getter
	@Setter
	@NonNull
	private String eventObjectId;
	
	@Getter
	@Setter
	@NonNull
	private String eventType;
	
	@Getter
	@Setter
	@NonNull
	private String timestamp;
	
	@Getter
	@Setter
	@NonNull
	private Object payload;	//	contains the JSON of the actual object emitting the event
	
	@Getter
	@Setter
	@NonNull
	private String message;

}
