package by.pvt.pintusov.courses.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Describes the abstract entity <strong>AbstractEntity</strong>
 * @author dpintusov
 * @version 1.2
 */

@NoArgsConstructor
@MappedSuperclass
@Access(AccessType.PROPERTY)
@Data
public abstract class AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "identity")
	@GenericGenerator(name = "identity", strategy = "identity")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	protected Integer id;

	@Override
	public boolean equals (Object o) {
		if (this == o) {return true;}
		if (o == null || !(o instanceof AbstractEntity)) {return false;}
		AbstractEntity abstractEntity = (AbstractEntity) o;
		return id == abstractEntity.id;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "AbstractEntity {id=" + id + "}";
	}
}
