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
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.header;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import java.util.List;
import java.util.SortedSet;

import org.junit.Test;
import org.springframework.social.weibo.api.FollowedTrend;
import org.springframework.social.weibo.api.Trends;
import org.springframework.social.weibo.api.Trends.Trend;
import org.springframework.social.weibo.api.TrendsWrapper;
import org.springframework.social.weibo.api.UserTrend;

public class TrendTemplateTest extends AbstractWeiboOperationsTest {

	private TrendTemplate trendTemplate;

	@Test
	public void testGetTrendsPagination() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/trends.json?uid=1&count=20&page=2"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("userTrends"),
								responseHeaders));
		List<UserTrend> trends = trendTemplate.getTrends(1, 20, 2);
		assertEquals(2, trends.size());
		UserTrend firstTrend = trends.iterator().next();
		verifyUserTrend(firstTrend);
	}

	@Test
	public void testGetTrends() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/trends.json?uid=1"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("userTrends"),
								responseHeaders));
		List<UserTrend> trends = trendTemplate.getTrends(1);
		assertEquals(2, trends.size());
		UserTrend firstTrend = trends.iterator().next();
		verifyUserTrend(firstTrend);
	}

	private void verifyUserTrend(UserTrend userTrend) {
		assertEquals(1567898, userTrend.getId());
		assertEquals("苹果", userTrend.getHotword());
		assertEquals("225673", userTrend.getNum());
	}

	@Override
	public void setUp() {
		trendTemplate = new TrendTemplate(getObjectMapper(), getRestTemplate(),
				true);
	}

	@Test
	public void testIsFollowed() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/trends/is_follow.json?trend_name=%E8%8B%B9%E6%9E%9C"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(
								"{\"trend_id\":123456,\"is_follow\":true}",
								responseHeaders));
		FollowedTrend followedTrend = trendTemplate.isFollowed("苹果");
		assertEquals(123456, followedTrend.getTrendId());
		assertTrue(followedTrend.isFollowed());
	}

	@Test
	public void testGetHourlyTrends() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/trends/hourly.json"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("trends"), responseHeaders));
		TrendsWrapper hourlyTrends = trendTemplate.getHourlyTrends();
		assertEquals(1280833537000L, hourlyTrends.getAsOf().getTime());
		SortedSet<Trends> trendsSet = hourlyTrends.getTrends();
		assertEquals(2, trendsSet.size());
		Trends trends = trendsSet.iterator().next();
		assertEquals(1306809960000L, trends.getDate().getTime());
		Trend firstTrend = trends.getTrends().iterator().next();
		assertEquals(123, firstTrend.getAmount());
		assertEquals(0, firstTrend.getDelta());
		assertEquals("苹果", firstTrend.getName());
		assertEquals("苹果", firstTrend.getQuery());
	}

	@Test
	public void testGetHourlyTrendsFilteredByApplication() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/trends/hourly.json?base_app=1"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("trends"), responseHeaders));
		TrendsWrapper hourlyTrends = trendTemplate.getHourlyTrends(true);
		assertEquals(1280833537000L, hourlyTrends.getAsOf().getTime());
		SortedSet<Trends> trendsSet = hourlyTrends.getTrends();
		assertEquals(2, trendsSet.size());
		Trends trends = trendsSet.iterator().next();
		assertEquals(1306809960000L, trends.getDate().getTime());
		Trend firstTrend = trends.getTrends().iterator().next();
		assertEquals(123, firstTrend.getAmount());
		assertEquals(0, firstTrend.getDelta());
		assertEquals("苹果", firstTrend.getName());
		assertEquals("苹果", firstTrend.getQuery());

	}

	@Test
	public void testGetDailyTrends() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/trends/daily.json"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("trends"), responseHeaders));
		TrendsWrapper dailyTrends = trendTemplate.getDailyTrends();
		assertEquals(1280833537000L, dailyTrends.getAsOf().getTime());
		SortedSet<Trends> trendsSet = dailyTrends.getTrends();
		assertEquals(2, trendsSet.size());
		Trends trends = trendsSet.iterator().next();
		assertEquals(1306809960000L, trends.getDate().getTime());
		Trend firstTrend = trends.getTrends().iterator().next();
		assertEquals(123, firstTrend.getAmount());
		assertEquals(0, firstTrend.getDelta());
		assertEquals("苹果", firstTrend.getName());
		assertEquals("苹果", firstTrend.getQuery());
	}

	@Test
	public void testGetDailyTrendsFilteredByApplication() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/trends/daily.json?base_app=1"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("trends"), responseHeaders));
		TrendsWrapper dailyTrends = trendTemplate.getDailyTrends(true);
		assertEquals(1280833537000L, dailyTrends.getAsOf().getTime());
		SortedSet<Trends> trendsSet = dailyTrends.getTrends();
		assertEquals(2, trendsSet.size());
		Trends trends = trendsSet.iterator().next();
		assertEquals(1306809960000L, trends.getDate().getTime());
		Trend firstTrend = trends.getTrends().iterator().next();
		assertEquals(123, firstTrend.getAmount());
		assertEquals(0, firstTrend.getDelta());
		assertEquals("苹果", firstTrend.getName());
		assertEquals("苹果", firstTrend.getQuery());
	}

	@Test
	public void testGetWeeklyTrends() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/trends/weekly.json"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("trends"), responseHeaders));
		TrendsWrapper weeklyTrends = trendTemplate.getWeeklyTrends();
		assertEquals(1280833537000L, weeklyTrends.getAsOf().getTime());
		SortedSet<Trends> trendsSet = weeklyTrends.getTrends();
		assertEquals(2, trendsSet.size());
		Trends trends = trendsSet.iterator().next();
		assertEquals(1306809960000L, trends.getDate().getTime());
		Trend firstTrend = trends.getTrends().iterator().next();
		assertEquals(123, firstTrend.getAmount());
		assertEquals(0, firstTrend.getDelta());
		assertEquals("苹果", firstTrend.getName());
		assertEquals("苹果", firstTrend.getQuery());
	}

	@Test
	public void testGetWeeklyTrendsFilteredByApplication() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/trends/weekly.json?base_app=1"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("trends"), responseHeaders));
		TrendsWrapper weeklyTrends = trendTemplate.getWeeklyTrends(true);
		assertEquals(1280833537000L, weeklyTrends.getAsOf().getTime());
		SortedSet<Trends> trendsSet = weeklyTrends.getTrends();
		assertEquals(2, trendsSet.size());
		Trends trends = trendsSet.iterator().next();
		assertEquals(1306809960000L, trends.getDate().getTime());
		Trend firstTrend = trends.getTrends().iterator().next();
		assertEquals(123, firstTrend.getAmount());
		assertEquals(0, firstTrend.getDelta());
		assertEquals("苹果", firstTrend.getName());
		assertEquals("苹果", firstTrend.getQuery());
	}
}
