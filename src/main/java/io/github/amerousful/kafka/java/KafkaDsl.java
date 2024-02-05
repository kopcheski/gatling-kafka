package io.github.amerousful.kafka.java;

public final class KafkaDsl {

    public static KafkaDslBuilderBase kafka(String something) {
        return new KafkaDslBuilderBase();
    }


}
