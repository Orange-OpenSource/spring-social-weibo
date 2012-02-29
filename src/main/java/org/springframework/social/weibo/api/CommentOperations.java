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

public interface CommentOperations {

	Comment createComment(long id, String comment);

	Comment createComment(long id, String comment,
			boolean commentFromExternalSource);

	Comment deleteComment(long id);

	List<Comment> deleteComments(List<Long> ids);

	CursoredList<Comment> getCommentsByMe();

	CursoredList<Comment> getCommentsByMe(int pageSize, int pageNumber);

	CursoredList<Comment> getCommentsByMe(int pageSize, int pageNumber,
			SourceFilterType sourceFilterType);

	CursoredList<Comment> getCommentsByMe(long sinceId, long maxId,
			int pageSize, int pageNumber, SourceFilterType sourceFilterType);

	CursoredList<Comment> getMentioningComments();

	CursoredList<Comment> getMentioningComments(int pageSize, int pageNumber);

	CursoredList<Comment> getMentioningComments(int pageSize, int pageNumber,
			AuthorFilterType authorFilterType, SourceFilterType sourceFilterType);

	CursoredList<Comment> getMentioningComments(long sinceId, long maxId,
			int pageSize, int pageNumber, AuthorFilterType authorFilterType,
			SourceFilterType sourceFilterType);

	CursoredList<Comment> getCommentsOnStatus(long id);

	CursoredList<Comment> getCommentsOnStatus(long id, int pageSize,
			int pageNumber);

	CursoredList<Comment> getCommentsOnStatus(long id, int pageSize,
			int pageNumber, AuthorFilterType authorFilterType);

	CursoredList<Comment> getCommentsOnStatus(long id, long sinceId,
			long maxId, int pageSize, int pageNumber,
			AuthorFilterType authorFilterType);

	/**
	 * @deprecated This API seems buggy, no result is returned
	 * @param ids
	 * @return
	 */
	@Deprecated
	List<Comment> getCommentsOnStatuses(List<Long> ids);

	CursoredList<Comment> getCommentsTimeline();

	CursoredList<Comment> getCommentsTimeline(int pageSize, int pageNumber);

	CursoredList<Comment> getCommentsTimeline(long sinceId, long maxId,
			int pageSize, int pageNumber);

	CursoredList<Comment> getCommentsToMe();

	CursoredList<Comment> getCommentsToMe(int pageSize, int pageNumber);

	CursoredList<Comment> getCommentsToMe(int pageSize, int pageNumber,
			AuthorFilterType authorFilterType, SourceFilterType sourceFilterType);

	CursoredList<Comment> getCommentsToMe(long sinceId, long maxId,
			int pageSize, int pageNumber, AuthorFilterType authorFilterType,
			SourceFilterType sourceFilterType);

}
