package net.korvin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ScanFiles {

    public static void main(String[] args) {
        try {
            //String oldScan = "C:\\ebooks\\bity";
            String oldScan = "C:\\arbeit\\ebooks\\code\\fb2\\example\\xx";
           // String oldScan = "a:\\book\\";
            List<File> files = Files.walk(Paths.get(oldScan))
                    .sequential()
                    .filter(Files::isRegularFile)
                    .filter(f -> f.toString().endsWith(".fb2"))
                    // .limit(100)
                    .map(Path::toFile)

                    .toList();
            FileWriter fw = new FileWriter("out.txt", Charset.forName("UTF-8"), false);

            PrintWriter error = new PrintWriter(new FileWriter("error.txt", Charset.forName("UTF-8"), false));

            for (File f : files) {
                process(f, fw, error);
            }
            fw.flush();;
            fw.close();;
            error.flush();
            error.close();

        }catch (Throwable e) {
            e.printStackTrace();
        }
    }



    private static void process(File x,FileWriter fw, PrintWriter error )  {
        try {
            //System.out.println();
            fw.write(x.getPath());
            fw.write("\r\n");
            //System.out.print("Processing: "+x.getPath()+" ::: ");
            TagEngine te = new TagEngine();
            FileInputStream fis = new FileInputStream(x);
            te.process(fis);
            //System.out.println("done: "+x.getPath());
            fis.close();
        } catch (Exception e) {
            System.out.println("Error parsing: "+x.getPath());
            e.printStackTrace();
            try {
                error.write("Error parsing: "+x.getPath()+"\r\n");
                e.printStackTrace(error);
                error.write("\r\n");
            } catch (Throwable ignore) {}
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

}
