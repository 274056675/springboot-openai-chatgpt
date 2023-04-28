
package org.springblade.gateway.dynamic;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 动态路由业务类
 *
 *
 */
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

	private final RouteDefinitionWriter routeDefinitionWriter;

	private ApplicationEventPublisher publisher;

	public DynamicRouteService(RouteDefinitionWriter routeDefinitionWriter) {
		this.routeDefinitionWriter = routeDefinitionWriter;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	/**
	 * 增加路由
	 */
	public String save(RouteDefinition definition) {
		try {
			routeDefinitionWriter.save(Mono.just(definition)).subscribe();
			this.publisher.publishEvent(new RefreshRoutesEvent(this));
			return "save success";
		} catch (Exception e) {
			e.printStackTrace();
			return "save failure";
		}
	}

	/**
	 * 更新路由
	 */
	public String update(RouteDefinition definition) {
		try {
			this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
			this.routeDefinitionWriter.save(Mono.just(definition)).subscribe();
			this.publisher.publishEvent(new RefreshRoutesEvent(this));
			return "update success";
		} catch (Exception e) {
			e.printStackTrace();
			return "update failure";
		}
	}

	/**
	 * 更新路由
	 */
	public String updateList(List<RouteDefinition> routeDefinitions) {
		routeDefinitions.forEach(this::update);
		return "update done";
	}

	/**
	 * 删除路由
	 */
	public String delete(String id) {
		try {
			this.routeDefinitionWriter.delete(Mono.just(id));
			return "delete success";
		} catch (Exception e) {
			e.printStackTrace();
			return "delete failure";
		}
	}


}
