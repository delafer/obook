package net.korvin;

import sun.misc.Unsafe;

public class TestMain {
    public static void main(String[] args) {

//        Unsafe a;
//        a.getUnsafe().get

        TagEngine te = new TagEngine();
        try {

            te.process("c:\\polygon\\sample3.fb2");

        }  catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
