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
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import org.junit.Test;
import org.springframework.social.weibo.api.Status;
import org.springframework.social.weibo.api.WeiboProfile;

public class UserTemplateTest extends AbstractWeiboOperationsTest {

	private UserTemplate userTemplate;

	@Test
	public void testGetUserProfileById() {
		String uid = "uid";
		mockServer
				.expect(requestTo("https://api.weibo.com/2/users/show.json?uid="
						+ uid))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("profile"), responseHeaders));

		WeiboProfile profile = userTemplate.getUserProfileById(uid);
		verifyWeiboProfile(profile);
	}

	private void verifyWeiboProfile(WeiboProfile profile) {
		assertEquals(2429576463L, profile.getId().longValue());
		assertEquals("Cirrus_Test1", profile.getScreenName());
		assertEquals("http://tp4.sinaimg.cn/2429576463/180/0/1",
				profile.getAvatarLarge());
		assertEquals(1, profile.getBiFollowersCount());
		assertEquals(1000, profile.getCity());
		assertEquals(1319644800000L, profile.getCreatedAt().getTime());
		assertEquals("description", profile.getDescription());
		assertEquals(0, profile.getFavouritesCount());
		assertEquals(1, profile.getFollowersCount());
		assertEquals(28, profile.getFriendsCount());
		assertEquals("m", profile.getGender());
		assertEquals("北京三元桥", profile.getLocation());
		assertEquals("Cirrus_Test1", profile.getName());
		assertEquals(0, profile.getOnlineStatus());
		assertEquals("http://tp4.sinaimg.cn/2429576463/50/0/1",
				profile.getProfileImageUrl());
		assertEquals(11, profile.getProvince());
		assertEquals(1, profile.getStatusesCount());
		assertEquals("http://myFavouriteUrl.com", profile.getUrl());
		assertEquals("domain", profile.getDomain());
		assertEquals("verified reason", profile.getVerifiedReason());

		Status status = profile.getStatus();
		assertEquals(1319681909000L, status.getCreatedAt().getTime());
		assertEquals(3373052761021575L, status.getId().longValue());
		assertEquals("3373052761021575", status.getMid());
		assertEquals("Youpie", status.getText());
		assertTrue(status.isFavorited());
		assertTrue(status.isTruncated());
	}

	@Test
	public void testGetUserProfileByScreenName() {
		String screenName = "Cirrus_Test1";
		mockServer
				.expect(requestTo("https://api.weibo.com/2/users/show.json?screen_name="
						+ screenName))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("profile"), responseHeaders));

		WeiboProfile profile = userTemplate
				.getUserProfileByScreenName(screenName);
		verifyWeiboProfile(profile);
	}

	@Override
	public void setUp() {
		userTemplate = new UserTemplate(getObjectMapper(), getRestTemplate(),
				true);
	}

}
