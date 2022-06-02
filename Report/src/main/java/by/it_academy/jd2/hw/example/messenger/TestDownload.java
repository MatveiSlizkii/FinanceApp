package by.it_academy.jd2.hw.example.messenger;

import org.apache.poi.util.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.*;
import java.net.URL;


public class TestDownload {
    public static void main(String[] args) {
        try (BufferedInputStream bis = new BufferedInputStream(new URL("http://res.cloudinary.com/dlydpec5" +
                "e/raw/upload/v1653683231/217729b0-4c2b-4099-9c1e-1f2139fc4da2.xls").openStream()))
        {
            byte[] bytes = IOUtils.toByteArray(bis);
            System.out.println(bytes.length);
             File file = new File("C:\\Users\\user\\Desktop\\test.xls");
            OutputStream os = new FileOutputStream(file);
            os.write(bytes);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }





    }
}
