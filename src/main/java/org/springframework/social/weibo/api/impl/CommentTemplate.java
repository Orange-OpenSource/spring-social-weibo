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

import java.net.URI;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.weibo.api.AuthorFilterType;
import org.springframework.social.weibo.api.Comment;
import org.springframework.social.weibo.api.CommentOperations;
import org.springframework.social.weibo.api.CursoredList;
import org.springframework.social.weibo.api.SourceFilterType;
import org.springframework.social.weibo.util.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

class CommentTemplate extends AbstractWeiboOperations implements
		CommentOperations {

	protected CommentTemplate(ObjectMapper objectMapper,
			RestTemplate restTemplate, boolean isAuthorized) {
		super(objectMapper, restTemplate, isAuthorized);
	}

	@Override
	public Comment createComment(long id, String comment) {
		return createComment(id, comment, false);
	}

	@Override
	public Comment createComment(long id, String comment,
			boolean commentFromExternalSource) {
		requireAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>(
				3);
		request.add("id", String.valueOf(id));
		request.add("comment", comment);
		request.add("comment_ori",
				StringUtils.booleanToString(commentFromExternalSource));
		return restTemplate.postForObject(buildUri("comments/create.json"),
				request, Comment.class);
	}

	@Override
	public Comment deleteComment(long id) {
		requireAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>(
				1);
		request.add("cid", String.valueOf(id));
		return restTemplate.postForObject(buildUri("comments/destroy.json"),
				request, Comment.class);
	}

	@Override
	public List<Comment> deleteComments(List<Long> ids) {
		requireAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>(
				1);
		request.add("cids", StringUtils.join(ids));
		JsonNode dataNode = restTemplate.postForObject(
				buildUri("comments/destroy_batch.json"), request,
				JsonNode.class);
		return deserializeDataList(dataNode, Comment.class);
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
	public CursoredList<Comment> getCommentsOnStatus(long id) {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("comments/show.json", "id", id), JsonNode.class);
		return deserializeCursoredList(dataNode, Comment.class, "comments");
	}

	@Override
	public CursoredList<Comment> getCommentsOnStatus(long id, int pageSize,
			int pageNumber) {
		return getCommentsOnStatus(id, pageSize, pageNumber,
				AuthorFilterType.ALL);
	}

	@Override
	public CursoredList<Comment> getCommentsOnStatus(long id, int pageSize,
			int pageNumber, AuthorFilterType authorFilterType) {
		return getCommentsOnStatus(id, 0, 0, pageSize, pageNumber,
				authorFilterType);
	}

	@Override
	public CursoredList<Comment> getCommentsOnStatus(long id, long sinceId,
			long maxId, int pageSize, int pageNumber,
			AuthorFilterType authorFilterType) {
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
	public List<Comment> getCommentsOnStatuses(List<Long> ids) {
		requireAuthorization();
		URI uri = buildUri("comments/show_batch.json", "cids",
				StringUtils.join(ids));
		JsonNode dataNode = restTemplate.getForObject(uri, JsonNode.class);
		return deserializeDataList(dataNode, Comment.class);
	}

	@Override
	public CursoredList<Comment> getCommentsTimeline() {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("comments/timeline.json"), JsonNode.class);
		return deserializeCursoredList(dataNode, Comment.class, "comments");
	}

	@Override
	public CursoredList<Comment> getCommentsTimeline(int pageSize,
			int pageNumber) {
		return getCommentsTimeline(0, 0, pageSize, pageNumber);
	}

	@Override
	public CursoredList<Comment> getCommentsTimeline(long sinceId, long maxId,
			int pageSize, int pageNumber) {
		requireAuthorization();
		JsonNode dataNode = restTemplate
				.getForObject(
						uriBuilder("comments/timeline.json")
								.queryParam("since_id", String.valueOf(sinceId))
								.queryParam("max_id", String.valueOf(maxId))
								.queryParam("count", String.valueOf(pageSize))
								.queryParam("page", String.valueOf(pageNumber))
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

	@Override
	public CursoredList<Comment> getMentioningComments() {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				buildUri("comments/mentions.json"), JsonNode.class);
		return deserializeCursoredList(dataNode, Comment.class, "comments");
	}

	@Override
	public CursoredList<Comment> getMentioningComments(int pageSize,
			int pageNumber) {
		return getMentioningComments(pageSize, pageNumber,
				AuthorFilterType.ALL, SourceFilterType.ALL);
	}

	@Override
	public CursoredList<Comment> getMentioningComments(int pageSize,
			int pageNumber, AuthorFilterType authorFilterType,
			SourceFilterType sourceFilterType) {
		return getMentioningComments(0, 0, pageSize, pageNumber,
				authorFilterType, sourceFilterType);
	}

	@Override
	public CursoredList<Comment> getMentioningComments(long sinceId,
			long maxId, int pageSize, int pageNumber,
			AuthorFilterType authorFilterType, SourceFilterType sourceFilterType) {
		requireAuthorization();
		JsonNode dataNode = restTemplate.getForObject(
				uriBuilder("comments/mentions.json")
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
