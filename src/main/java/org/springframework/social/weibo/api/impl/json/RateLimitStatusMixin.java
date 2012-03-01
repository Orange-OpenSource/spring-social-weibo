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
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.social.weibo.api.ApiRateLimit;
import org.springframework.social.weibo.api.LimitTimeUnit;

@JsonIgnoreProperties(ignoreUnknown = true)
class RateLimitStatusMixin {

	@JsonProperty("ip_limit")
	int ipLimit;
	@JsonProperty("limit_time_unit")
	LimitTimeUnit limitTimeUnit;
	@JsonProperty("remaining_ip_hits")
	int remainingIpHits;
	@JsonProperty("remaining_user_hits")
	int remainingUserHits;
	@JsonProperty("reset_time")
	@JsonDeserialize(using = DateDeserializer.class)
	Date resetTime;
	@JsonProperty("reset_time_in_seconds")
	int resetTimeInSeconds;
	@JsonProperty("user_limit")
	int userLimit;
	@JsonProperty("api_rate_limits")
	List<ApiRateLimit> apiRateLimits;

}
