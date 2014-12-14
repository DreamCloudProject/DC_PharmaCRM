package ru.dreamcloud.pharmatracker.model.authentication;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import ru.dreamcloud.pharmatracker.model.ContactInfo;
import ru.dreamcloud.pharmatracker.model.Notification;


/*delimiter $$

CREATE TABLE `common_user_info` (
  `user_info_id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `sessionid` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_info_id`),
  KEY `fk_role_user_info_idx` (`role`),
  KEY `fk_contact_info_user_info_idx` (`contact_info`),
  CONSTRAINT `fk_contact_info_user_info` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_user_info` FOREIGN KEY (`role`) REFERENCES `common_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$
 */
@Entity
@Table(name="common_user_info")
public class CommonUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_info_id")
	private int userInfoId;

	private String firstname;

	private String lastname;

	private String login;

	private String middlename;

	private String password;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinColumn(name="role")
	private CommonRole role;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="userInfo")
    private List<Notification> notifications;

	private String sessionid;
	
	@OneToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="contact_info")
	private ContactInfo contactInfo;

	public CommonUserInfo() {
	}

	public int getUserInfoId() {
		return this.userInfoId;
	}

	public void setUserInfoId(int userInfoId) {
		this.userInfoId = userInfoId;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CommonRole getRole() {
		return role;
	}

	public void setRole(CommonRole role) {
		this.role = role;
	}

	public String getSessionid() {
		return this.sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}	

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	
	@Transient
	public String getFullname(){
		return this.lastname + " " + this.firstname + " " + this.middlename;
	}

}