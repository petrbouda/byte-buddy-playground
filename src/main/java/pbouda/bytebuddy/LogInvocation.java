package pbouda.bytebuddy;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.nameContains;

public class LogInvocation {

    public static void main(String[] args) {
        premain("", ByteBuddyAgent.install());

        Foo foo = new Foo();
        System.out.println(foo.bar());
    }

    public static void premain(String arg, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(nameContains("Foo"))
                .transform((builder, type, ignored1, ignored2) -> builder
                        .method(ElementMatchers.isAnnotatedWith(Log.class))
                        .intercept(MethodDelegation.to(LogAdvice.class)
                                .andThen(SuperMethodCall.INSTANCE)))
                .installOn(instrumentation);
    }

    public static class LogAdvice {
        public static void intercept(@Origin Method method) {
            System.out.println(method.getName() + " was called");
        }
    }
}

@interface Log {
}
