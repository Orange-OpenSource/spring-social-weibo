/*
* Copyright 2011 France Télécom
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

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.InternalServerErrorException;
import org.springframework.social.ServerDownException;
import org.springframework.social.ServerOverloadedException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;

public class WeiboErrorHandler extends DefaultResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		HttpStatus statusCode = response.getStatusCode();
		if (statusCode.series() == Series.SERVER_ERROR) {
			handleServerErrors(statusCode);
		} else if (statusCode.series() == Series.CLIENT_ERROR) {
			handleClientErrors(response);
		}

		// if not otherwise handled, do default handling and wrap with
		// UncategorizedApiException
		try {
			super.handleError(response);
		} catch (Exception e) {
			throw new UncategorizedApiException(
					"Error consuming Weibo REST API", e);
		}
	}

	/**
	 * According to error code, throw exception
	 * 
	 * @param response
	 *            response from Weibo server
	 * @throws IOException
	 */
	private void handleClientErrors(ClientHttpResponse response)
			throws IOException {
		Map<String, Object> errorMap = extractErrorDetailsFromResponse(response);
		if (errorMap == null) {
			return; // unexpected error body, can't be handled here
		}

		int errorCode;
		if (errorMap.containsKey("error_code")) {
			errorCode = (Integer) errorMap.get("error_code");
			switch (errorCode) {
			case 21327:
				throw new ExpiredAuthorizationException();
			default:
				throw new UncategorizedApiException(String.format(
						"Error calling weibo: error code=%d, error message=%s",
						errorCode, errorMap.get("error")),
						new RestClientException("Unknown status code ["
								+ response.getStatusCode() + "]"));
			}
		}
	}

	private void handleServerErrors(HttpStatus statusCode) throws IOException {
		if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR) {
			throw new InternalServerErrorException(
					"Something is broken at Weibo.");
		} else if (statusCode == HttpStatus.BAD_GATEWAY) {
			throw new ServerDownException("Weibo is down or is being upgraded.");
		} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE) {
			throw new ServerOverloadedException(
					"Weibo is overloaded with requests. Try again later.");
		}
	}

	private Map<String, Object> extractErrorDetailsFromResponse(
			ClientHttpResponse response) throws IOException {
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		try {
			return mapper.<Map<String, Object>> readValue(response.getBody(),
					new TypeReference<Map<String, Object>>() {
					});
		} catch (JsonParseException e) {
			return null;
		}
	}

}
