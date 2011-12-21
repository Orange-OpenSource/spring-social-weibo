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

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.weibo.api.CursoredList;
import org.springframework.social.weibo.api.Status;
import org.springframework.social.weibo.api.TimelineOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class TimelineTemplate extends AbstractWeiboOperations implements
		TimelineOperations {

	protected TimelineTemplate(ObjectMapper objectMapper,
			RestTemplate restTemplate, boolean isAuthorized) {
		super(objectMapper, restTemplate, isAuthorized);
	}

	@Override
	public Status updateStatus(String message) {
		requireAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>(
				1);
		request.add("status", message);
		return restTemplate.postForObject(buildUri("statuses/update.json"),
				request, Status.class);
	}

	@Override
	public CursoredList<Status> getPublicTimeline() {
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("statuses/public_timeline.json"), JsonNode.class);
		return deserializeCursoredList(dataNode, Status.class, "statuses");
	}

	@Override
	public CursoredList<Status> getHomeTimeline() {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("statuses/home_timeline.json"), JsonNode.class);
		return deserializeCursoredList(dataNode, Status.class, "statuses");
	}

	@Override
	public CursoredList<Status> getUserTimeline() {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("statuses/user_timeline.json"), JsonNode.class);
		return deserializeCursoredList(dataNode, Status.class, "statuses");
	}

	@Override
	public CursoredList<Status> getFriendsTimeline() {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("statuses/friends_timeline.json"), JsonNode.class);
		return deserializeCursoredList(dataNode, Status.class, "statuses");
	}

	@Override
	public Status updateStatus(String message, Float latitude, Float longitude) {
		requireAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>(
				1);
		request.add("status", message);
		request.add("lat", latitude.toString());
		request.add("long", longitude.toString());
		return restTemplate.postForObject(buildUri("statuses/update.json"),
				request, Status.class);
	}

}
