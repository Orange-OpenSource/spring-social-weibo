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

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.social.weibo.api.Status;
import org.springframework.social.weibo.api.WeiboProfile;

/**
 * Annotated mixin to add Jackson annotations to Comment.
 * 
 * @author edva8332
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class CommentMixin {
	CommentMixin(
			@JsonProperty("id") long id,
			@JsonProperty("created_at") @JsonDeserialize(using = DateDeserializer.class) Date createAt,
			@JsonProperty("text") String text,
			@JsonProperty("source") String source) {
	}

	@JsonProperty("mid")
	String mid;
	@JsonProperty("user")
	WeiboProfile user;
	@JsonProperty("status")
	Status status;

}
