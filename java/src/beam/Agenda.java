package beam;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Agenda {
  private PriorityQueue<ParseState> pq;
  private int beamSize;

  public Agenda(int _beamSize) {
    pq = new PriorityQueue<>(Comparator.reverseOrder());
    beamSize = _beamSize;
  }

  public boolean empty() { return pq.size() == 0; }

  public void push(ParseState o) {
    if (pq.size() < beamSize) pq.add(o);
    else {
      ParseState lowestScoreState = __findLowestScoreState();
      if (o.getScore() > lowestScoreState.getScore()) {
        pq.remove(lowestScoreState); pq.add(o);
      }
    }
  }

  public ParseState pop() {
    ParseState st = pq.peek(); pq.remove(st);
    return st;
  }

  public void pushAll(List<ParseState> states) {
    for (ParseState state : states) push(state);
  }

  private ParseState __findLowestScoreState() {
    ParseState result = pq.peek(); int minScore = Integer.MIN_VALUE;

    for (ParseState state : pq) {
      if (state.getScore() < minScore) {
        minScore = state.getScore();
        result = state;
      }
    }
    return result;
  }
}
