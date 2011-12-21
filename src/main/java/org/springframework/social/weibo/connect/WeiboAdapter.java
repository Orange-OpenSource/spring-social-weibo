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

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.weibo.api.Weibo;
import org.springframework.social.weibo.api.WeiboProfile;

public class WeiboAdapter implements ApiAdapter<Weibo> {

	@Override
	public boolean test(Weibo api) {
		return true;
	}

	@Override
	public void setConnectionValues(Weibo api, ConnectionValues values) {
		WeiboProfile weiboProfile = fetchWeiboProfile(api);
		values.setProviderUserId(String.valueOf(weiboProfile.getId()));
		values.setDisplayName(weiboProfile.getName());
		values.setProfileUrl("http://weibo.com/u/" + weiboProfile.getId());
		values.setImageUrl(weiboProfile.getProfileImageUrl());
	}

	@Override
	public UserProfile fetchUserProfile(Weibo api) {
		WeiboProfile weiboProfile = fetchWeiboProfile(api);
		String name = weiboProfile.getName();
		return new UserProfileBuilder()
				.setUsername(weiboProfile.getScreenName()).setName(name)
				.setLastName(extractChineseLastName(name))
				.setFirstName(extractChineseFirstname(name)).build();

	}

	private String extractChineseFirstname(String name) {
		String result = null;
		if (name != null && !name.trim().isEmpty()) {
			result = name.substring(1);
		}
		return result;
	}

	private String extractChineseLastName(String name) {
		String result = null;
		if (name != null && !name.trim().isEmpty()) {
			result = name.substring(0, 1);
		}
		return result;
	}

	private WeiboProfile fetchWeiboProfile(Weibo api) {
		String uid = api.accountOperations().getUid();
		WeiboProfile weiboProfile = api.userOperations()
				.getUserProfileById(uid);
		return weiboProfile;
	}

	@Override
	public void updateStatus(Weibo api, String message) {
		api.timelineOperations().updateStatus(message);
	}

}
