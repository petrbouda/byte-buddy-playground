package pbouda.bytebuddy;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.any;

/**
 * TODO: It doesn't work :(
 */
public class PreAndPostEntry {

    public static void main(String[] args) {
        premain("", ByteBuddyAgent.install());
        PrePostFoo foo = new PrePostFoo();
        foo.bar();
    }

    public static void premain(String arg, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .disableClassFormatChanges()
                .type(ElementMatchers.nameContains("PrePostFoo"))
                .transform(((builder, typeDescription, classLoader, module) -> builder.method(any())
                        .intercept(MethodDelegation.to(PrePostInterceptor.class))))
                .installOn(instrumentation);
    }

    public static class PrePostFoo {
        public void bar() {
            System.out.println("bar");
        }
    }

    public static class PrePostInterceptor {
        @RuntimeType
        public static void intercept(@This Object obj, @SuperCall Runnable invocation, @Origin Method method) {
            System.out.println("pre");
//            invocation.run();
            System.out.println("post");
        }
    }
}
