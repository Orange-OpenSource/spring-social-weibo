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
package org.springframework.social.weibo.api.impl.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.social.weibo.api.ApiRateLimit;
import org.springframework.social.weibo.api.Comment;
import org.springframework.social.weibo.api.Favorite;
import org.springframework.social.weibo.api.Favorite.Tag;
import org.springframework.social.weibo.api.FollowedTrend;
import org.springframework.social.weibo.api.RateLimitStatus;
import org.springframework.social.weibo.api.Status;
import org.springframework.social.weibo.api.UserTrend;
import org.springframework.social.weibo.api.WeiboProfile;
import org.springframework.social.weibo.api.impl.json.FavoriteMixin.TagMixin;

public class WeiboModule extends SimpleModule {

	public WeiboModule() {
		super("WeiboModule", new Version(1, 0, 0, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(WeiboProfile.class, WeiboProfileMixin.class);
		context.setMixInAnnotations(Status.class, StatusMixin.class);
		context.setMixInAnnotations(Comment.class, CommentMixin.class);
		context.setMixInAnnotations(ApiRateLimit.class, ApiRateLimitMixin.class);
		context.setMixInAnnotations(RateLimitStatus.class,
				RateLimitStatusMixin.class);
		context.setMixInAnnotations(Favorite.class, FavoriteMixin.class);
		context.setMixInAnnotations(Tag.class, TagMixin.class);
		context.setMixInAnnotations(UserTrend.class, UserTrendMixin.class);
		context.setMixInAnnotations(FollowedTrend.class,
				FollowedTrendMixin.class);
	}

}
