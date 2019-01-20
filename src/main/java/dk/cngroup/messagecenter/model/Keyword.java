package dk.cngroup.messagecenter.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Keyword {

	@Id
	private String word;

	public Keyword() {
	}

	public Keyword(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public String toString() {
		return word;
	}
}
