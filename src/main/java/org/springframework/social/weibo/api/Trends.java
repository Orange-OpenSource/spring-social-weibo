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
import java.util.LinkedList;
import java.util.List;

public class Trends {

	public static class Trend {
		private String name;
		private String query;
		private long amount;
		private long delta;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the query
		 */
		public String getQuery() {
			return query;
		}

		/**
		 * @param query
		 *            the query to set
		 */
		public void setQuery(String query) {
			this.query = query;
		}

		/**
		 * @return the amount
		 */
		public long getAmount() {
			return amount;
		}

		/**
		 * @param amount
		 *            the amount to set
		 */
		public void setAmount(long amount) {
			this.amount = amount;
		}

		/**
		 * @return the delta
		 */
		public long getDelta() {
			return delta;
		}

		/**
		 * @param delta
		 *            the delta to set
		 */
		public void setDelta(long delta) {
			this.delta = delta;
		}
	}

	private List<Trend> trends = new LinkedList<Trends.Trend>();
	private Date date;

	/**
	 * @return the trends
	 */
	public List<Trend> getTrends() {
		return trends;
	}

	/**
	 * @param trends
	 *            the trends to set
	 */
	public void setTrends(List<Trend> trends) {
		this.trends = trends;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
