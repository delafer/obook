package net.korvin.transcoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ConvertXmls {

    public static void main(String[] args) {
        try {
            //String oldScan = "C:\\ebooks\\bity";
            //String oldScan = "C:\\arbeit\\ebooks\\code\\fb2\\example\\covers\\";
           String oldScan = "C:\\workspace\\spielplatz\\input";
            List<File> files = Files.walk(Paths.get(oldScan))
                    .sequential()
                    .filter(Files::isRegularFile)
                    .filter(f -> f.toString().endsWith(".xml") || f.toString().endsWith(".htm") || f.toString().endsWith(".html"))
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
            String fileName = x.getPath();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.flush();
            fw.write(fileName);
            fw.write("\r\n");
            System.out.print("Processing: "+x.getPath()+" ::: ");
            var te = new StaxWriter();
            FileInputStream fis = new FileInputStream(x);
            te.process(fileName, x.getName());
            System.out.println("done: "+x.getPath());
            fis.close();
            System.out.flush();
            Thread.sleep(750);
        } catch (Exception e) {
            System.out.println("Error parsing & converting: "+x.getPath());
            e.printStackTrace();
            try {
                error.write("Error parsing & converting: "+x.getPath()+"\r\n");
                e.printStackTrace(error);
                error.write("\r\n");
            } catch (Throwable ignore) {}
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.flush();
        }

    }

}
