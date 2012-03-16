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
import org.springframework.social.weibo.api.CursoredList;
import org.springframework.social.weibo.api.Favorite;
import org.springframework.social.weibo.api.Favorite.Tag;
import org.springframework.social.weibo.api.FavoriteOperations;
import org.springframework.web.client.RestTemplate;

public class FavoriteTemplate extends AbstractWeiboOperations implements
		FavoriteOperations {

	protected FavoriteTemplate(ObjectMapper objectMapper,
			RestTemplate restTemplate, boolean isAuthorized) {
		super(objectMapper, restTemplate, isAuthorized);
	}

	@Override
	public CursoredList<Favorite> getFavorites() {
		requireAuthorization();
		JsonNode jsonNode = restTemplate.getForObject(
				buildUri("favorites.json"), JsonNode.class);
		return deserializeCursoredList(jsonNode, Favorite.class, "favorites");
	}

	@Override
	public CursoredList<Favorite> getFavorites(int pageSize, int pageNumber) {
		requireAuthorization();
		JsonNode jsonNode = restTemplate
				.getForObject(
						uriBuilder("favorites.json")
								.queryParam("count", String.valueOf(pageSize))
								.queryParam("page", String.valueOf(pageNumber))
								.build(), JsonNode.class);
		return deserializeCursoredList(jsonNode, Favorite.class, "favorites");
	}

	@Override
	public Favorite getFavorite(long id) {
		requireAuthorization();
		return restTemplate.getForObject(uriBuilder("favorites/show.json")
				.queryParam("id", String.valueOf(id)).build(), Favorite.class);
	}

	@Override
	public CursoredList<Tag> getTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CursoredList<Tag> getTags(int pageSize, int pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
