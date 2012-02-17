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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import org.junit.Test;
import org.springframework.social.weibo.api.AuthorFilterType;
import org.springframework.social.weibo.api.Comment;
import org.springframework.social.weibo.api.CursoredList;
import org.springframework.social.weibo.api.SourceFilterType;

public class CommentTemplateTest extends AbstractWeiboOperationsTest {

	private CommentTemplate commentTemplate;

	@Override
	public void setUp() {
		commentTemplate = new CommentTemplate(getObjectMapper(),
				getRestTemplate(), true);
	}

	@Test
	public void testGetCommentsOnStatusOnStatusOnStatusOnStatus() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/comments/show.json?id=123"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("comments"), responseHeaders));
		CursoredList<Comment> comments = commentTemplate
				.getCommentsOnStatus(123L);
		verifyComment(comments.iterator().next());
		assertEquals(2, comments.size());
		assertEquals(7, comments.getTotalNumber());
		assertEquals(0, comments.getPreviousCursor());
		assertEquals(10, comments.getNextCursor());
	}

	@Test
	public void testGetCommentsOnStatusOnStatusOnStatusOnStatusByMe() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/comments/by_me.json"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("comments"), responseHeaders));
		CursoredList<Comment> comments = commentTemplate.getCommentsByMe();
		verifyComment(comments.iterator().next());
		assertEquals(2, comments.size());
		assertEquals(7, comments.getTotalNumber());
		assertEquals(0, comments.getPreviousCursor());
		assertEquals(10, comments.getNextCursor());
	}

	@Test
	public void testGetCommentsOnStatusOnStatusOnStatusOnStatusByMePagination() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/comments/by_me.json?since_id=0&max_id=0&count=50&page=5&filter_by_source=0"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("comments"), responseHeaders));
		CursoredList<Comment> comments = commentTemplate.getCommentsByMe(50, 5);
		verifyComment(comments.iterator().next());
		assertEquals(2, comments.size());
		assertEquals(7, comments.getTotalNumber());
		assertEquals(0, comments.getPreviousCursor());
		assertEquals(10, comments.getNextCursor());
	}

	@Test
	public void testGetCommentsOnStatusOnStatusOnStatusOnStatusPagination() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/comments/show.json?id=123&since_id=0&max_id=0&count=50&page=5&filter_by_author=0"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("comments"), responseHeaders));
		CursoredList<Comment> comments = commentTemplate.getCommentsOnStatus(
				123L, 50, 5);
		verifyComment(comments.iterator().next());
		assertEquals(2, comments.size());
		assertEquals(7, comments.getTotalNumber());
		assertEquals(0, comments.getPreviousCursor());
		assertEquals(10, comments.getNextCursor());
	}

	private void verifyComment(Comment comment) {
		assertEquals(12438492184L, comment.getId());
		assertEquals(1306860625000L, comment.getCreatedAt().getTime());
		assertEquals("我喜欢你做的", comment.getText());
		assertNotNull(comment.getUser());
		assertNotNull(comment.getStatus());
	}

	@Test
	public void testGetCommentsOnStatusOnStatusOnStatusOnStatusToMe() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/comments/to_me.json"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("comments"), responseHeaders));
		CursoredList<Comment> comments = commentTemplate.getCommentsToMe();
		verifyComment(comments.iterator().next());
		assertEquals(2, comments.size());
		assertEquals(7, comments.getTotalNumber());
		assertEquals(0, comments.getPreviousCursor());
		assertEquals(10, comments.getNextCursor());
	}

	@Test
	public void testGetCommentsOnStatusOnStatusOnStatusOnStatusToMePagination() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/comments/to_me.json?since_id=0&max_id=0&count=50&page=5&filter_by_author=0&filter_by_source=0"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("comments"), responseHeaders));
		CursoredList<Comment> comments = commentTemplate.getCommentsToMe(50, 5);
		verifyComment(comments.iterator().next());
		assertEquals(2, comments.size());
		assertEquals(7, comments.getTotalNumber());
		assertEquals(0, comments.getPreviousCursor());
		assertEquals(10, comments.getNextCursor());
	}

	@Test
	public void testGetCommentsOnStatusOnStatusOnStatusOnStatusToMePaginationFiltered() {
		mockServer
				.expect(requestTo("https://api.weibo.com/2/comments/to_me.json?since_id=0&max_id=0&count=50&page=5&filter_by_author=1&filter_by_source=0"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("comments"), responseHeaders));
		CursoredList<Comment> comments = commentTemplate.getCommentsToMe(50, 5,
				AuthorFilterType.FRIENDS, SourceFilterType.ALL);
		verifyComment(comments.iterator().next());
		assertEquals(2, comments.size());
		assertEquals(7, comments.getTotalNumber());
		assertEquals(0, comments.getPreviousCursor());
		assertEquals(10, comments.getNextCursor());
	}

}
