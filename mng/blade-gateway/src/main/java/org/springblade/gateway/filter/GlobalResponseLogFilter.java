
package org.springblade.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * webflux 相应日志，方便开发调试，注意排序要优先。
 *
 * @author dream.lu
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "blade.log.request.enabled", havingValue = "true", matchIfMissing = true)
public class GlobalResponseLogFilter implements GlobalFilter, Ordered {
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
		return chain.filter(exchange).then(
			Mono.fromRunnable(() -> {
				MultiValueMap<String, String> queryParams = request.getQueryParams();
				String requestUrl = UriComponentsBuilder.fromPath(path).queryParams(queryParams).build().toUriString();

				// 构建成一条长 日志，避免并发下日志错乱
				StringBuilder responseLog = new StringBuilder(300);
				// 日志参数
				List<Object> responseArgs = new ArrayList<>();
				responseLog.append("\n\n================ Gateway Response Start  ================\n");
				ServerHttpResponse response = exchange.getResponse();
				// 打印路由 200 get: /api/xxx/xxx
				responseLog.append("<=== {} {}: {}\n");
				// 参数
				String requestMethod = request.getMethodValue();
				responseArgs.add(response.getStatusCode().value());
				responseArgs.add(requestMethod);
				responseArgs.add(requestUrl);

				// 打印请求头
				HttpHeaders headers = response.getHeaders();
				headers.forEach((headerName, headerValue) -> {
					responseLog.append("===Headers===  {}: {}\n");
					responseArgs.add(headerName);
					responseArgs.add(StringUtils.join(headerValue.toArray()));
				});

				responseLog.append("================  Gateway Response End  =================\n");
				// 打印执行时间
				log.info(responseLog.toString(), responseArgs.toArray());
			})
		);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
