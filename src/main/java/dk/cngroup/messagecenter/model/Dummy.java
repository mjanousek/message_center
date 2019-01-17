package dk.cngroup.messagecenter.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dummy")
public class Dummy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String msg;

	public Dummy() {
	}

	public Dummy(String msg) {
		this.msg = msg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Dummy dummy = (Dummy) o;
		return Objects.equals(id, dummy.id) &&
				Objects.equals(msg, dummy.msg);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, msg);
	}
}
