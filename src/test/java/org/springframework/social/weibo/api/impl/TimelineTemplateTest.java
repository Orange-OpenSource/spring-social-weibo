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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.social.test.client.RequestMatchers.body;
import static org.springframework.social.test.client.RequestMatchers.header;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.social.weibo.api.CursoredList;
import org.springframework.social.weibo.api.Status;
import org.springframework.social.weibo.api.StatusContentType;
import org.springframework.social.weibo.api.WeiboProfile;

public class TimelineTemplateTest extends AbstractWeiboOperationsTest {

	private TimelineTemplate timelineTemplate;

	private Resource createResource() {
		Resource media = new ByteArrayResource("data".getBytes()) {
			@Override
			public String getFilename() throws IllegalStateException {
				return "photo.jpg";
			}
		};
		return media;
	}

	@Override
	public void setUp() {
		timelineTemplate = new TimelineTemplate(getObjectMapper(),
				getRestTemplate(), true);
	}

	@Test
	public void testDeleteStatus() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/destroy.json"))
				.andExpect(method(POST))
				.andExpect(body("id=1"))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("status"), responseHeaders));
		Status status = timelineTemplate.deleteStatus(1L);
		verifyStatus(status);
	}

	@Test
	public void testGetFriendsTimeline() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/friends_timeline.json"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getFriendsTimeline();
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetFriendsTimelineFilteredByApplication() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/friends_timeline.json?since_id=0&max_id=0&count=10&page=5&base_app=1&feature=0"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getFriendsTimeline(10,
				5, true);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetFriendsTimelineFilteredByFeature() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/friends_timeline.json?since_id=123&max_id=456&count=10&page=5&base_app=0&feature=4"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getFriendsTimeline(
				123L, 456L, 10, 5, false, StatusContentType.MUSIC);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetFriendsTimelinePagination() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/friends_timeline.json?since_id=0&max_id=0&count=10&page=5&base_app=0&feature=0"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getFriendsTimeline(10,
				5);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetHomeTimeline() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/home_timeline.json"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getHomeTimeline();
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetHomeTimelineFilteredByApplication() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/home_timeline.json?since_id=0&max_id=0&count=10&page=5&base_app=1&feature=0"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getHomeTimeline(10, 5,
				true);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetHomeTimelineFilteredByFeature() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/home_timeline.json?since_id=123&max_id=456&count=10&page=5&base_app=0&feature=4"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getHomeTimeline(123L,
				456L, 10, 5, false, StatusContentType.MUSIC);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetHomeTimelinePagination() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/home_timeline.json?since_id=0&max_id=0&count=10&page=5&base_app=0&feature=0"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getHomeTimeline(10, 5);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetPublicTimeline() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/public_timeline.json"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getPublicTimeline();
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetPublicTimelineFilteredByApplication() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/public_timeline.json?count=10&page=5&base_app=1"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getPublicTimeline(10,
				5, true);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetPublicTimelinePagination() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/public_timeline.json?count=10&page=5&base_app=0"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getPublicTimeline(10,
				5);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetUserTimeline() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/user_timeline.json?uid=1"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getUserTimeline(1L);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetUserTimelineFilteredByApplication() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/user_timeline.json?uid=1&since_id=0&max_id=0&count=10&page=5&base_app=1&feature=0"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getUserTimeline(1L,
				10, 5, true);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetUserTimelineFilteredByFeature() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/user_timeline.json?uid=1&since_id=123&max_id=456&count=10&page=5&base_app=0&feature=4"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getUserTimeline(1L,
				123L, 456L, 10, 5, false, StatusContentType.MUSIC);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetUserTimelinePagination() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/user_timeline.json?uid=1&since_id=0&max_id=0&count=10&page=5&base_app=0&feature=0"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getUserTimeline(1L,
				10, 5);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testRepostStatus() {
		String message = "我是法国人";
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/repost.json"))
				.andExpect(method(POST))
				.andExpect(
						body("id=11488058246&status=%E6%88%91%E6%98%AF%E6%B3%95%E5%9B%BD%E4%BA%BA"))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("repost"), responseHeaders));

		Status status = timelineTemplate.repostStatus(11488058246L, message);
		verifyRepost(status);
		assertEquals(message, status.getText());
	}

	@Test
	public void testUpdateStatus() {
		String message = "你好";
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(body("status=%E4%BD%A0%E5%A5%BD"))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("status"), responseHeaders));

		Status status = timelineTemplate.updateStatus(message);
		verifyStatus(status);
		assertEquals(message, status.getText());
	}

	@Test
	public void testUpdateStatusUploadPicture() {
		String message = "你好";
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/upload.json"))
				.andExpect(method(POST))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("status"), responseHeaders));

		Status status = timelineTemplate
				.updateStatus(message, createResource());
		verifyStatus(status);
		assertEquals(message, status.getText());
	}

	@Test
	public void testUpdateStatusUploadPictureWithLocation() {
		String message = "你好";
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/upload.json"))
				.andExpect(method(POST))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("status"), responseHeaders));

		Status status = timelineTemplate.updateStatus(message,
				createResource(), 48.856667f, 2.350833f);
		verifyStatus(status);
		assertEquals(message, status.getText());
	}

	@Test
	public void testUpdateStatusWithLocation() {
		String message = "你好";
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(
						body("status=%E4%BD%A0%E5%A5%BD&lat=48.856667&long=2.350833"))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("status"), responseHeaders));

		Status status = timelineTemplate.updateStatus(message, 48.856667f,
				2.350833f);
		verifyStatus(status);
		assertEquals(message, status.getText());
	}

	private void verifyRepost(Status status) {
		verifyStatus(status);
		Status originalStatus = status.getOriginalStatus();
		assertEquals(1306231493000L, originalStatus.getCreatedAt().getTime());
		assertEquals("你好", originalStatus.getText());
	}

	private void verifyStatus(Status status) {
		assertEquals(1306835215000L, status.getCreatedAt().getTime());
		assertEquals(11488058246L, status.getId().longValue());
		assertEquals("5612814510546515491", status.getMid());
		assertFalse(status.isFavorited());
		assertFalse(status.isTruncated());
		WeiboProfile user = status.getUser();
		assertNotNull(user);
		assertEquals("zaku", user.getScreenName());
		assertEquals(1404376560L, user.getId().longValue());
	}

	private void verifyStatusList(CursoredList<Status> statuses) {
		assertEquals(2, statuses.size());
		assertEquals(1L, statuses.getNextCursor());
		assertEquals(0, statuses.getPreviousCursor());
		assertEquals(81655, statuses.getTotalNumber());
	}

	@Test
	public void testGetRepostTimeline() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/repost_timeline.json?id=1"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("repostTimeline"),
								responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getRepostTimeline(1L);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetRepostTimelinePagination() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/repost_timeline.json?id=1&since_id=0&max_id=0&count=10&page=5&filter_by_author=0"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("repostTimeline"),
								responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getRepostTimeline(1L,
				10, 5);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetRepostByMe() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/repost_by_me.json"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("repostTimeline"),
								responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getRepostByMe();
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

	@Test
	public void testGetRepostByMePagination() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/repost_by_me.json?since_id=0&max_id=0&count=10&page=5"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("repostTimeline"),
								responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getRepostByMe(10, 5);
		verifyStatusList(statuses);
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("你好", firstStatus.getText());
	}

}
