package tp.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.io.IOException;

class CoderDecoder {

  private ICommand decode(String json, String type) {
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      ICommand command = CommandFactory.getCommand(type);
      command = (ICommand) mapper.readValue(json, CommandFactory.getClass(type));
      return command;
    } catch(JsonProcessingException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  ICommand decodeCommand(String json) {
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      CommandClass cmd = new CommandClass();
      cmd = mapper.readValue(json, cmd.getClass());
      return decode(cmd.getValue(), cmd.getType());
    } catch(JsonProcessingException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  String decodeCall(String json) {
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      CommandClass cmd = new CommandClass();
      cmd = mapper.readValue(json, cmd.getClass());
      return cmd.getType();
    } catch(JsonProcessingException e) {
      System.out.println(e.getMessage());
    }
    
    return "";
  }
  
  String codeCommand(ICommand object) { // void
    try {
      ObjectMapper mapper = new ObjectMapper(); 
      mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
      String objectAsString = mapper.writeValueAsString(object);
      CommandClass cmd = new CommandClass();
      cmd.setType(object.getClass().getSimpleName());
      cmd.setValue(objectAsString);
      String commandAsString = mapper.writeValueAsString(cmd);
      return commandAsString;
    } catch(JsonProcessingException e) {
      System.out.println(e.getMessage());
    } catch(IOException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
}
