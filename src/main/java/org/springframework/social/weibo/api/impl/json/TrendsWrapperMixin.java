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
import java.util.SortedSet;

import org.springframework.social.weibo.api.Trends.Trend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Annotated mixin to add Jackson annotations to TrendsWrapper.
 * 
 * @author edva8332
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class TrendsWrapperMixin {

	@JsonProperty("trends")
	@JsonDeserialize(using = TrendsDeserializer.class)
	SortedSet<Trend> trends;

	@JsonProperty("as_of")
	@JsonDeserialize(using = DateInSecondsDeserializer.class)
	Date asOf;

}
