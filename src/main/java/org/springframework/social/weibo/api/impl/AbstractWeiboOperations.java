/*
 * Copyright 2011 France Telecom R&D Beijing Co., Ltd 北京法国电信研发中心有限公司
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.weibo.api.impl;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.support.URIBuilder;
import org.springframework.social.weibo.api.CursoredList;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

abstract class AbstractWeiboOperations {

	private static final String API_URL_BASE = "https://api.weibo.com/2/";
	// private static final String API_URL_BASE = "http://localhost:9999/2/";
	private static final LinkedMultiValueMap<String, String> EMPTY_PARAMETERS = new LinkedMultiValueMap<String, String>();

	private final boolean isAuthorized;
	protected final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	protected AbstractWeiboOperations(ObjectMapper objectMapper,
			RestTemplate restTemplate, boolean isAuthorized) {
		this.objectMapper = objectMapper;
		this.isAuthorized = isAuthorized;
		this.restTemplate = restTemplate;
	}

	protected void requireAuthorization() {
		if (!isAuthorized) {
			throw new MissingAuthorizationException();
		}
	}

	protected URI buildUri(String path) {
		return buildUri(path, EMPTY_PARAMETERS);
	}

	protected URI buildUri(String path, String parameterName,
			Object parameterValue) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set(parameterName, parameterValue.toString());
		return buildUri(path, parameters);
	}

	protected URI buildUri(String path, MultiValueMap<String, String> parameters) {
		return URIBuilder.fromUri(API_URL_BASE + path).queryParams(parameters)
				.build();
	}

	protected URIBuilder uriBuilder(String path) {
		return URIBuilder.fromUri(API_URL_BASE + path);
	}

	protected <T> CursoredList<T> deserializeCursoredList(JsonNode jsonNode,
			final Class<T> elementType, String dataFieldName) {
		CursoredList<T> result = new CursoredList<T>();
		result.setPreviousCursor(jsonNode.get("previous_cursor").getLongValue());
		result.setNextCursor(jsonNode.get("next_cursor").getLongValue());
		result.setTotalNumber(jsonNode.get("total_number").getIntValue());
		result.addAll(deserializeDataList(jsonNode.get(dataFieldName),
				elementType));
		return result;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> deserializeDataList(JsonNode jsonNode,
			final Class<T> elementType) {
		try {
			CollectionType listType = TypeFactory.defaultInstance()
					.constructCollectionType(List.class, elementType);
			return (List<T>) objectMapper.readValue(jsonNode, listType);
		} catch (IOException e) {
			throw new UncategorizedApiException(
					"Error deserializing data from Weibo: " + e.getMessage(), e);
		}
	}

}
