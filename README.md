# spring-social-weibo

This project is an [Spring Social](http://www.springsource.org/spring-social) extension for [weibo](http://www.weibo.com).
Currently, only basic operations are supported:

* get profile by id or username
* get statuses (pagination is not supported): user timeline, home timeline, public timeline
* update status (only text is supported)
* get friends (pagination is not supported)
* get followers (pagination is not supported)

If you are using this project, you should probably consider to read the [term of use of Weibo](http://open.weibo.com/wiki/%E5%BA%94%E7%94%A8%E5%BC%80%E5%8F%91%E8%80%85%E5%8D%8F%E8%AE%AE)

# Getting started

In order to get started with spring-social-weibo, you have to follow only 3 simple steps.

## Step 1: Get the code from GitHub

git clone --recursive git://github.com/vergnes/spring-social-weibo.git

## Step 2: Use Maven to build project

cd spring-social-weibo
mvn clean install

## Step 3: Add spring-social-weibo as a Maven dependency

	<dependency>
	    <groupId>org.springframework.social</groupId>
	    <artifactId>spring-social-weibo</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>

# License

This project is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
