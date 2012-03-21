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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.social.weibo.api.Trends;
import org.springframework.social.weibo.api.Trends.Trend;

public class TrendsDeserializer extends JsonDeserializer<SortedSet<Trends>> {

	private static final Log logger = LogFactory
			.getLog(TrendsDeserializer.class.getName());

	private static final Comparator<? super Trends> comparator = new Comparator<Trends>() {

		@Override
		public int compare(Trends o1, Trends o2) {
			if (o1.getDate() == null) {
				if (o2.getDate() == null) {
					return 0;
				} else {
					return 1;
				}
			} else if (o2.getDate() == null) {
				return -1;
			} else {
				return o1.getDate().compareTo(o2.getDate());
			}
		}
	};

	@Override
	public SortedSet<Trends> deserialize(JsonParser jp,
			DeserializationContext ctxt) throws IOException,
			JsonProcessingException {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		TreeSet<Trends> result = new TreeSet<Trends>(comparator);
		for (Iterator<Entry<String, JsonNode>> iterator = jp.readValueAsTree()
				.getFields(); iterator.hasNext();) {
			Entry<String, JsonNode> next = iterator.next();
			Trends trends = new Trends();
			try {
				dateFormat
						.applyPattern(retrieveDateFormatPattern(next.getKey()));
				trends.setDate(dateFormat.parse(next.getKey()));
				JsonNode trendsNode = next.getValue();
				for (Iterator<JsonNode> iterator2 = trendsNode.getElements(); iterator2
						.hasNext();) {
					JsonParser nodeParser = iterator2.next().traverse();
					nodeParser.setCodec(jp.getCodec());
					Trend readValueAs = nodeParser.readValueAs(Trend.class);
					trends.getTrends().add(readValueAs);
				}
				result.add(trends);
			} catch (ParseException e) {
				logger.warn("Unable to parse date", e);
			}
		}
		return result;
	}

	private String retrieveDateFormatPattern(String key) {
		String result = null;
		switch (key.length()) {
		case 19:
			result = "yyyy-MM-dd HH:mm:ss";
			break;
		case 16:
			result = "yyyy-MM-dd HH:mm";
			break;
		default:
			result = "yyyy-MM-dd";
			break;
		}
		return result;
	}
}
