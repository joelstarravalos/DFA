/**
 * 
 */
package fa.dfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fa.State;

/**
 * Implementation of the DFAInterface representing a Deterministic Finite Automata capable of accepting a 
 * set of states including a start state and final state(s) and transitions for each state (which are held by the states
 * themselves) on an alphabet of symbols.
 * @author joelstarravalos
 *
 */
public class DFA implements DFAInterface {
	//represents set of all states (Q), HashMap for constant time access to states and linked maintenance of order
	private LinkedHashMap<String, DFAState> states;
	//represents the set of final states (F) 
	private LinkedHashMap<String, DFAState> finals;
	//respresents start state (q0)
	private DFAState start;
	//LinkedHashSet representing the set of symbols (Sigma), linked for maintenance of order
	private LinkedHashSet<Character> alphabet;
	
	public DFA() {
		states = new LinkedHashMap<String, DFAState>();
		finals = new LinkedHashMap<String, DFAState>();
		alphabet = new LinkedHashSet<Character>();
		alphabet.add('e');
	}
	/**
	 * Adds the initial state to the DFA instance
	 * @param name is the label of the start state
	 */
	@Override
	public void addStartState(String name) {
		start = new DFAState(name);
		states.put(name, start);
	}
	
	/**
	 * Changes the initial state
	 * @param start is the new start state
	 */
	private void changeStart(DFAState start) {
		this.start = start;
	}
	
	/**
	 * Adds a non-final, not initial state to the DFA instance
	 * @param name is the label of the state 
	 */
	@Override
	public void addState(String name) {
		states.put(name, new DFAState(name));
	}
	
	/**
	 * Adds a non-final, not initial state to the DFA instance
	 * @param state is an actual DFAState
	 */
	private void addState(DFAState state) {
		states.put(state.getName(), state);
	}

	/**
	 * Adds a final state to the DFA
	 * @param name is the label of the state
	 */
	@Override
	public void addFinalState(String name) {
		DFAState fs =  new DFAState(name);
		finals.put(name, fs);
		states.put(name, fs);
		
	}
	
	/**
	 * Adds a final state to the DFA
	 * @param state is an actual final DFAState
	 */
	public void addFinalState(DFAState state) {
		finals.put(state.getName(), state);
		states.put(state.getName(), state);
	}
	
	/**
	 * Adds the transition to the DFA's delta data structure
	 * @param fromState is the label of the state where the transition starts
	 * @param onSymb is the symbol from the DFA's alphabet.
	 * @param toState is the label of the state where the transition ends
	 */
	@Override
	public void addTransition(String fromState, char onSymb, String toState) {
		alphabet.add(onSymb);
		states.get(fromState).addTransition(onSymb, toState);
	}
	
	/**
	 * Getter for Q
	 * @return a set of states that FA has
	 */
	@Override
	public Set<DFAState> getStates() {
		HashSet<DFAState> ret = new HashSet<DFAState>();
		for (DFAState x : states.values()) {
			ret.add(x);
		}
		return ret;
	}
	
	/**
	 * Getter for F
	 * @return a set of final states that FA has
	 */
	@Override
	public Set<DFAState> getFinalStates() {
		HashSet<DFAState> ret = new HashSet<DFAState>();
		for (DFAState x : finals.values()) {
			ret.add(x);
		}
		return ret;
	}
	
	/**
	 * Getter for q0
	 * @return the start state of FA
	 */
	@Override
	public State getStartState() {
		return start;
	}

	
	/**
	 * Getter for Sigma
	 * @return the alphabet of FA
	 */
	@Override
	public Set<Character> getABC() {
		return new HashSet<Character>(alphabet);
	}
	
	/**
	 * Computes a copy of this DFA
	 * which language is the complement
	 * of this DFA's language.
	 * @return a copy of this DFA
	 */
	@Override
	public DFA complement() {
		//whole new DFA object
		DFA comp = new DFA();
		//same start state
		comp.changeStart(start);
		for (DFAState x : states.values()) {
			//add states that are not final states of the og DFA to the final states of the complement
			if (!finals.containsValue(x)) comp.addFinalState(x);
			// o/w they are non-final states
			else comp.addState(x.copy());
		}
		//alphabet is the same
		comp.getABC().addAll(alphabet);
		return comp;
	}

	/**
	 * Construct the textual representation of the DFA, for example
	 * A simple two state DFA
	 * Q = { a b }
	 * Sigma = { 0 1 }
	 * delta =
	 *		0	1	
	 *	a	a	b	
	 *	b	a	b	
	 * q0 = a
	 * F = { b }
	 * 
	 * The order of the states and the alphabet is the order
	 * in which they were instantiated in the DFA.
	 * @return String representation of the DFA
	 */
	public String toString() {
		String s = "";
		//append states
		s += "Q = { ";
		for (String x : states.keySet()) {
			s += x + " ";
		}
		s += "}\n";
		
		//append alphabet
		s += "Sigma = {";
		for (char x : alphabet) {
			if (x != 'e') s += x + " ";
		}
		s += "}\n";
		
		//append transition table
		s += "Delta =\n\t";
		for (char x : alphabet) {
			if (x != 'e') s += x + "\t";
		}
		s += "\n";
		for (DFAState x : states.values()) {
			s += x.getName() + "\t";
			for (char y : alphabet) {
				if (y!='e') s += x.getTransition(y) + "\t";
			}
			s += "\n";
		}
		s += "\n";
		
		//append start and final states
		s += "q0 = " + start.getName() + "\n";
		s += "F = { ";
		for (String x : finals.keySet()) {
			s += x + " ";
		}
		s += "}\n";
		return s;
		
	}
	
	/**
	 * Simulates a DFA on input s to determine
	 * whether the DFA accepts s.
	 * @param s - the input string
	 * @return true if s in the language of the DFA and false otherwise
	 */
	@Override
	public boolean accepts(String s) {
		DFAState curState = start;
		int i = 0;
		//initial state condition
		boolean accept = finals.containsKey(curState.getName());
		
		//test the empty string
		char curChar = s.charAt(i);
		if (curChar == 'e') return accept;
		
		//loops until end of string
		while (i < s.length()) {
			curChar = s.charAt(i);
			curState = states.get(curState.getTransition(curChar));
			accept = finals.containsKey(curState.getName());
			i++;
		}
		//condition of the final state determines acceptance of string
		return accept;
	}
	
	/**
	 * Uses transition function delta of FA
	 * @param from the source state
	 * @param onSymb the label of the transition
	 * @return the sink state.
	 */
	@Override
	public State getToState(DFAState from, char onSymb) {
			return states.get(from.getTransition(onSymb));
	}

}
