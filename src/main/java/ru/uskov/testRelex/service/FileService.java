package ru.uskov.testRelex.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.uskov.testRelex.dto.FileDTO;

import java.io.IOException;
import java.util.List;


@Service
public interface FileService {
    public void addFile(MultipartFile file);
    public void addFilePath(String filePath);
    public FileDTO getMaxValue();
    public FileDTO getMinValue();
    public FileDTO getMedianValue();
    public FileDTO getAvgValue();
    public FileDTO getAscendingSeq();
    public FileDTO getDescendingSeq();
    public FileDTO callOperation(FileDTO fileDTO);
    public List<Long> readFile(String filePath)throws IOException;
    public List<List<Long>> findSeq(boolean type);//true - Ascending; false - Descending
}
