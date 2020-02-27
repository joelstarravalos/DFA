package fa.dfa;
/**
 * Subclass of fa.State that represents a single state in a Deterministic Finite Automata.
 * Contains a mapping of characters to strings to represent transitions for that state
 * @author joelstarravalos
 */
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import fa.State;
public class DFAState extends fa.State {
	//A mapping of symbols to the state to be transitioned to
	private HashMap<Character,String> transitions;
	/**
	 * Constructor
	 * @param name is label for the state
	 */
	public DFAState(String name) {
		this.name = name;
		transitions = new HashMap<Character, String>();
	}
	
	/**
	 * Adds a transition to the map of transitions
	 * @param symbol - the symbol in the string being transitioned on
	 * @param state - the name of the state being transitioned to
	 */
	public void addTransition(char symbol,String state) {
		transitions.put(symbol, state);
	}
	
	/**
	 * Gets the name of the next state to transition to for the given symbol
	 * @param symbol - the character being recognized
	 * @return
	 */
	public String getTransition(char symbol) {
		String ret = transitions.get(symbol);
		if (ret == null) {
			System.out.println("No transition found for this symbol, not a DFA");
		}
		return transitions.get(symbol);
	}
	
	/**
	 * Gets the entire set of transition as a Set of Entries
	 * @return Set of all transitions
	 */
	public Set<Entry<Character, String>> getAllTransitions() {
		return transitions.entrySet();
	}
	
	/**
	 * Compare String to state's label
	 * @param name of state to be compared to
	 * @return whether this state equals the parameter
	 */
	public boolean equals(String name) {
		if (name == this.name) return true; else return false;
	}
	/**
	 * Returns a completely separate copy of this state
	 * @return copied DFAState
	 */
	public DFAState copy() {
		DFAState copy = new DFAState(name);
		for (Entry<Character, String> x : this.getAllTransitions()) {
			copy.addTransition(x.getKey(), x.getValue());
		}
		return copy;
	}
}
