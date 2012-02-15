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
import org.springframework.social.weibo.api.UserOperations;
import org.springframework.social.weibo.api.WeiboProfile;
import org.springframework.web.client.RestTemplate;

class UserTemplate extends AbstractWeiboOperations implements UserOperations {

	protected UserTemplate(ObjectMapper objectMapper,
			RestTemplate restTemplate, boolean isAuthorized) {
		super(objectMapper, restTemplate, isAuthorized);
	}

	@Override
	public WeiboProfile getUserProfileById(long uid) {
		requireAuthorization();
		return restTemplate.getForObject(
				buildUri("users/show.json", "uid", uid), WeiboProfile.class);
	}

	@Override
	public WeiboProfile getUserProfileByScreenName(String screenName) {
		requireAuthorization();
		return restTemplate.getForObject(
				buildUri("users/show.json", "screen_name", screenName),
				WeiboProfile.class);
	}

}
