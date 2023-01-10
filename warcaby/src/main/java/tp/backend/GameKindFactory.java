package tp.backend;

import javax.swing.event.SwingPropertyChangeSupport;

public class GameKindFactory {
  public IGameKind getGameKind(String type) {
    switch(type) {
      case "czech":
        return new CzechKind();
      case "german":
        return new GermanKind();
      //case "swidish":
        //return new SwedishKind();
    }

    return null;
  }
}
