package check.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class TableContentFetcherService {
    public<T> String fetchTableContentAsString(/*String pathName,*/ CrudRepository repository){
        //File file = new File(pathName);

        Iterable<T> iterable = repository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String res = "";
        try {
            res =  objectMapper.writeValueAsString(iterable);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  res;
    }

    public <T> File fetchTableContentIntoFile(String pathName,CrudRepository<T,Integer> repository){
        //File file = new File(pathName);

        Iterable<T> iterable = repository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();

        File resFile = null;
        try {
            Path paths = Paths.get(pathName);
            resFile = new File(pathName);

            Files.deleteIfExists(paths);
            Files.createFile(Paths.get(pathName));
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(resFile,iterable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resFile;
    }
}
