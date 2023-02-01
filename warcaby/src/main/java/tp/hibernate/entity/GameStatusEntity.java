package tp.hibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="GameStatus")
public class GameStatusEntity {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  @Column(name="GAMEID", length=1024, nullable=true)
  String gameID;

  @Column(name="GAMESTATUS", length=1024, nullable=true)
  String gameStatusSerialized;

  public String getGameStatusSerialized() {
    return this.gameStatusSerialized;
  }

  public void setGameStatusSerialized(String gameStatus) {
    this.gameStatusSerialized = gameStatus;
  }

  public String getGameID() {
    return this.gameID;
  }

  public void setGameID(String id) {
    this.gameID = id;
  }
}
