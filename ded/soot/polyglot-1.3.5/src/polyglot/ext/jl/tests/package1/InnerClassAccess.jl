package package1;

import package1.InnerClassProblem.Problem;

public class InnerClassAccess {
    
    public void run(Object o) {
        if (o instanceof Problem) {
            System.out.println("Smile");
        }
    }
}
