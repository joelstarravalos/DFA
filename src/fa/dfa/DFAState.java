package fa.dfa;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import fa.State;
public class DFAState extends fa.State {
	private HashMap<Character,String> transitions;
	public DFAState(String name) {
		this.name = name;
		transitions = new HashMap<Character, String>();
	}
	
	public void addTransition(char symbol,String state) {
		transitions.put(symbol, state);
	}
	
	public String getTransition(char symbol) {
		return transitions.get(symbol);
	}
	
	public Set<Entry<Character, String>> getAllTransitions() {
		return transitions.entrySet();
	}
	
	/**
	 * Compare String to state's label
	 * @param name
	 * @return
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
