package ru.dreamcloud.pharmatracker.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/*delimiter $$

CREATE TABLE `extensions` (
  `extension_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `extension_name` varchar(255) DEFAULT NULL,
  `icon_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`extension_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$ 
 */
@Entity
@Table(name="extensions")
public class Extension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="extension_id")
	private int extensionId;

	private String description;

	@Column(name="extension_name")
	private String extensionName;

	@Column(name="icon_name")
	private String iconName;

	private String title;
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, mappedBy="extension")
    private List<Document> documents;

	public Extension() {
	}

	public int getExtensionId() {
		return this.extensionId;
	}

	public void setExtensionId(int extensionId) {
		this.extensionId = extensionId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtensionName() {
		return this.extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public String getIconName() {
		return this.iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}	

}