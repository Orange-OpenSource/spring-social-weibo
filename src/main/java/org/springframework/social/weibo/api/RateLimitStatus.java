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

import java.util.Date;
import java.util.List;

public class RateLimitStatus {

	private int ipLimit;
	private LimitTimeUnit limitTimeUnit;
	private int remainingIpHits;
	private int remainingUserHits;
	private Date resetTime;
	private int resetTimeInSeconds;
	private int userLimit;
	private List<ApiRateLimit> apiRateLimits;

	/**
	 * @return the ipLimit
	 */
	public int getIpLimit() {
		return ipLimit;
	}

	/**
	 * @param ipLimit
	 *            the ipLimit to set
	 */
	public void setIpLimit(int ipLimit) {
		this.ipLimit = ipLimit;
	}

	/**
	 * @return the limitTimeUnit
	 */
	public LimitTimeUnit getLimitTimeUnit() {
		return limitTimeUnit;
	}

	/**
	 * @param limitTimeUnit
	 *            the limitTimeUnit to set
	 */
	public void setLimitTimeUnit(LimitTimeUnit limitTimeUnit) {
		this.limitTimeUnit = limitTimeUnit;
	}

	/**
	 * @return the remainingIpHits
	 */
	public int getRemainingIpHits() {
		return remainingIpHits;
	}

	/**
	 * @param remainingIpHits
	 *            the remainingIpHits to set
	 */
	public void setRemainingIpHits(int remainingIpHits) {
		this.remainingIpHits = remainingIpHits;
	}

	/**
	 * @return the remainingUserHits
	 */
	public int getRemainingUserHits() {
		return remainingUserHits;
	}

	/**
	 * @param remainingUserHits
	 *            the remainingUserHits to set
	 */
	public void setRemainingUserHits(int remainingUserHits) {
		this.remainingUserHits = remainingUserHits;
	}

	/**
	 * @return the resetTime
	 */
	public Date getResetTime() {
		return resetTime;
	}

	/**
	 * @param resetTime
	 *            the resetTime to set
	 */
	public void setResetTime(Date resetTime) {
		this.resetTime = resetTime;
	}

	/**
	 * @return the resetTimeInSeconds
	 */
	public int getResetTimeInSeconds() {
		return resetTimeInSeconds;
	}

	/**
	 * @param resetTimeInSeconds
	 *            the resetTimeInSeconds to set
	 */
	public void setResetTimeInSeconds(int resetTimeInSeconds) {
		this.resetTimeInSeconds = resetTimeInSeconds;
	}

	/**
	 * @return the userLimit
	 */
	public int getUserLimit() {
		return userLimit;
	}

	/**
	 * @param userLimit
	 *            the userLimit to set
	 */
	public void setUserLimit(int userLimit) {
		this.userLimit = userLimit;
	}

	/**
	 * @return the apiRateLimits
	 */
	public List<ApiRateLimit> getApiRateLimits() {
		return apiRateLimits;
	}

	/**
	 * @param apiRateLimits
	 *            the apiRateLimits to set
	 */
	public void setApiRateLimits(List<ApiRateLimit> apiRateLimits) {
		this.apiRateLimits = apiRateLimits;
	}

}
