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
import org.springframework.social.weibo.api.CursoredList;
import org.springframework.social.weibo.api.Status;
import org.springframework.social.weibo.api.WeiboProfile;

public class TimelineTemplateTest extends AbstractWeiboOperationsTest {

	private TimelineTemplate timelineTemplate;

	@Test
	public void testUpdateStatus() {
		String message = "a brand new weibo";
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(body("status=a+brand+new+weibo"))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("status"), responseHeaders));

		Status status = timelineTemplate.updateStatus(message);
		verifyStatus(status);
		assertEquals(message, status.getText());
	}

	@Test
	public void testUpdateStatusWithLocation() {
		String message = "a brand new weibo";
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(
						body("status=a+brand+new+weibo&lat=48.856667&long=2.350833"))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("status"), responseHeaders));

		Status status = timelineTemplate.updateStatus(message, 48.856667f,
				2.350833f);
		verifyStatus(status);
		assertEquals(message, status.getText());
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

	@Override
	public void setUp() {
		timelineTemplate = new TimelineTemplate(getObjectMapper(),
				getRestTemplate(), true);
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
		assertEquals(2, statuses.size());
		assertEquals(11488013766L, statuses.getNextCursor());
		assertEquals(0, statuses.getPreviousCursor());
		assertEquals(81655, statuses.getTotalNumber());
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("weibo from Beijing", firstStatus.getText());
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
		assertEquals(2, statuses.size());
		assertEquals(11488013766L, statuses.getNextCursor());
		assertEquals(0, statuses.getPreviousCursor());
		assertEquals(81655, statuses.getTotalNumber());
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("weibo from Beijing", firstStatus.getText());
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
		assertEquals(2, statuses.size());
		assertEquals(11488013766L, statuses.getNextCursor());
		assertEquals(0, statuses.getPreviousCursor());
		assertEquals(81655, statuses.getTotalNumber());
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("weibo from Beijing", firstStatus.getText());
	}

	@Test
	public void testGetUserTimeline() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/statuses/user_timeline.json"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth2 accessToken"))
				.andRespond(
						withResponse(jsonResource("timeline"), responseHeaders));
		CursoredList<Status> statuses = timelineTemplate.getUserTimeline();
		assertEquals(2, statuses.size());
		assertEquals(11488013766L, statuses.getNextCursor());
		assertEquals(0, statuses.getPreviousCursor());
		assertEquals(81655, statuses.getTotalNumber());
		Status firstStatus = statuses.iterator().next();
		verifyStatus(firstStatus);
		assertEquals("weibo from Beijing", firstStatus.getText());
	}

}
