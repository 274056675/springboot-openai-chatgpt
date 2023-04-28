
package org.springblade.gateway.provider;

import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.LinkedHashSet;

/**
 * RequestProvider
 *
 *
 */
public class RequestProvider {

	/**
	 * 获取原始url
	 *
	 * @param exchange
	 * @return
	 */
	public static String getOriginalRequestUrl(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		LinkedHashSet<URI> uris = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
		URI requestUri = uris.stream().findFirst().orElse(request.getURI());
		MultiValueMap<String, String> queryParams = request.getQueryParams();
		return UriComponentsBuilder.fromPath(requestUri.getRawPath()).queryParams(queryParams).build().toUriString();
	}

}
