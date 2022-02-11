package net.korvin.fb2;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Testing {

    BiFunction<String, Integer, Supplier<String>> bf;

    public Testing() {};
//    public Testing(BiFunction<String, Integer, Supplier<String>> bf) {
//        this.bf = bf;
//    }

//    Testing x  = new Testing(this::method);

    public static void main(String[] args) {
        Testing a = new Testing();
        a.bf = a::method;


        System.out.println(a.bf.apply("alex",1));
        System.out.println(a.bf.apply("sascha",2));
    }

    Supplier<String> xx = () -> {
        System.out.println(" s:"+3+" i:"+4);
        return "xxx";
    };

    private Supplier<String> method(String s, Integer integer) {
        return xx;
//        return () -> {
//            System.out.println(" s:"+s+" i:"+integer);
//            return "xxx";
//        };
    }

}
