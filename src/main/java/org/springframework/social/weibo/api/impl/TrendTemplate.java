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

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.weibo.api.FollowedTrend;
import org.springframework.social.weibo.api.TrendOperations;
import org.springframework.social.weibo.api.TrendsWrapper;
import org.springframework.social.weibo.api.UserTrend;
import org.springframework.social.weibo.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class TrendTemplate extends AbstractWeiboOperations implements
		TrendOperations {

	protected TrendTemplate(ObjectMapper objectMapper,
			RestTemplate restTemplate, boolean isAuthorized) {
		super(objectMapper, restTemplate, isAuthorized);
	}

	@Override
	public List<UserTrend> getTrends(long userId) {
		requireAuthorization();
		JsonNode jsonNode = restTemplate.getForObject(
				buildUri("trends.json", "uid", String.valueOf(userId)),
				JsonNode.class);
		return deserializeDataList(jsonNode, UserTrend.class);
	}

	@Override
	public List<UserTrend> getTrends(long userId, int pageSize, int pageNumber) {
		requireAuthorization();
		JsonNode jsonNode = restTemplate
				.getForObject(
						uriBuilder("trends.json")
								.queryParam("uid", String.valueOf(userId))
								.queryParam("count", String.valueOf(pageSize))
								.queryParam("page", String.valueOf(pageNumber))
								.build(), JsonNode.class);
		return deserializeDataList(jsonNode, UserTrend.class);
	}

	@Override
	public FollowedTrend isFollowed(String trendName) {
		return restTemplate.getForObject(
				buildUri("trends/is_follow.json", "trend_name", trendName),
				FollowedTrend.class);
	}

	@Override
	public TrendsWrapper getHourlyTrends() {
		return restTemplate.getForObject(buildUri("trends/hourly.json"),
				TrendsWrapper.class);
	}

	@Override
	public TrendsWrapper getHourlyTrends(boolean onlyApplicationData) {
		return restTemplate.getForObject(
				buildUri("trends/hourly.json", "base_app",
						StringUtils.booleanToString(onlyApplicationData)),
				TrendsWrapper.class);
	}

	@Override
	public TrendsWrapper getDailyTrends() {
		return restTemplate.getForObject(buildUri("trends/daily.json"),
				TrendsWrapper.class);
	}

	@Override
	public TrendsWrapper getDailyTrends(boolean onlyApplicationData) {
		return restTemplate.getForObject(
				buildUri("trends/daily.json", "base_app",
						StringUtils.booleanToString(onlyApplicationData)),
				TrendsWrapper.class);
	}

	@Override
	public TrendsWrapper getWeeklyTrends() {
		return restTemplate.getForObject(buildUri("trends/weekly.json"),
				TrendsWrapper.class);
	}

	@Override
	public TrendsWrapper getWeeklyTrends(boolean onlyApplicationData) {
		return restTemplate.getForObject(
				buildUri("trends/weekly.json", "base_app",
						StringUtils.booleanToString(onlyApplicationData)),
				TrendsWrapper.class);
	}
}
