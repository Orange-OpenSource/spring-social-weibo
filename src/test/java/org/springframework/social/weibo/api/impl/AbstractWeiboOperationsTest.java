/*
* Copyright 2011 France Telecom R&D Beijing Co., Ltd
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
import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.test.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractWeiboOperationsTest {

	protected MockRestServiceServer mockServer;
	private WeiboTemplate weiboTemplate;
	protected HttpHeaders responseHeaders;

	public AbstractWeiboOperationsTest() {
		weiboTemplate = new WeiboTemplate("accessToken");
		mockServer = MockRestServiceServer.createServer(weiboTemplate
				.getRestTemplate());
		responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
	}

	@Before
	public abstract void setUp();

	protected RestTemplate getRestTemplate() {
		return weiboTemplate.getRestTemplate();
	}

	protected ObjectMapper getObjectMapper() {
		return weiboTemplate.getObjectMapper();
	}

	protected Resource jsonResource(String file) {
		ClassPathResource classPathResource = new ClassPathResource("json/"
				+ file + ".json");
		return classPathResource;
	}

}