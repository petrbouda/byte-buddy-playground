package pbouda.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;

import static net.bytebuddy.implementation.FixedValue.value;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class HotSwap {

    public static void main(String[] args) {
        Foo foo = new Foo();

        new ByteBuddy()
                .redefine(Foo.class)
                .method(named("bar"))
                .intercept(value("Hello World!"))
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        System.out.println(foo.bar());
    }

    public static class Foo {
        public String bar() {
            return "bar";
        }
    }
}
