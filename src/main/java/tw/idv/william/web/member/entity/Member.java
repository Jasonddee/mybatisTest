package tw.idv.william.web.member.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.idv.william.core.pojo.Core;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Core {
	private static final long serialVersionUID = 1062017833925367218L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password;
	private String nickname;
	@Column(insertable = false)
	private Boolean pass;
	@Column(name = "ROLE_ID")
	private Integer roleId;
	@Column(insertable = false)
	private String creator;
	@Column(name = "CREATED_DATE", insertable = false)
	private Timestamp createdDate;
	private String updater;
	@Column(name = "LAST_UPDATED_DATE")
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp lastUpdatedDate;
	private byte[] image;
	@ManyToOne
	@JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)
	private Role role;
}
