package by.it_academy.jd2.hw.example.messenger.services.claudinary;

import by.it_academy.jd2.hw.example.messenger.services.claudinary.api.ICloudStorage;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.http.HttpEntity;
import org.apache.poi.util.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudService implements ICloudStorage {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dlydpec5e",
            "api_key", "674496632189625",
            "api_secret", "FC_3Jx1u-3RQnIEe20S4PlxM1ss"));

    @Override
    public String upload(byte[] file) {

        String fileName = UUID.randomUUID() + ".xls";
        String resourceType = "raw";

        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap("resource_type", resourceType, "public_id", fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadResult.get("url").toString();
    }

    @Override
    public byte[] download(String link) {
        try (BufferedInputStream bis = new BufferedInputStream(new URL(link).openStream())) {
            byte[] bytes = IOUtils.toByteArray(bis);
            File file = new File("test.xls");
            OutputStream os = new FileOutputStream(file);
            os.write(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        return null;
    }
}
