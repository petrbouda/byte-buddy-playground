package pbouda.bytebuddy;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.MemberSubstitution;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class MyMemberSubstitution {

    private static final Field MEMBER_DEFAULT;

    static {
        try {
            MEMBER_DEFAULT = Member.class.getField("DEFAULT");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        premain("", ByteBuddyAgent.install());

        Member member = new Member();
        member.memberize();
    }

    public static void premain(String arg, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(ElementMatchers.nameContains("Member"))
                .transform((builder, typeDescription, classLoader, module) -> builder
                        .visit(MemberSubstitution.strict()
                                .method(named("println"))
                                .replaceWith(new FieldDescription.ForLoadedField(MEMBER_DEFAULT))
                                .on(named("memberize"))))
                .installOn(instrumentation);
    }

    public static class Member {

        public static final String DEFAULT = "DEFAULT HELLO";

        public void memberize() {
            System.out.println("hello");
        }
    }

}
