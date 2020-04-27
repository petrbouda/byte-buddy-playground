package pbouda.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;

import static net.bytebuddy.implementation.MethodDelegation.to;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class Delegation {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<?> myClass = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(to(MyInterceptor.class))
                .make()
                .load(Delegation.class.getClassLoader())
                .getLoaded();

        System.out.println(myClass.newInstance().toString());
    }

    public static class MyInterceptor {
        public static String intercept(@Origin Method method) {
            return "Hello World from " + method.getName();
        }

//        public static long intercept(int arg, @SuperCall Callable<Long> superMethod) {
//            return cache.computeIfAbsent(arg, o -> {
//                try {
//                    return superMethod.call();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return 0L;
//            });
//        }
    }
}
