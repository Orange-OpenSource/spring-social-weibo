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

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.OAuth2Version;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.weibo.api.AccountOperations;
import org.springframework.social.weibo.api.FriendOperations;
import org.springframework.social.weibo.api.TimelineOperations;
import org.springframework.social.weibo.api.UserOperations;
import org.springframework.social.weibo.api.Weibo;
import org.springframework.social.weibo.api.impl.json.WeiboModule;
import org.springframework.web.client.RestTemplate;

public class WeiboTemplate extends AbstractOAuth2ApiBinding implements Weibo {

	private AccountOperations accountOperations;

	private UserOperations userOperations;

	private TimelineOperations timelineOperations;

	private FriendOperations friendOperations;

	private ObjectMapper objectMapper;

	public WeiboTemplate(String accessToken) {
		super(accessToken);
		initialize();
	}

	public WeiboTemplate() {
		initialize();
	}

	@Override
	protected void configureRestTemplate(RestTemplate restTemplate) {
		super.configureRestTemplate(restTemplate);
		restTemplate.setErrorHandler(new WeiboErrorHandler());
	}

	@Override
	protected MappingJacksonHttpMessageConverter getJsonMessageConverter() {
		MappingJacksonHttpMessageConverter converter = super
				.getJsonMessageConverter();
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new WeiboModule());
		converter.setObjectMapper(objectMapper);
		return converter;
	}

	private void initialize() {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so
		// that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector
				.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis();
	}

	private void initSubApis() {
		this.userOperations = new UserTemplate(objectMapper, getRestTemplate(),
				isAuthorized());
		this.accountOperations = new AccountTemplate(objectMapper,
				getRestTemplate(), isAuthorized());
		this.timelineOperations = new TimelineTemplate(objectMapper,
				getRestTemplate(), isAuthorized());
		this.friendOperations = new FriendTemplate(objectMapper,
				getRestTemplate(), isAuthorized());
	}

	@Override
	public UserOperations userOperations() {
		return userOperations;
	}

	@Override
	protected OAuth2Version getOAuth2Version() {
		return OAuth2Version.BEARER_DRAFT_2;
	}

	@Override
	public AccountOperations accountOperations() {
		return accountOperations;
	}

	@Override
	public TimelineOperations timelineOperations() {
		return timelineOperations;
	}

	@Override
	public FriendOperations friendOperations() {
		return friendOperations;
	}

	protected ObjectMapper getObjectMapper() {
		return objectMapper;
	}

}
