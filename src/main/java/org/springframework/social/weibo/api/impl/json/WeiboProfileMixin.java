/*
* Copyright 2011 France Télécom
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

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.social.weibo.api.Status;

/**
 * Annotated mixin to add Jackson annotations to WeiboProfile.
 * 
 * @author edva8332
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class WeiboProfileMixin {

	WeiboProfileMixin(
			@JsonProperty("id") Long id,
			@JsonProperty("screen_name") String screenName,
			@JsonProperty("name") String name,
			@JsonProperty("url") String url,
			@JsonProperty("profile_image_url") String profileImageUrl,
			@JsonProperty("description") String description,
			@JsonProperty("location") String location,
			@JsonProperty("created_at") @JsonDeserialize(using = DateDeserializer.class) Date createAt) {
	}

	@JsonProperty("province")
	int province; // 省份编码（参考省份编码表）
	@JsonProperty("city")
	int city; // 城市编码（参考城市编码表）
	@JsonProperty("domain")
	String domain; // 用户个性化URL
	@JsonProperty("gender")
	String gender; // 性别,m--男，f--女,n--未知
	@JsonProperty("followers_count")
	int followersCount; // 粉丝数
	@JsonProperty("friends_count")
	int friendsCount; // 关注数
	@JsonProperty("statuses_count")
	int statusesCount; // 微博数
	@JsonProperty("favourites_count")
	int favouritesCount; // 收藏数
	@JsonProperty("following")
	boolean following; // 保留字段,是否已关注(此特性暂不支持)
	@JsonProperty("verified")
	boolean verified; // 加V标示，是否微博认证用户
	@JsonProperty("verified_reason")
	int verifiedReason; // 认证类型
	@JsonProperty("allow_all_act_msg")
	boolean allowAllActMsg; // 是否允许所有人给我发私信
	@JsonProperty("allow_all_comment")
	boolean allowAllComment; // 是否允许所有人对我的微博进行评论
	@JsonProperty("follow_me")
	boolean followMe; // 此用户是否关注我
	@JsonProperty("avatar_large")
	String avatarLarge; // 大头像地址
	@JsonProperty("online_status")
	int onlineStatus; // 用户在线状态
	@JsonProperty("status")
	Status status; // 用户最新一条微博
	@JsonProperty("bi_followers_count")
	int biFollowersCount; // 互粉数

}
