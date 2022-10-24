package ru.uskov.testRelex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.uskov.testRelex.dto.FileDTO;
import ru.uskov.testRelex.repository.FileRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void addFile(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String filePath = "files/" + file.getOriginalFilename();

            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(bytes);
            stream.close();

            fileRepository.setFilePath(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения файла");
        }
    }

    @Override
    public void addFilePath(String filePath) {
        fileRepository.setFilePath(filePath);
    }

    @Override
    @Cacheable(value = "maxValue", key = "@fileRepository.hashCalculate()")
    public FileDTO getMaxValue() {
        Long maxValue = readFile(fileRepository.getFilePath()).
                stream().
                max(Long::compareTo).
                orElseThrow(() -> new RuntimeException("Файл пустой"));

        FileDTO fileDTO = new FileDTO();
        fileDTO.setMaxValue(maxValue);

        return fileDTO;
    }

    @Override
    @Cacheable(value = "minValue", key = "@fileRepository.hashCalculate()")
    public FileDTO getMinValue() {
        Long minValue = readFile(fileRepository.getFilePath()).
                stream().
                min(Long::compareTo).
                orElseThrow(() -> new RuntimeException("Файл пустой"));

        FileDTO fileDTO = new FileDTO();
        fileDTO.setMinValue(minValue);

        return fileDTO;
    }

    @Override
    @Cacheable(value = "medianValue", key = "@fileRepository.hashCalculate()")
    public FileDTO getMedianValue() {
        try {
            List<Long> list = readFile(fileRepository.getFilePath()).
                    stream().
                    sorted(Long::compareTo).
                    toList();

            int middle = list.size() / 2;
            double medianValue = list.get(middle);
            if (list.size() % 2 == 0) {
                medianValue = (medianValue + list.get((middle - 1))) / 2;
            }

            FileDTO fileDTO = new FileDTO();
            fileDTO.setMedianValue(medianValue);
            return fileDTO;

        } catch (Exception e) {
            throw new RuntimeException("Файл пустой");
        }
    }

    @Override
    @Cacheable(value = "avgValue", key = "@fileRepository.hashCalculate()")
    public FileDTO getAvgValue() {
        Double avgValue = readFile(fileRepository.getFilePath()).
                stream().
                mapToLong(e -> e).
                average().
                orElseThrow(() -> new RuntimeException("Файл пустой"));

        FileDTO fileDTO = new FileDTO();
        fileDTO.setAvgValue(avgValue);

        return fileDTO;
    }

    @Override
    @Cacheable(value = "ascendingSeq", key = "@fileRepository.hashCalculate()")
    public FileDTO getAscendingSeq() {
        try {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setAscendingSeq(findSeq(true));

            return fileDTO;
        } catch (Exception e) {
            throw new RuntimeException("Файл пустой");
        }
    }

    @Override
    @Cacheable(value = "descendingSeq", key = "@fileRepository.hashCalculate()")
    public FileDTO getDescendingSeq() {
        try {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setDescendingSeq(findSeq(false));
            return fileDTO;
        } catch (Exception e) {
            throw new RuntimeException("Файл пустой");
        }
    }

    @Override
    public FileDTO callOperation(FileDTO fileDTO) {
        FileDTO result;
        switch (fileDTO.getOperation()) {
            case "get_max_value":
                result = getMaxValue();
                break;
            case "get_min_value":
                result = getMinValue();
                break;
            case "get_avg_value":
                result = getAvgValue();
                break;
            case "get_median_value":
                result = getMedianValue();
                break;
            case "get_ascending_seq":
                result = getAscendingSeq();
                break;
            case "get_descending_seq":
                result = getDescendingSeq();
                break;
            default:
                result = null;
        }
        return result;
    }

    @Override
    public List<Long> readFile(String filePath) {

        try {
            List<Long> list;
            BufferedReader in = new BufferedReader(new FileReader(fileRepository.getFilePath()));
            list = new ArrayList<>(in.lines().map(i -> Long.parseLong(i)).toList());
            in.close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Файл не найден");
        }

    }

    @Override
    public List<List<Long>> findSeq(boolean type) {
        List<Long> list = readFile(fileRepository.getFilePath());
        List<List<Long>> result = new ArrayList<>();
        List<Long> ascendingSeq = new ArrayList<>();
        long countElem = 0;
        long maxCountElem = 0;
        long prevElem = list.get(0);
        list.remove(0);

        for (long elem : list) {
            if ((elem <= prevElem && !type) || (elem >= prevElem && type)) {
                ascendingSeq.add(elem);
                countElem++;
            } else {
                ascendingSeq.clear();
                countElem = 1;
                ascendingSeq.add(elem);
            }

            if (countElem > maxCountElem) {
                result.clear();
                result.add(new ArrayList<>(ascendingSeq));
                maxCountElem = countElem;
            } else if (countElem == maxCountElem) {
                result.add(new ArrayList<>(ascendingSeq));
            }

            prevElem = elem;
        }
        return result;
    }

}
