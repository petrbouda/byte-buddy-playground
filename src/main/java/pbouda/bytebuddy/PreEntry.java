package pbouda.bytebuddy;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.PrintStream;
import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.any;

public class PreEntry {

    private static final MethodCall PRINTLN_METHOD_CALL;

    static {
        try {
            PRINTLN_METHOD_CALL = MethodCall.invoke(PrintStream.class.getMethod("println", String.class))
                    .onField(System.class.getField("out"));
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        premain("", ByteBuddyAgent.install());
        Foo foo = new Foo();
        foo.bar();
    }

    public static void premain(String arg, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.nameContains("Foo"))
                .transform(((builder, typeDescription, classLoader, module) -> builder.method(any())
                        .intercept(PRINTLN_METHOD_CALL
                                .with("pre-bar")
                                .andThen(SuperMethodCall.INSTANCE))))
                .installOn(instrumentation);
    }

    public static class Foo {
        public void bar() {
            System.out.println("bar");
        }
    }

}
