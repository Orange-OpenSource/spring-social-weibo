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
/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.weibo.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.weibo.api.Weibo;
import org.springframework.social.weibo.api.impl.WeiboTemplate;

/**
 * Twitter ServiceProvider implementation that exposes the Twitter 4j API
 * binding.
 * 
 * @author Craig Walls
 */
public final class WeiboServiceProvider extends
		AbstractOAuth2ServiceProvider<Weibo> {

	public WeiboServiceProvider(String consumerKey, String consumerSecret) {
		super(new WeiboOAuth2Template(consumerKey, consumerSecret));
	}

	@Override
	public Weibo getApi(String accessToken) {
		return new WeiboTemplate(accessToken);
	}

}