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
package org.springframework.social.weibo.matcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.springframework.social.weibo.api.CursoredList;
import org.springframework.social.weibo.api.Status;
import org.springframework.social.weibo.api.WeiboProfile;

public abstract class StatusMatcher {

	public static void verifyStatus(Status status) {
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

	public static void verifyStatusList(
			CursoredList<Status> statuses) {
		assertEquals(2, statuses.size());
		assertEquals(1L, statuses.getNextCursor());
		assertEquals(0, statuses.getPreviousCursor());
		assertEquals(81655, statuses.getTotalNumber());
	}

	private StatusMatcher() {
	}

}
