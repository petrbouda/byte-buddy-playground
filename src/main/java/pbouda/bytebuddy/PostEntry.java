package pbouda.bytebuddy;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.ToStringMethod;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.PrintStream;
import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.any;

public class PostEntry {

    public static void main(String[] args) {
        premain("", ByteBuddyAgent.install());
        Bar bar = new Bar();
        bar.bar();
    }

    public static void premain(String arg, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.nameContains("Bar"))
                .transform(((builder, typeDescription, classLoader, module) -> builder.method(any())
                        .intercept(SuperMethodCall.INSTANCE.andThen(new MyImplementation()))))
                .installOn(instrumentation);
    }

    public static class Bar {
        public void bar() {
            System.out.println("bar");
        }
    }

    private static class MyImplementation implements Implementation {

        @Override
        public ByteCodeAppender appender(Target implementationTarget) {
            if (implementationTarget.getInstrumentedType().isInterface()) {
                throw new IllegalStateException("Cannot implement append " + implementationTarget.getInstrumentedType());
            }
            return null;
        }

        @Override
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return null;
        }
    }
}
