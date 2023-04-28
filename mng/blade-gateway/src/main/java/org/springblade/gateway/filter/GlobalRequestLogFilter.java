
package org.springblade.gateway.filter;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.gateway.provider.AuthProvider;
import org.springblade.gateway.provider.RequestProvider;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * webflux 日志请求记录，方便开发调试。请求日志过滤器排序尽量低。
 *
 * <p>
 * 注意：暂时不支持结构体打印，想实现，请看下面的链接。
 * https://stackoverflow.com/questions/45240005/how-to-log-request-and-response-bodies-in-spring-webflux
 * https://github.com/Silvmike/webflux-demo/blob/master/tests/src/test/java/ru/hardcoders/demo/webflux/web_handler/filters/logging
 * </p>
 *
 * @author dream.lu
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "blade.log.request.enabled", havingValue = "true", matchIfMissing = true)
public class GlobalRequestLogFilter implements GlobalFilter, Ordered {
	private final WebEndpointProperties endpointProperties;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		// 打印请求路径
		String path = request.getPath().pathWithinApplication().value();

		// 忽略 endpoint 请求
		String endpointBasePath = endpointProperties.getBasePath();
		if (StringUtils.isNotBlank(endpointBasePath) && path.startsWith(endpointBasePath)) {
			return chain.filter(exchange);
		}

		String requestUrl = RequestProvider.getOriginalRequestUrl(exchange);

		// 构建成一条长 日志，避免并发下日志错乱
		StringBuilder beforeReqLog = new StringBuilder(300);
		// 日志参数
		List<Object> beforeReqArgs = new ArrayList<>();
		beforeReqLog.append("\n\n================ Gateway Request Start  ================\n");
		// 打印路由
		beforeReqLog.append("===> {}: {}\n");
		// 参数
		String requestMethod = request.getMethodValue();
		beforeReqArgs.add(requestMethod);
		beforeReqArgs.add(requestUrl);

		// 打印请求头
		HttpHeaders headers = request.getHeaders();
		headers.forEach((headerName, headerValue) -> {
			beforeReqLog.append("===Headers===  {}: {}\n");
			beforeReqArgs.add(headerName);
			if (AuthProvider.AUTH_KEY.toLowerCase().equals(headerName)) {
				String value = headerValue.get(0);
				String token = JwtUtil.getToken(value);
				Claims claims = JwtUtil.parseJWT(token);
				beforeReqArgs.add((claims == null) ? "" : claims.toString());
				beforeReqLog.append("===Headers===  {}: {}\n");
				beforeReqArgs.add(headerName.concat("-original"));
				beforeReqArgs.add(StringUtils.join(headerValue.toArray()));
			} else {
				beforeReqArgs.add(StringUtils.join(headerValue.toArray()));
			}
		});

		beforeReqLog.append("================  Gateway Request End  =================\n");
		// 打印执行时间
		log.info(beforeReqLog.toString(), beforeReqArgs.toArray());
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
