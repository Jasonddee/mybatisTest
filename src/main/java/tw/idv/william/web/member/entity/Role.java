package tw.idv.william.web.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.idv.william.core.pojo.Core;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Core {
	private static final long serialVersionUID = 2791328122275752602L;
	@Id
	private Integer id;
	private String name;
}
