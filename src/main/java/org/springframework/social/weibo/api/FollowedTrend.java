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
package org.springframework.social.weibo.api;

public class FollowedTrend {

	private long trendId;
	private boolean followed;

	/**
	 * @return the trendId
	 */
	public long getTrendId() {
		return trendId;
	}

	/**
	 * @param trendId
	 *            the trendId to set
	 */
	public void setTrendId(long trendId) {
		this.trendId = trendId;
	}

	/**
	 * @return the followed
	 */
	public boolean isFollowed() {
		return followed;
	}

	/**
	 * @param followed
	 *            the followed to set
	 */
	public void setFollowed(boolean followed) {
		this.followed = followed;
	}

}
