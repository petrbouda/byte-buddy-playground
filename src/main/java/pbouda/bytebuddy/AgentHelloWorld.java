package pbouda.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.implementation.FixedValue.value;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class AgentHelloWorld {

    public static void premain(String args, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(named("Foo"))
                .transform((builder, typeDescription, classLoader, module) ->
                        builder.method(named("bar"))
                                .intercept(value("Hello World!!"))
                )
                .installOn(instrumentation);
    }

    public static class Foo {
        String bar() {
            return "bar";
        }
    }
}
