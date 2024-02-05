package io.github.amerousful.kafka.java;

import io.gatling.commons.validation.Validation;
import io.gatling.core.check.Check;
import io.gatling.core.session.Session;
import io.gatling.javaapi.core.CheckBuilder;
import io.gatling.javaapi.core.internal.Expressions;
import io.github.amerousful.kafka.action.RequestReplyBuilder;
import io.github.amerousful.kafka.request.KafkaAttributes;
import scala.*;
import scala.collection.immutable.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static scala.jdk.javaapi.CollectionConverters.asScala;

public class RequestReplyDslBuilder {

	private String key;

	private List<Check> checks = new ArrayList<>();

	public RequestReplyDslBuilder key(String key) {
		this.key = key;
		return this;
	}

	public RequestReplyDslBuilder check(CheckBuilder.Final check) {
		this.checks.add(check);
		return this;
	}

	public static final class Topic {

		private final String requestName;

		public Topic(String requestName) {
			this.requestName = requestName;
		}

		public Payload topic(String topicName) {
			return new Payload(requestName, topicName);
		}
	}

	public static final class Payload {

		private final String requestName;
		private final String topicName;

		public Payload(String requestName, String topicName) {
			this.requestName = requestName;
			this.topicName = topicName;
		}

		public ReplyTopic payload(String payload) {
			return new ReplyTopic(requestName, topicName, payload);
		}
	}

	public static final class ReplyTopic {

		private final String requestName;
		private final String topicName;
		private final String payload;

		public ReplyTopic(String requestName, String topicName, String payload) {
			this.requestName = requestName;
			this.topicName = topicName;
			this.payload = payload;
		}

		public RequestReplyDslBuilder replyTopic(String replyTopic) {
			Function1<Session, Validation<String>> requestNameExpression = Expressions.toStringExpression(requestName);
			Function1<Session, Validation<String>> topicNameExpression = Expressions.toStringExpression(topicName);
			Function1<Session, Validation<String>> payloadExpression = Expressions.toStringExpression(payload);
			Option<Function1<Session, Validation<String>>> payloadExpressionOption = new Some<>(payloadExpression);

			java.util.Map<Object, Object> headers = Collections.emptyMap();
			Map<Object, Object> scalaMap = scala.collection.immutable.Map.from(asScala(headers));

			java.util.List<Check> checks = null;
			scala.collection.immutable.List.from(asScala(checks));
			return new RequestReplyDslBuilder(
					new KafkaAttributes(requestNameExpression, topicNameExpression, null, payloadExpressionOption,
							scalaMap, this.checks),
					new RequestReplyBuilder(replyTopic));
		}
	}
}
