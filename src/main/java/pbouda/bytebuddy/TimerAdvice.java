package pbouda.bytebuddy;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.nameContains;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class TimerAdvice {

    public static void main(String[] args) {
        agentmain("", ByteBuddyAgent.install());
        Foo foo = new Foo();
        foo.bar();
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .disableClassFormatChanges()
                .type(nameContains("Foo"))
                .transform((builder, typeDescription, classLoader, module) ->
                        builder.visit(Advice.to(TimedAdvice.class).on(named("bar"))))
                .installOn(instrumentation);
    }

    public static class TimedAdvice {

        @Advice.OnMethodEnter
        public static long enter() {
            return System.nanoTime();
        }

        @Advice.OnMethodExit
        public static void exit(@Advice.Enter long start, @Advice.Origin String method) {
            long duration = System.nanoTime() - start;
            System.out.println(method + " took " + duration);
        }

        /*

        class Foo {
            String bar() {
                long $start = System.nanoTime();
                String $result = "bar";
                long $duration = System.nanoTime() - $start;
                System.out.println("Foo.bar()" + " took " + $duration);
                return $result;
            }
        }

         */
    }
}
