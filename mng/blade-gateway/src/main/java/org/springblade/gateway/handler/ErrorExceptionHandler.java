
package org.springblade.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springblade.gateway.provider.ResponseProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 异常处理
 *
 *
 */
@Order(-1)
@RequiredArgsConstructor
public class ErrorExceptionHandler implements ErrorWebExceptionHandler {

	private final ObjectMapper objectMapper;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		if (response.isCommitted()) {
			return Mono.error(ex);
		}

		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		if (ex instanceof ResponseStatusException) {
			response.setStatusCode(((ResponseStatusException) ex).getStatus());
		}

		return response.writeWith(Mono.fromSupplier(() -> {
			DataBufferFactory bufferFactory = response.bufferFactory();
			try {
				int status = 500;
				if (response.getStatusCode() != null) {
					status = response.getStatusCode().value();
				}
				Map<String, Object> result = ResponseProvider.response(status, this.buildMessage(request, ex));
				return bufferFactory.wrap(objectMapper.writeValueAsBytes(result));
			} catch (JsonProcessingException e) {
				return bufferFactory.wrap(new byte[0]);
			}
		}));
	}


	/**
	 * 构建异常信息
	 */
	private String buildMessage(ServerHttpRequest request, Throwable ex) {
		String uri = request.getURI().toString();
		if (uri.endsWith("doc.html")) {
			return "[Swagger聚合网关] 已迁移至 [blade-swagger] 服务，请开启 [blade-swagger] 服务并访问 [http://127.0.0.1:18000/doc.html]";
		}
		StringBuilder message = new StringBuilder("Failed to handle request [");
		message.append(request.getMethodValue());
		message.append(" ");
		message.append(request.getURI());
		message.append("]");
		if (ex != null) {
			message.append(": ");
			message.append(ex.getMessage());
		}
		return message.toString();
	}

}
