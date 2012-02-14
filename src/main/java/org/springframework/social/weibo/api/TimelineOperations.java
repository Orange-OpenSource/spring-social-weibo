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

import java.util.List;

import org.springframework.core.io.Resource;

public interface TimelineOperations {

	Status deleteStatus(long id);

	CursoredList<Status> getBilateralTimeline();

	CursoredList<Status> getBilateralTimeline(int pageSize, int pageNumber);

	CursoredList<Status> getBilateralTimeline(int pageSize, int pageNumber,
			boolean onlyApplicationStatus);

	CursoredList<Status> getBilateralTimeline(long sinceId, long maxId,
			int pageSize, int pageNumber, boolean onlyApplicationStatus,
			StatusContentType statusContentType);

	List<Status> getDailyHotComments();

	List<Status> getDailyHotComments(int pageSize, boolean onlyApplicationStatus);

	List<Status> getDailyHotRepost();

	List<Status> getDailyHotRepost(int pageSize, boolean onlyApplicationStatus);

	CursoredList<Status> getFriendsTimeline();

	CursoredList<Status> getFriendsTimeline(int pageSize, int pageNumber);

	CursoredList<Status> getFriendsTimeline(int pageSize, int pageNumber,
			boolean onlyApplicationStatus);

	CursoredList<Status> getFriendsTimeline(long sinceId, long maxId,
			int pageSize, int pageNumber, boolean onlyApplicationStatus,
			StatusContentType statusContentType);

	CursoredList<Status> getHomeTimeline();

	CursoredList<Status> getHomeTimeline(int pageSize, int pageNumber);

	CursoredList<Status> getHomeTimeline(int pageSize, int pageNumber,
			boolean onlyApplicationStatus);

	CursoredList<Status> getHomeTimeline(long sinceId, long maxId,
			int pageSize, int pageNumber, boolean onlyApplicationStatus,
			StatusContentType statusContentType);

	CursoredList<Status> getMentions();

	CursoredList<Status> getMentions(int pageSize, int pageNumber);

	CursoredList<Status> getMentions(long sinceId, long maxId, int pageSize,
			int pageNumber, AuthorFilterType authorFilterType,
			SourceFilterType sourceFilterType, boolean createdInWeibo);

	CursoredList<Status> getPublicTimeline();

	CursoredList<Status> getPublicTimeline(int pageSize, int pageNumber);

	CursoredList<Status> getPublicTimeline(int pageSize, int pageNumber,
			boolean onlyApplicationStatus);

	CursoredList<Status> getRepostByMe();

	CursoredList<Status> getRepostByMe(int pageSize, int pageNumber);

	CursoredList<Status> getRepostByMe(long sinceId, long maxId, int pageSize,
			int pageNumber);

	CursoredList<Status> getRepostTimeline(long id);

	CursoredList<Status> getRepostTimeline(long id, int pageSize, int pageNumber);

	CursoredList<Status> getRepostTimeline(long id, long sinceId, long maxId,
			int pageSize, int pageNumber, AuthorFilterType authorFilterType);

	CursoredList<Status> getUserTimeline(long uid);

	CursoredList<Status> getUserTimeline(long uid, int pageSize, int pageNumber);

	CursoredList<Status> getUserTimeline(long uid, int pageSize,
			int pageNumber, boolean onlyApplicationStatus);

	CursoredList<Status> getUserTimeline(long uid, long sinceId, long maxId,
			int pageSize, int pageNumber, boolean onlyApplicationStatus,
			StatusContentType statusContentType);

	List<Status> getWeeklyHotComments();

	List<Status> getWeeklyHotComments(int pageSize,
			boolean onlyApplicationStatus);

	List<Status> getWeeklyHotRepost();

	List<Status> getWeeklyHotRepost(int pageSize, boolean onlyApplicationStatus);

	Status repostStatus(long id, String message);

	Status updateStatus(String message);

	Status updateStatus(String message, float latitude, float longitude);

	Status updateStatus(String message, Resource media);

	Status updateStatus(String message, Resource media, float latitude,
			float longitude);

}
