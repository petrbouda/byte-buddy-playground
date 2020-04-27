package pbouda.bytebuddy;

public class Foo {
    @Log
    public String bar() {
        System.out.println("bar");
        return "bar";
    }
}