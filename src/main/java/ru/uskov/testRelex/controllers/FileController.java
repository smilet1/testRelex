package ru.uskov.testRelex.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.uskov.testRelex.dto.FileDTO;
import ru.uskov.testRelex.service.FileService;


@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public FileDTO uploadFile(@RequestParam MultipartFile file, FileDTO fileDTO) {
        fileService.addFile(file);
        return fileDTO.getOperation() != null ? fileService.callOperation(fileDTO) : new FileDTO();
    }

    @PostMapping("/uploadFilePath")
    @ResponseBody
    public FileDTO uploadFilePath(@RequestBody FileDTO fileDTO) {
        fileService.addFilePath(fileDTO.getFilePath());
        return fileDTO.getOperation() != null ? fileService.callOperation(fileDTO) : new FileDTO();
    }

    @GetMapping("/get_max_value")
    @ResponseBody
    public FileDTO getMaxValue() {
        return fileService.getMaxValue();
    }

    @GetMapping("/get_min_value")
    @ResponseBody
    public FileDTO getMinValue() {
        return fileService.getMinValue();
    }

    @GetMapping("/get_median_value")
    @ResponseBody
    public FileDTO getMedianValue() {
        return fileService.getMedianValue();
    }

    @GetMapping("/get_avg_value")
    @ResponseBody
    public FileDTO getAvgValue() {
        return fileService.getAvgValue();
    }

    @GetMapping("/get_ascending_seq")
    @ResponseBody
    public FileDTO getAscendingSeq() {
        return fileService.getAscendingSeq();
    }

    @GetMapping("/get_descending_seq")
    @ResponseBody
    public FileDTO getDescendingSeq() {
        return fileService.getDescendingSeq();
    }


}
