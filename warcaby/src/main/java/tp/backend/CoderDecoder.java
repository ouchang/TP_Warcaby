package tp.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.io.IOException;

/**
 * Implementation of communication protocol between Client and Server
 */
public class CoderDecoder {
  
  /**
   * Command's deserializer
   * @param json serialized command as JSON
   * @param type command's type
   * @return command as object
   */
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

  /**
   * Gerneric task deserializer
   * @param json serialized command as JSON
   * @return command as object
   */
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

  /**
   * Generic task type deserializer
   * @param json
   * @return command's type
   */
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
  
  /**
   * Command's serializer
   * @param object command object
   * @return serialized command as JSON
   */
  String codeCommand(ICommand object) {
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
    }

    return null;
  }
}
