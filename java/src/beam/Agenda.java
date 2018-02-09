package beam;

import java.util.PriorityQueue;

public class Agenda {
  private PriorityQueue<ParseState> pq;
  private int beamSize;

  public Agenda(int _beamSize) {
    pq = new PriorityQueue<>();
    beamSize = _beamSize;
  }

  public void push(ParseState o) {
    if (pq.size() < beamSize) pq.add(o);
    else {
      ParseState lowestScoreState = __findLowestScoreState();
      if (o.getScore() > lowestScoreState.getScore()) {
        pq.remove(lowestScoreState); pq.add(o);
      }
    }
  }

  public ParseState pop() { return pq.remove(); }

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
