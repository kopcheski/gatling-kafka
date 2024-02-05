package io.github.amerousful.kafka;

import io.gatling.core.structure.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.github.amerousful.kafka.java.KafkaDsl.kafka;

public class Example {

	private final ScenarioBuilder scn = scenario("Basic")
			.exec(
					kafka("Kafka: request with reply")
							.requestReply()
							.topic("input_topic")
							.payload("{ \"m\": \"#{payload}\" }")
							.replyTopic("output_topic")
							.key("#{id} #{key}")
							.check(jsonPath("$.m").is("#{payload}_1"))
			);
}
