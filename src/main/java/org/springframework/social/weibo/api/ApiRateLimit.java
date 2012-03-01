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

public class ApiRateLimit {

	private String api;
	private int limit;
	private LimitTimeUnit limitTimeUnit;
	private int remainingHits;

	/**
	 * @return the api
	 */
	public String getApi() {
		return api;
	}

	/**
	 * @param api
	 *            the api to set
	 */
	public void setApi(String api) {
		this.api = api;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
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
	 * @return the remainingHits
	 */
	public int getRemainingHits() {
		return remainingHits;
	}

	/**
	 * @param remainingHits
	 *            the remainingHits to set
	 */
	public void setRemainingHits(int remainingHits) {
		this.remainingHits = remainingHits;
	}

}
