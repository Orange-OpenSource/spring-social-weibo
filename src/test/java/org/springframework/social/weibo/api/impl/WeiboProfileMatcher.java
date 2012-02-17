package org.springframework.social.weibo.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.springframework.social.weibo.api.Status;
import org.springframework.social.weibo.api.WeiboProfile;

public abstract class WeiboProfileMatcher {

	public static void verifyWeiboProfile(WeiboProfile profile) {
		assertEquals(123L, profile.getId().longValue());
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

}
