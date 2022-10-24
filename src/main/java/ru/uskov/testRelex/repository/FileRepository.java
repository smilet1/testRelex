package ru.uskov.testRelex.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.Checksum;


@Repository
@Getter
@Setter
public class FileRepository {

    private String filePath;

    public long hashCalculate() {
        try {
            byte[] bytes = Files.readAllBytes(Path.of(this.filePath));
            Checksum crc32 = new CRC32();
            crc32.update(bytes, 0, bytes.length);
            return crc32.getValue();
        }catch (Exception e){
            throw new RuntimeException("Путь к файлу указан некорректно");
        }
    }
}
