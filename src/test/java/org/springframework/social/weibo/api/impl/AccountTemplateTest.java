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

import org.junit.Test;

public class AccountTemplateTest extends AbstractWeiboOperationsTest {

	private AccountTemplate accountTemplate;

	@Test
	public void testGetUid() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/account/get_uid.json"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("account"), responseHeaders));
		assertEquals("3456676543", accountTemplate.getUid());
	}

	@Override
	public void setUp() {
		accountTemplate = new AccountTemplate(getObjectMapper(),
				getRestTemplate(), true);
	}

}
