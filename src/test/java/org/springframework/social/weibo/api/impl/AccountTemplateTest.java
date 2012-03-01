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

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import java.util.List;

import org.junit.Test;
import org.springframework.social.weibo.api.ApiRateLimit;
import org.springframework.social.weibo.api.LimitTimeUnit;
import org.springframework.social.weibo.api.RateLimitStatus;

public class AccountTemplateTest extends AbstractWeiboOperationsTest {

	private AccountTemplate accountTemplate;

	@Test
	public void testGetUid() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/account/get_uid.json"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("account"), responseHeaders));
		assertEquals(123, accountTemplate.getUid());
	}

	@Override
	public void setUp() {
		accountTemplate = new AccountTemplate(getObjectMapper(),
				getRestTemplate(), true);
	}

	@Test
	public void testGetRateLimitStatus() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/account/rate_limit_status.json"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("rateLimitStatus"),
								responseHeaders));
		RateLimitStatus rateLimitStatus = accountTemplate.getRateLimitStatus();

		List<ApiRateLimit> apiRateLimits = rateLimitStatus.getApiRateLimits();
		assertEquals(5, apiRateLimits.size());
		ApiRateLimit updateStatusRateLimit = apiRateLimits.iterator().next();
		assertEquals("/statuses/update", updateStatusRateLimit.getApi());
		assertEquals(30, updateStatusRateLimit.getLimit());
		assertEquals(LimitTimeUnit.HOURS,
				updateStatusRateLimit.getLimitTimeUnit());
		assertEquals(10, updateStatusRateLimit.getRemainingHits());

		assertEquals(1000, rateLimitStatus.getIpLimit());
		assertEquals(LimitTimeUnit.HOURS, rateLimitStatus.getLimitTimeUnit());
		assertEquals(999, rateLimitStatus.getRemainingIpHits());
		assertEquals(149, rateLimitStatus.getRemainingUserHits());
		assertEquals(1330509600000L, rateLimitStatus.getResetTime().getTime());
		assertEquals(2295, rateLimitStatus.getResetTimeInSeconds());
		assertEquals(150, rateLimitStatus.getUserLimit());
	}
}
