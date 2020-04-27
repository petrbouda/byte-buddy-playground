package pbouda.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PrintVariable {

    /*

List<StackManipulation> statements = new ArrayList();

statements.add(IntegerConstant.forValue(false));
statements.add(MethodVariableAccess.INTEGER.storeAt(3));

...

StackManipulation logic = new StackManipulation.Compound(statements.toArray(new StackManipulation[0]));

StackManipulation.Size size = new StackManipulation.Compound(logic).apply(methodVisitor, context);

return new ByteCodeAppender.Size(size.getMaximalSize(), methodDescription.getStackSize());

     */

    public static void main(String[] args) throws IOException {

//        Map<TypeDescription, File> files = new ByteBuddy()
//                .rebase(MyVarObject.class)
//                .method(ElementMatchers.named("addVar"))
//                .intercept(new Implementation.Simple())
//                .make()
//                .saveIn(new File("bytecodes"));

//        Printer.bytecode(files);
    }

    public static class MyVarObject {

        public static void addVar(int i) {
            int my_variable = i;
        }
    }
}
