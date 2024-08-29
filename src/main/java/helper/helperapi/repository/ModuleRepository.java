package helper.helperapi.repository;

import helper.helperapi.models.Module;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends MongoRepository <Module ,String>{
    Optional<Module>findByModuleName(String moduleName);
}
