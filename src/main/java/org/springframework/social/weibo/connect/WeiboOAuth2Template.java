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
package org.springframework.social.weibo.connect;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class WeiboOAuth2Template extends OAuth2Template {

	private static final Log logger = LogFactory
			.getLog(WeiboOAuth2Template.class.getName());

	public WeiboOAuth2Template(String clientId, String clientSecret) {
		super(clientId, clientSecret,
				"https://api.t.sina.com.cn/oauth2/authorize",
				"https://api.t.sina.com.cn/oauth2/access_token");
	}

	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(
				ClientHttpRequestFactorySelector.getRequestFactory());
		HttpMessageConverter<?> messageConverter = new FormHttpMessageConverter() {

			private final ObjectMapper objectMapper = new ObjectMapper();

			@Override
			public boolean canRead(Class<?> clazz, MediaType mediaType) {
				return true;
			}

			@Override
			public MultiValueMap<String, String> read(
					Class<? extends MultiValueMap<String, ?>> clazz,
					HttpInputMessage inputMessage) throws IOException,
					HttpMessageNotReadableException {

				LinkedHashMap<String, ?> readValue = objectMapper.readValue(
						inputMessage.getBody(), TypeFactory.type(Map.class));
				LinkedMultiValueMap<String, String> result = new LinkedMultiValueMap<String, String>();
				for (Entry<String, ?> currentEntry : readValue.entrySet()) {
					result.add(currentEntry.getKey(), currentEntry.getValue()
							.toString());
				}
				return result;
			}
		};

		// new MappingJacksonHttpMessageConverter() {
		// public boolean canRead(Class<?> clazz, MediaType mediaType) {
		// // always read as x-www-url-formencoded even though Facebook //
		// // sets contentType to text/plain
		// return true;
		// }
		// };
		// List<MediaType> supportedMediaTypes = new ArrayList<MediaType>(2);
		// supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
		// supportedMediaTypes.add(MediaType.MULTIPART_FORM_DATA);
		// messageConverter.setSupportedMediaTypes(supportedMediaTypes);

		restTemplate.setMessageConverters(Collections
				.<HttpMessageConverter<?>> singletonList(messageConverter));
		return restTemplate;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected AccessGrant postForAccessGrant(String accessTokenUrl,
			MultiValueMap<String, String> parameters) {
		MultiValueMap<String, String> response = getRestTemplate()
				.postForObject(accessTokenUrl, parameters, MultiValueMap.class);
		String expires = response.getFirst("expires_in");
		String accessToken = response.getFirst("access_token");
		if (logger.isDebugEnabled()) {
			logger.debug("access token value = " + accessToken);
		}
		return new AccessGrant(accessToken, null, null,
				expires != null ? Integer.valueOf(expires) : null);
	}

}
