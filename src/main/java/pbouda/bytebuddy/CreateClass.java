package pbouda.bytebuddy;

import net.bytebuddy.ByteBuddy;

import static net.bytebuddy.implementation.FixedValue.value;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class CreateClass {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<?> myClass = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(value("Hello World!"))
                .make()
                .load(CreateClass.class.getClassLoader())
                .getLoaded();

        String hello = myClass.newInstance().toString();
        System.out.println(hello);
    }
}
