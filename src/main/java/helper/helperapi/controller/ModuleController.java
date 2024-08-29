package helper.helperapi.controller;

import helper.helperapi.exception.ModuleCollectionException;
import helper.helperapi.models.Module;
import helper.helperapi.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@ResponseBody
@RequestMapping("/helper-api")

public class ModuleController {
   @Autowired
   private ModuleService moduleService ;
    @GetMapping("/test")
    public  String addModule(){
       return  "ok";
   }


    @PostMapping("/addModule")
    public ResponseEntity<?> createModule (@RequestBody Module module) {
        try {
            moduleService.createModule(module);
            return new ResponseEntity<Module>(module, HttpStatus.OK);
        } catch (ModuleCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @GetMapping("/get_all_module")
    public  ResponseEntity<?>getAllModules (){
        List<Module>modules = moduleService.getAllModule();
        return  new ResponseEntity<>(modules ,modules.size()>0? HttpStatus.OK:HttpStatus.NOT_FOUND);
    }

}
