package org.springblade.mng.event;

import org.springframework.context.ApplicationEvent;

public class FileLogEvent extends ApplicationEvent {
	public FileLogEvent(Object source) {
		super(source);
	}
}
