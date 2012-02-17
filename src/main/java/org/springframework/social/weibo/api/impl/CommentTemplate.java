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

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.weibo.api.AuthorFilterType;
import org.springframework.social.weibo.api.Comment;
import org.springframework.social.weibo.api.CommentOperations;
import org.springframework.social.weibo.api.CursoredList;
import org.springframework.social.weibo.api.SourceFilterType;
import org.springframework.web.client.RestTemplate;

class CommentTemplate extends AbstractWeiboOperations implements
		CommentOperations {

	protected CommentTemplate(ObjectMapper objectMapper,
			RestTemplate restTemplate, boolean isAuthorized) {
		super(objectMapper, restTemplate, isAuthorized);
	}

	@Override
	public CursoredList<Comment> getComments(long id) {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("comments/show.json", "id", id), JsonNode.class);
		return deserializeCursoredList(dataNode, Comment.class, "comments");
	}

	@Override
	public CursoredList<Comment> getComments(long id, int pageSize,
			int pageNumber) {
		return getComments(id, pageSize, pageNumber, AuthorFilterType.ALL);
	}

	@Override
	public CursoredList<Comment> getComments(long id, int pageSize,
			int pageNumber, AuthorFilterType authorFilterType) {
		return getComments(id, 0, 0, pageSize, pageNumber, authorFilterType);
	}

	@Override
	public CursoredList<Comment> getComments(long id, long sinceId, long maxId,
			int pageSize, int pageNumber, AuthorFilterType authorFilterType) {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				uriBuilder("comments/show.json")
						.queryParam("id", String.valueOf(id))
						.queryParam("since_id", String.valueOf(sinceId))
						.queryParam("max_id", String.valueOf(maxId))
						.queryParam("count", String.valueOf(pageSize))
						.queryParam("page", String.valueOf(pageNumber))
						.queryParam("filter_by_author",
								String.valueOf(authorFilterType.ordinal()))
						.build(), JsonNode.class);
		return deserializeCursoredList(dataNode, Comment.class, "comments");
	}

	@Override
	public CursoredList<Comment> getCommentsByMe() {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("comments/by_me.json"), JsonNode.class);
		return deserializeCursoredList(dataNode, Comment.class, "comments");
	}

	@Override
	public CursoredList<Comment> getCommentsByMe(int pageSize, int pageNumber) {
		return getCommentsByMe(pageSize, pageNumber, SourceFilterType.ALL);
	}

	@Override
	public CursoredList<Comment> getCommentsByMe(int pageSize, int pageNumber,
			SourceFilterType sourceFilterType) {
		return getCommentsByMe(0, 0, pageSize, pageNumber, sourceFilterType);
	}

	@Override
	public CursoredList<Comment> getCommentsByMe(long sinceId, long maxId,
			int pageSize, int pageNumber, SourceFilterType sourceFilterType) {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				uriBuilder("comments/by_me.json")
						.queryParam("since_id", String.valueOf(sinceId))
						.queryParam("max_id", String.valueOf(maxId))
						.queryParam("count", String.valueOf(pageSize))
						.queryParam("page", String.valueOf(pageNumber))
						.queryParam("filter_by_source",
								String.valueOf(sourceFilterType.ordinal()))
						.build(), JsonNode.class);
		return deserializeCursoredList(dataNode, Comment.class, "comments");
	}

	@Override
	public CursoredList<Comment> getCommentsToMe() {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("comments/to_me.json"), JsonNode.class);
		return deserializeCursoredList(dataNode, Comment.class, "comments");
	}

	@Override
	public CursoredList<Comment> getCommentsToMe(int pageSize, int pageNumber) {
		return getCommentsToMe(pageSize, pageNumber, AuthorFilterType.ALL,
				SourceFilterType.ALL);
	}

	@Override
	public CursoredList<Comment> getCommentsToMe(int pageSize, int pageNumber,
			AuthorFilterType authorFilterType, SourceFilterType sourceFilterType) {
		return getCommentsToMe(0, 0, pageSize, pageNumber, authorFilterType,
				sourceFilterType);
	}

	@Override
	public CursoredList<Comment> getCommentsToMe(long sinceId, long maxId,
			int pageSize, int pageNumber, AuthorFilterType authorFilterType,
			SourceFilterType sourceFilterType) {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				uriBuilder("comments/to_me.json")
						.queryParam("since_id", String.valueOf(sinceId))
						.queryParam("max_id", String.valueOf(maxId))
						.queryParam("count", String.valueOf(pageSize))
						.queryParam("page", String.valueOf(pageNumber))
						.queryParam("filter_by_author",
								String.valueOf(authorFilterType.ordinal()))
						.queryParam("filter_by_source",
								String.valueOf(sourceFilterType.ordinal()))
						.build(), JsonNode.class);
		return deserializeCursoredList(dataNode, Comment.class, "comments");
	}
}
