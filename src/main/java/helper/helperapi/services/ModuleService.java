package helper.helperapi.services;

import helper.helperapi.exception.ModuleCollectionException;
import helper.helperapi.models.Module;

import java.util.List;

public interface ModuleService {
    public List <Module> getAllModule ();
    public void createModule(Module module) throws ModuleCollectionException ;

    public Module getSingleTodo(String id) throws ModuleCollectionException ;
}
