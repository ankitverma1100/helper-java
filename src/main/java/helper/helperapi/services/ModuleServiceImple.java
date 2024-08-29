package helper.helperapi.services;

import helper.helperapi.exception.ModuleCollectionException;
import helper.helperapi.models.Module;
import helper.helperapi.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImple implements  ModuleService{
    @Autowired
    private ModuleRepository moduleRepository ;
    @Override
    public List<Module> getAllModule() {
        List<Module>modules = moduleRepository.findAll();
        if(modules.size()>0){
            return modules ;
        }else{
            return new ArrayList<Module>();
        }
    }

    @Override
    public void createModule(Module module) throws ModuleCollectionException {
        Optional<Module> modules = moduleRepository.findByModuleName(module.getModuleName());
        if(modules.isPresent()){
            throw new ModuleCollectionException("Module already add");
        }
        module.setCreatedAt(new Date(System.currentTimeMillis()));
        moduleRepository.save(module);
    }

    @Override
    public Module getSingleTodo(String id) throws ModuleCollectionException {
        return null;
    }
}